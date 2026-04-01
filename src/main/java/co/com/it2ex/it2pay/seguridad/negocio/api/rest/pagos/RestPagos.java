package co.com.it2ex.it2pay.seguridad.negocio.api.rest.pagos;

 /*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 28/8/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.pagos.PagoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.pagos.ServiciosPagos;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
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
import java.util.Date;

@RestController
public class RestPagos {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    ServiciosPagos serviciosPagos;

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_REALIZAR_PAGO_ALIADO_PRODUCTO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> realizarPagoAliadoProducto (@RequestBody PagoDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosPagos.componentePagoAliadoProducto(paramsIn, "Transacción");

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_VALIDAR_PAGO_ALIADO_PRODUCTO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> validarPagoAliadoProducto (@RequestBody PagoDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosPagos.validarPagoAliadoProducto(paramsIn);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTAR_PARAM_PAGO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> consultarParamPago (@RequestBody PagoDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosPagos.componentePagoAliadoProducto(paramsIn, "Consulta");

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_REVERSAR_PAGO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> reversarPago (@RequestBody PagoDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosPagos.componentePagoAliadoProducto(paramsIn, "Reverso");

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_ENVIAR_COMPROBANTE_PAGO, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> enviarComprobantePago (@RequestBody UsuarioDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        BaseDTO respuesta = null;

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        try {
            paramsIn.setLogin(usuarioSesion.getLogin());
            respuesta = serviciosPagos.enviarComprobantePago(paramsIn);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<BaseDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null || respuesta.getMensajeRespuesta().trim().equals("")) {
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

    public synchronized String format(Date date) {
        return ConstantesConfiguracion.formatoHora.format(date);
    }


}
