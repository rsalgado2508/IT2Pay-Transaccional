package co.com.it2ex.it2pay.util.negocio.servicios.intento;

import javax.servlet.http.HttpServletRequest;

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.accesodatos.mapper.generico.ParametroMapper;
import co.com.it2ex.it2pay.util.accesodatos.mapper.intento.BloqueoMapper;
import co.com.it2ex.it2pay.util.accesodatos.mapper.intento.IntentoUsuarioMapper;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.intento.BloqueoDTO;
import co.com.it2ex.it2pay.util.modelo.intento.IntentoUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.session.ParametrosValidacionURLDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.funcionalidad.ServiciosFuncionalidad;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesAplicacion;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.enums.TipoValorEnum;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiciosIntentoUsuarioImpl implements ServiciosIntentoUsuario{

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;

	@Autowired
	private IntentoUsuarioMapper intentoUsuarioMapper;
	

	@Autowired
	private ParametroMapper parametroMapper;
	
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private ServiciosComun serviciosComun;
	
	@Autowired
	ServiciosBloqueo serviciosBloqueo;
	
	private String usuario = "";
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ServiciosFuncionalidad serviciosFuncionalidad;
	
	@Autowired
	BloqueoMapper bloqueoMapper;

	@Override
	public IntentoUsuarioDTO consultarIntentoUsuario(IntentoUsuarioDTO intentoUsuarioDTO) throws Exception {

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, intentoUsuarioDTO.toString(), this.getClass() );

		obtenerUsuario(intentoUsuarioDTO);

		
		IntentoUsuarioDTO intento = new IntentoUsuarioDTO();
		Integer numeroIntentos = intentoUsuarioMapper.consultarIntentoUsuario(intentoUsuarioDTO);
		if(numeroIntentos != null && numeroIntentos !=0){
			intento.setNumeroIntento(numeroIntentos);
			intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());
			
			return intento;
		}
		else{
			intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
			intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());

			return intento;
		}
	}
	
	
	@Override
	public BaseDTO registrarIntento(IntentoUsuarioDTO intentoUsuarioDTO) throws Exception {

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

		BaseDTO intento = new BaseDTO();
		
		obtenerUsuario(intentoUsuarioDTO);

		ParametrosValidacionURLDTO parametro =  new ParametrosValidacionURLDTO();
		String estadoPagina  = request.getHeader("state");
		Long idFuncionalidad;
		parametro.setUrlFuncionalidad(estadoPagina);
		
		if (intentoUsuarioDTO.getIdFuncionalidad()==null){
			idFuncionalidad = serviciosFuncionalidad.consultarIdFuncionalidad(parametro);
		}else{
			idFuncionalidad = intentoUsuarioDTO.getIdFuncionalidad();
		}

		
		Integer registro = intentoUsuarioMapper.consultarIntentoUsuario(intentoUsuarioDTO);
		if(registro!=null){
			intentoUsuarioMapper.modificarIntentoUsuario(intentoUsuarioDTO);
			registro = intentoUsuarioMapper.consultarIntentoUsuario(intentoUsuarioDTO);
			
			if(intentoUsuarioDTO.getParametroIntento().equals(TipoValorEnum.VACIO.name())){
				intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
				intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());
			}
			else if(registro == Integer.parseInt(intentoUsuarioDTO.getParametroIntento())-1){
				intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_ULTIMO_INTENTO);
				intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());

				return intento;
			}
			else if(registro >= Integer.parseInt(intentoUsuarioDTO.getParametroIntento())){
				UsuarioDTO usuarioRespuesta = new UsuarioDTO();
				
				Long idUsuario = usuarioMapper.consultarIdUsuarioPorLogin(intentoUsuarioDTO.getUsuario());

				usuarioRespuesta.setIdUsuario(idUsuario);
				usuarioRespuesta.setLogin(intentoUsuarioDTO.getUsuario());
				
				BloqueoDTO bloqueo = new BloqueoDTO();
				
				bloqueo.setIdFuncionalidad(idFuncionalidad);
				bloqueo.setIdUsuarioBloqueo(idUsuario);
				bloqueo.setTipoIntento(intentoUsuarioDTO.getTipoIntento());
				Long existeBloqueo = bloqueoMapper.consultarCantBloqueoUsuarioXFuncionalidad(bloqueo);
				
				if(existeBloqueo > 0){	
					
					intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_INTENTO_BLOQUEO);
					intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());

				    return intento;
									
				}
				bloqueo = serviciosBloqueo.bloquearProceso(idFuncionalidad, intentoUsuarioDTO.getTipoIntento(), intentoUsuarioDTO.getUsuario(), null, idUsuario);
				
				if (!bloqueo.getCodigoRespuesta().equalsIgnoreCase(ConstantesCodigosError.CODIGO_EXITO)){
					intento.setCodigoRespuesta(bloqueo.getCodigoRespuesta());
					intento.setMensajeRespuesta(bloqueo.getMensajeRespuesta());
					return intento;
				}
				if(bloqueo.getBloqueoDestino().equalsIgnoreCase("U")){
					usuarioMapper.bloquearUsuario(usuarioRespuesta);

				}else{
					intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_INTENTO_BLOQUEO);
					intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());

					
					
					return intento;
				}
				
				intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_MENSAJE_CONTRASENA_BLOQ);
				intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());

				return intento;
			}
			intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());
			
			return intento;
		}
		else{
			intentoUsuarioMapper.insertarIntentoUsuario(intentoUsuarioDTO);
			intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(intento.getCodigoRespuesta()).getMensaje());
			
			return intento;
		}	
	}


	@Override
	public BaseDTO eliminarIntento(IntentoUsuarioDTO intentoUsuarioDTO) throws Exception {

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

		BaseDTO intento = new BaseDTO();
		obtenerUsuario(intentoUsuarioDTO);

		if(consultarIntentoUsuario(intentoUsuarioDTO).getCodigoRespuesta()
				.equals(ConstantesCodigosError.CODIGO_EXITO)){
			intentoUsuarioMapper.eliminarIntento(intentoUsuarioDTO);
			intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(
					intento.getCodigoRespuesta()).getMensaje());
			return intento;
		}
		intento.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
		intento.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(
				intento.getCodigoRespuesta()).getMensaje());
		return intento;
	}
	
	private void obtenerUsuario(IntentoUsuarioDTO intentoUsuarioDTO){
		usuario = intentoUsuarioDTO.getUsuario()!=null ? intentoUsuarioDTO.getUsuario():
			ConstantesAplicacion.USUARIO_NO_REGISTRADO;
	}

}
