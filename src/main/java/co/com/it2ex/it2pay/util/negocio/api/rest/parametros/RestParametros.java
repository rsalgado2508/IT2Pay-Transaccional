package co.com.it2ex.it2pay.util.negocio.api.rest.parametros;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 18/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.*;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.negocio.servicios.parametros.ServicioParametros;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
public class RestParametros {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    private ServicioParametros servicioParametros;

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTAR_PARAMETROS, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ListaDTO> consultarParametros (@RequestBody ParametroDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        ListaDTO respuesta = null;

        try {
            paramsIn.setUsuarioModificacion(usuarioSesion.getLogin());
            respuesta = servicioParametros.consultarParametros(paramsIn);
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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTAR_GRUPOS_PARAMETROS, method = RequestMethod.GET, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ListaDTO> consultarGruposParametros (ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        ListaDTO respuesta = null;

        try {
            respuesta = servicioParametros.consultarGruposParametros();
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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_MODIFICAR_PARAMETROS, method = RequestMethod.POST, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BaseDTO> modificarParametro (@RequestBody ParametroDTO paramsIn, ModelMap model, Principal principal)throws Exception {

        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

        BaseDTO respuesta = null;

        try {
            paramsIn.setUsuarioModificacion(usuarioSesion.getLogin());
            respuesta = servicioParametros.modificarParametro(paramsIn);

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

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_PARAMETROS_VALOR_POR_NOMBRE, params={"nombre"}, method = RequestMethod.GET, headers = "Accept="+MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ParametroConsultaDTO> parametroValorPorNombre(ModelMap model, Principal principal, @RequestParam String nombre)throws Exception{
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        ParametroConsultaDTO respuesta = new ParametroConsultaDTO();
        try {
            respuesta = servicioParametros.obtenerValorParametro(nombre);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<ParametroConsultaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<ParametroConsultaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new ParametroConsultaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<ParametroConsultaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_PARAMETROS_PUBLICO_POR_NOMBRE, params={"nombre"}, method = RequestMethod.GET, headers = "Accept="+MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ParametroConsultaDTO> parametroValorPorNombre(@RequestParam String nombre)throws Exception{
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        ParametroConsultaDTO respuesta = new ParametroConsultaDTO();
        try {
            respuesta = servicioParametros.obtenerValorParametro(nombre);

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<ParametroConsultaDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<ParametroConsultaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new ParametroConsultaDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<ParametroConsultaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
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
