package co.com.it2ex.it2pay.seguridad.negocio.api.rest.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 28/4/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServiciosUsuarios;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.components.UsuarioUtil;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesConfiguracion;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesLogger;
import co.com.it2ex.it2pay.util.otros.constantes.path.ConstantesSeguridadPathRest;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class RestUsuarios {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    private ServiciosUsuarios serviciosUsuarios;

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTAR_LISTA_USUARIOS, method = RequestMethod.GET, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ListaDTO> consultarListaUsuarios( )throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        ListaDTO respuesta = null;

        try {
            respuesta = serviciosUsuarios.listarUsuarios();
            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new ListaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTAR_USUARIO_POR_ID, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UsuarioDTO> consultarUsuarioPorId(@RequestBody UsuarioDTO paramsIn)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        UsuarioDTO respuesta = null;

        try {
            respuesta = serviciosUsuarios.consultarUsuarioPorId(paramsIn);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<UsuarioDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<UsuarioDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new UsuarioDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<UsuarioDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CAMBIAR_ESTADO_USUARIO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> cambiarEstadoUsuario(@RequestBody UsuarioDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosUsuarios.cambiarEstadoUsuario(paramsIn);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new BaseDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_ULTIMA_CONEXION, method = RequestMethod.GET, headers = "Accept="+MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DatosBasicosUsuarioDTO> getUsuarioLogueado(ModelMap model, Principal principal) throws Exception {
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);
        try{
            usuarioSesion.setNombreRol( serviciosUsuarios.obtenerNombreRol( usuarioSesion.getIdRol() ) );

            usuarioSesion.setFechaActual( serviciosUsuarios.cargarFechaActual() );
            String fechaUltimaVisita = serviciosUsuarios.cargarUltimaConexion(usuarioSesion.getIdUsuario());
            String dia = getStringDateDia(fechaUltimaVisita);
            usuarioSesion.setUltimaVisita(dia + ", " + fechaUltimaVisita);

            usuarioSesion.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);

            if(usuarioSesion.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                usuarioSesion.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                usuarioSesion.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<DatosBasicosUsuarioDTO>(usuarioSesion, HttpStatus.OK);

            } else {
                if (usuarioSesion.getMensajeRespuesta() == null
                        || usuarioSesion.getMensajeRespuesta().trim().equals("")) {
                    usuarioSesion.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + usuarioSesion.getCodigoRespuesta()
                        + " - " + usuarioSesion.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<DatosBasicosUsuarioDTO>(usuarioSesion, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (usuarioSesion == null) {
                usuarioSesion = new DatosBasicosUsuarioDTO();
            }
            if (usuarioSesion.getCodigoRespuesta() == null || usuarioSesion.getCodigoRespuesta().equals("")) {
                usuarioSesion.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                usuarioSesion.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( usuarioSesion.getMensajeRespuesta() ), this.getClass(), e);

            return new ResponseEntity<DatosBasicosUsuarioDTO>(usuarioSesion, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    private static String getStringDateDia(String fecha) throws ParseException {
        String dia;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date date = sdf.parse(fecha);

        switch (date.getDay()) {
            case 0:
                dia = "Domingo";
                break;
            case 1:
                dia = "Lunes";
                break;
            case 2:
                dia = "Martes";
                break;
            case 3:
                dia = "Miercoles";
                break;
            case 4:
                dia = "Jueves";
                break;
            case 5:
                dia = "Viernes";
                break;
            case 6:
                dia = "Sabado";
                break;
            default:
                dia = "??";
                break;
        }

        return dia;
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CAMBIO_CLAVE_USUARIO, method = RequestMethod.PUT, headers = "Accept="+MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UsuarioDTO> actualizarContrasena(ModelMap model, Principal principal, @RequestBody UsuarioDTO dto) throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        dto.setLogin(usuarioSesion.getLogin());

        UsuarioDTO respuesta = new UsuarioDTO();

        dto.setIdUsuario(usuarioSesion.getIdUsuario());
        dto.setLogin(usuarioSesion.getLogin());
        dto.setUsuarioCreacion(usuarioSesion.getLogin());
        dto.setUsuarioModificacion(usuarioSesion.getLogin());

        try {
            respuesta = serviciosUsuarios.actualizarClave(dto, usuarioSesion);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<UsuarioDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<UsuarioDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new UsuarioDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<UsuarioDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_DESBLOQUEAR_USUARIO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> desbloquearUsuario(@RequestBody UsuarioDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosUsuarios.desbloquearUsuario(paramsIn);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new BaseDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    /**
     * Format con la solución de la vulnerabilidad Concurrent Execution using Shared Resource
     * with Improper Synchronization ('Race Condition').
     *
     * @param date the date
     * @return the string
     */
    public synchronized String format(Date date) {
        return ConstantesConfiguracion.formatoHora.format(date);
    }



}
