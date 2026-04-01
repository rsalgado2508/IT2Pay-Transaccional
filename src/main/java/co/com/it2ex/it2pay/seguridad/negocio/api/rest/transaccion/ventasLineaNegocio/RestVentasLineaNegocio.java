package co.com.it2ex.it2pay.seguridad.negocio.api.rest.transaccion.ventasLineaNegocio;

import co.com.it2ex.it2pay.seguridad.modelo.graficos.PantallaGraficosDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.transaccion.ventasLineaNegocio.ServicioVentaLineaNegocio;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 11/08/2023
 */
@RestController
public class RestVentasLineaNegocio {
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    ServicioVentaLineaNegocio servicioVentaLineaNegocio;

    private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");


    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_VENTAS_LINEA_NEGOCIO_FECHA,params={ "fechaInicial","fechaFinal","rol" }, method = RequestMethod.GET, headers = "Accept="+ MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PantallaGraficosDTO> listarTodosMovTransacciones(ModelMap model, Principal principal,@RequestParam String fechaInicial,@RequestParam String fechaFinal,@RequestParam Long rol )throws Exception {
        loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        PantallaGraficosDTO respuesta = null;

        try {
            DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);
            respuesta = servicioVentaLineaNegocio.consultarVentasTransaccionesFecha(fechaInicial,fechaFinal,rol,usuarioSesion.getIdUsuario());
            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<PantallaGraficosDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<PantallaGraficosDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            if (respuesta == null) {
                respuesta = new PantallaGraficosDTO();
            }
            if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            }

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
            return new ResponseEntity<PantallaGraficosDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + format(horaInicio) + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }


    public synchronized String format(Date date) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT-5");
        formato.setTimeZone(timeZone);
        return formato.format(date);
    }
}
