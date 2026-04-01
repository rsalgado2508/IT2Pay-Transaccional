package co.com.it2ex.it2pay.seguridad.negocio.api.rest.login;
/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Jorge Ruiz
 * @FechaCreación: 27/4/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.login.LoginCambioClaveDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginRespuestaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginEntradaDTO;

import co.com.it2ex.it2pay.seguridad.negocio.servicios.login.ServicioLogin;

import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;

import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesLogger;
import co.com.it2ex.it2pay.util.otros.constantes.path.ConstantesSeguridadPathRest;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class RestLogin {
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    private ServicioLogin servicioLogin;

    private final SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_LOGIN, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginRespuestaDTO> validarClaveLogin(@RequestBody LoginEntradaDTO datos) throws Exception {

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        LoginRespuestaDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        try {

            respuesta = servicioLogin.validarClaveLogin(datos);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<LoginRespuestaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<LoginRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new LoginRespuestaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

            return new ResponseEntity<LoginRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }

    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CAMBIAR_CLAVE_LOGIN, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginRespuestaDTO> cambiarClaveLogin(@RequestBody LoginCambioClaveDTO datos) {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        LoginRespuestaDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        try {

            datos.setUsuarioModificacion( datos.getLogin() );

            respuesta = servicioLogin.cambiarClaveLogin(datos);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<LoginRespuestaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<LoginRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new LoginRespuestaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

            return new ResponseEntity<LoginRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
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
        return formato.format(date);
    }
}