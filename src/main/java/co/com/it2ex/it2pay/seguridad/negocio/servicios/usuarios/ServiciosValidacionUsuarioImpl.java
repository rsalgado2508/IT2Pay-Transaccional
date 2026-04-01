package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.AutenticacionMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.modelo.token.DatosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesLogger;
import co.com.it2ex.it2pay.util.otros.constantes.enums.EstadosUsuariosEnum;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ServiciosValidacionUsuarioImpl implements ServicioValidacionUsuario {

    private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");

    @Autowired
    private LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    private AutenticacionMapper autenticacionMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ServiciosComun serviciosComun;
    public DatosAutenticacionDTO validarUsuario(DatosAutenticacionDTO usuario) throws Exception {

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosAutenticacionDTO salida = null;

        try{

            if(usuario == null ||
                    usuario.getUsuario() == null ||
                    usuario.getUsuarioContr() == null ||
                    usuario.getUsuario().trim().equals("") ||
                    usuario.getUsuarioContr().trim().equals("")){
                salida = new DatosAutenticacionDTO();

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.ERROR, CaracteresUtil.neutralizeMessage(ConstantesLogger.ERROR_USU_PWD_SVC + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

                salida.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USU_PWD_SVC);
                salida.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_ERROR_USU_PWD_SVC);
                return salida;
            }

            salida = autenticacionMapper.autenticacion(usuario.getUsuario());

            if(salida.getUsuario().equals(usuario.getUsuario()) &&
                    salida.getUsuarioContr().equals(usuario.getUsuarioContr())){
                salida.setUsuario(null);
                salida.setUsuarioContr(null);
                salida.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                salida.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
            } else {
                salida = new DatosAutenticacionDTO();

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.ERROR, CaracteresUtil.neutralizeMessage(ConstantesLogger.ERROR_USU_PWD_NOVAL + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

                salida.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USU_PWD_NOVAL);
                salida.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_ERROR_USU_PWD_NOVAL);
                return salida;
            }

        } catch (Exception e) {

            loggerAuditoriasComponent.registrarLoggerError( e.getMessage(), this.getClass(), e);
            throw e;

        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }

        return salida;
    }

    public DatosBasicosUsuarioDTO validarContrasena(UsuarioDTO usuario) throws Exception{

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosBasicosUsuarioDTO usuarioDTO = new DatosBasicosUsuarioDTO();

        try{

            /*if(!existeUsuarioLogin(usuario)){
                IntentoUsuarioDTO intentoUsuario = new IntentoUsuarioDTO();

                BaseDTO validacionIntento = serviciosIntentoUsuario.registrarIntento(intentoUsuario);
                if(!validacionIntento.getCodigo().equals(CodigosError.CODIGO_EXITO)){

                    usuarioDTO.setCodigo(validacionIntento.getCodigo());
                    usuarioDTO.setMensaje(validacionIntento.getMensaje());
                    return usuarioDTO;
                }

                usuarioDTO.setCodigo(CodigosError.CODIGO_MENSAJE_CONTRASENA);
                usuarioDTO.setMensaje(serviciosComun.consultarMensajePorIdioma(usuarioDTO.getCodigo()).getMensaje());
                auditoria = new AuditoriaServicioDTO(this.getClass().getSimpleName(), new PortalModulosWebException().getStackTrace()[0].getMethodName(),
                        TipoAuditoria.VALIDACION.getNombre(), usuario.getLogin(), usuario.toString(), usuarioDTO.getMensaje());

                servicioAuditoria.auditoriaErrores(auditoria);
                return usuarioDTO;
            }*/



            usuarioDTO = usuarioMapper.consultaDatosBasicosUsuarioPorUsuarioClave(usuario.getLogin(), usuario.getClave());

            if(usuarioDTO!=null){

                if(usuarioDTO.getEstado().equals(EstadosUsuariosEnum.BLOQUEADO.getCodigo())){

                    usuarioDTO.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_BLOQUEADO);
                    usuarioDTO.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(usuarioDTO.getCodigoRespuesta()).getMensaje());

                    return usuarioDTO;
                }

                usuarioDTO.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                usuarioDTO.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
            }

        } catch (Exception e) {
            usuarioDTO = new DatosBasicosUsuarioDTO();
            usuarioDTO.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            usuarioDTO.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(usuarioDTO.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(usuarioDTO.getMensajeRespuesta()), this.getClass(), e);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
        return usuarioDTO;
    }

    /*private boolean existeUsuarioLogin(UsuarioDTO usuarioDTO){
        Integer existe = 0;
        existe = usuarioMapper.existeUsuarioLogin(usuarioDTO);
        if (existe != 0) {
            return true;
        }
        return false;
    }*/

    /**
     * Format con la solución de la vulnerabilidad Concurrent Execution using Shared Resource
     * with Improper Synchronization ('Race Condition').
     *
     * @param date the date
     * @return the string
     */
    public synchronized String format(Date date) {
        return formato.format(date);
    }

}