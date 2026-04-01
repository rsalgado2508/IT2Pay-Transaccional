package co.com.it2ex.it2pay.util.negocio.servicios.intento;

import co.com.it2ex.it2pay.util.accesodatos.mapper.intento.BloqueoMapper;
import co.com.it2ex.it2pay.util.modelo.intento.BloqueoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false, rollbackFor = Exception.class)
public class ServiciosBloqueoImpl implements ServiciosBloqueo{

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;

	@Autowired
	private ServiciosComun serviciosComun;

	@Autowired
	private BloqueoMapper bloqueoMapper;

	@Override
	public BloqueoDTO bloquearProceso(Long idFuncionalidad, String tipoIntento, String usuario, String persona, Long idUsuario) throws Exception {

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

		BloqueoDTO bloqueo = new BloqueoDTO();


		try{

			bloqueo.setIdFuncionalidad(idFuncionalidad);
			bloqueo.setTipoIntento(tipoIntento);
			bloqueo = bloqueoMapper.consultarTipoBloqueo(bloqueo);

			if (bloqueo!=null){
				bloqueo.setEstadoBloqueo("A");

				if(usuario != null){

					bloqueo.setIdUsuarioBloqueo(idUsuario);
					bloqueo.setLoginUsuarioBloqueo(usuario);


					long existeRegistroBloqueo = bloqueoMapper.consultarTipoBloqueoUsuario(bloqueo);
					if(existeRegistroBloqueo <= 0){						
						bloqueoMapper.registrarBloqueoUsuario(bloqueo);


					}

				}else{
					bloqueo.setIdPersonaBloqueo(persona);
					bloqueo.setLoginUsuarioBloqueo(persona);
					long existeRegistroBloqueo = bloqueoMapper.consultarTipoBloqueoPersona(bloqueo);
					if(existeRegistroBloqueo <= 0){						
						bloqueoMapper.registrarBloqueoPersona(bloqueo);
					}

				}

			}else{
				bloqueo = new BloqueoDTO();
				bloqueo.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
				bloqueo.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(bloqueo.getCodigoRespuesta()).getMensaje());


				return bloqueo;
			}

			bloqueo.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			bloqueo.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(bloqueo.getCodigoRespuesta()).getMensaje());

		}catch(Exception e){
			bloqueo.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_BLOQUEO);
			bloqueo.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(bloqueo.getCodigoRespuesta()).getMensaje());
			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( bloqueo.getMensajeRespuesta() ), this.getClass(), e);
		}
		return bloqueo;
	}

}
