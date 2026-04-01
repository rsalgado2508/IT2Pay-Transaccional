package co.com.it2ex.it2pay.seguridad.negocio.api.rest.transaccion;

import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionRespuestaDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.transaccion.ServicioMovimientoTransaccion;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.components.UsuarioUtil;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo backend
 * @Copyright IT2Ex
 *
 * @Autor: jlalfonso
 * @FechaCreación: 27/07/2023
 */
@RestController
public class RestMovimientoTransaccion {
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    ServicioMovimientoTransaccion servicioMovimientoTransaccion;

    private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_LISTAR_TRANSACCIONES, method = RequestMethod.GET, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ListaDTO> listarMovTransacciones( )throws Exception {
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        ListaDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();
        try {
            respuesta = servicioMovimientoTransaccion.listarMovTransacciones();
            if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
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

            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase " + this.getClass().getSimpleName()
                    + "- Metodo -" + new IT2PayException().getStackTrace()[0].getMethodName() +
                    " fue de " + ((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    + " segundos - Hora inicio " + format(horaInicio) + " - Hora fin " + format(horaFin)), this.getClass());
        }

    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_TRANSACCIONES_FECHA,params={ "fechaInicial","fechaFinal" }, method = RequestMethod.GET, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ListaDTO> consultarTransaccionesFecha(@RequestParam String fechaInicial, @RequestParam String fechaFinal) throws Exception {
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        ListaDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();
        try {
            respuesta = servicioMovimientoTransaccion.consultarTransaccionesFecha( fechaInicial, fechaFinal);
            if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
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

            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase " + this.getClass().getSimpleName()
                    + "- Metodo -" + new IT2PayException().getStackTrace()[0].getMethodName() +
                    " fue de " + ((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    + " segundos - Hora inicio " + format(horaInicio) + " - Hora fin " + format(horaFin)), this.getClass());
        }
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_REVERSAR_TRANSACCION, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MovimientoTransaccionRespuestaDTO> reversarTransaccion(@RequestBody MovimientoTransaccionDTO datos, ModelMap model, Principal principal) throws Exception{

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        MovimientoTransaccionRespuestaDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();
        try {
            DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);
            datos.setUsuarioModificacion(usuarioSesion.getLogin());
            respuesta = servicioMovimientoTransaccion.reversarTransaccion(datos);
            if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<MovimientoTransaccionRespuestaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<MovimientoTransaccionRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new MovimientoTransaccionRespuestaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

            return new ResponseEntity<MovimientoTransaccionRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase " + this.getClass().getSimpleName()
                    + "- Metodo -" + new IT2PayException().getStackTrace()[0].getMethodName() +
                    " fue de " + ((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    + " segundos - Hora inicio " + format(horaInicio) + " - Hora fin " + format(horaFin)), this.getClass());
        }

    }
    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_ENVIAR_COMPROBANTE, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MovimientoTransaccionRespuestaDTO> enviarComprobante(@RequestBody MovimientoTransaccionDTO datos) throws Exception{
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        MovimientoTransaccionRespuestaDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();
        try {
            respuesta = servicioMovimientoTransaccion.enviarComprobante(datos);
            if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<MovimientoTransaccionRespuestaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<MovimientoTransaccionRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new MovimientoTransaccionRespuestaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

            return new ResponseEntity<MovimientoTransaccionRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase " + this.getClass().getSimpleName()
                    + "- Metodo -" + new IT2PayException().getStackTrace()[0].getMethodName() +
                    " fue de " + ((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    + " segundos - Hora inicio " + format(horaInicio) + " - Hora fin " + format(horaFin)), this.getClass());
        }

    }
    public synchronized String format(Date date) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        formato.setTimeZone(timeZone);
        return formato.format(date);
    }
}
