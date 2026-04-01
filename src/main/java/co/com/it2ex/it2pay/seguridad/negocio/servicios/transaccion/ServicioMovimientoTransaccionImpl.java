package co.com.it2ex.it2pay.seguridad.negocio.servicios.transaccion;

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.transaccion.MovimientoTransaccionMapper;
import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionRespuestaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.correo.ServicioMensajeria;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesPlantillasCorreo;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

@Service
public class ServicioMovimientoTransaccionImpl implements ServicioMovimientoTransaccion{
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    ServiciosComun serviciosComun;
    @Autowired
    MovimientoTransaccionMapper movimientoTransaccionMapper;
    @Autowired
    ServicioMensajeria servicioMensajeria;

    public ListaDTO listarMovTransacciones() throws Exception {
        ListaDTO respuesta = new ListaDTO();
        try {
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(
                    ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO +
                            new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            List<MovimientoTransaccionDTO> listado = movimientoTransaccionMapper.listarMovTransacciones();
            if (listado.isEmpty()) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS_FECHA_ACTUAL);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS_FECHA_ACTUAL);
            } else {
                respuesta.setLista(listado);
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }
        } catch (Exception e){
            respuesta = new ListaDTO();
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuesta.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);
        }
        return respuesta;
    }

    @Override
    public ListaDTO consultarTransaccionesFecha(String fechaInicial, String fechaFinal) throws Exception {
        ListaDTO respuesta;
        try {
            String pattern = "yyyy-MM-dd";
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date fechaI = dateFormat.parse(fechaInicial);
            Date fechaF = dateFormat.parse(fechaFinal);
            LocalDate localDate1 = fechaI.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = fechaF.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long diferenciaDias = ChronoUnit.DAYS.between(localDate1, localDate2);
            long numeroDiasPermitidos = movimientoTransaccionMapper.consultarNumeroDias().get(0).getNumeroDias();
            respuesta = new ListaDTO();
            List<MovimientoTransaccionDTO> listado = new ArrayList<MovimientoTransaccionDTO>();
            if (!(diferenciaDias <= numeroDiasPermitidos)) {
                respuesta.setLista(listado);
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_AUDOTORIA_02);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje() + numeroDiasPermitidos);
                return respuesta;
            }
            listado = movimientoTransaccionMapper.consultarTransaccionesFecha(fechaI, fechaF);
            if (listado.isEmpty()) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_AUDOTORIA_01);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                respuesta.setLista(listado);
                return respuesta;
            }
            respuesta.setLista(listado);
            respuesta.setTotalPaginas(listado.size() > 0 ? 1 : 0);
            respuesta.setTotalRegistros(listado.size());
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

        } catch (Exception e) {
            respuesta = new ListaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public MovimientoTransaccionRespuestaDTO reversarTransaccion(MovimientoTransaccionDTO movimientoTransaccionDTO) throws Exception {
        MovimientoTransaccionRespuestaDTO respuesta = new MovimientoTransaccionRespuestaDTO();
        try {
            movimientoTransaccionMapper.reversarTransaccion(movimientoTransaccionDTO);
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

        } catch (Exception e) {
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_COLOCADOR_01);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje() + e.getMessage());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
            return respuesta;
        }
        return respuesta;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public MovimientoTransaccionRespuestaDTO enviarComprobante(MovimientoTransaccionDTO datos) throws Exception {
        MovimientoTransaccionRespuestaDTO respuesta = new MovimientoTransaccionRespuestaDTO();
        try {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String montoFormateado = numberFormat.format(datos.getMonto());

            SimpleDateFormat formatoOriginal = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
            Date fechaDate = formatoOriginal.parse(datos.getFechaFormat());

            // Formatear la fecha en el nuevo formato
            SimpleDateFormat formatoNuevo = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = formatoNuevo.format(fechaDate);

            CorreoDTO mensaje = new CorreoDTO();
            mensaje.setPara(datos.getCorreoEnvioComprobante());
            mensaje.setParaNombre(datos.getCorreoEnvioComprobante());

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("correo", datos.getCorreoEnvioComprobante());
            jsonObject.put("estado", datos.getEstado());
            jsonObject.put("monto", montoFormateado);
            jsonObject.put("moneda", datos.getMoneda());
            jsonObject.put("fecha", fechaFormateada);
            jsonObject.put("fechaFormat", datos.getFechaFormat());
            jsonObject.put("cliente", datos.getCliente());
            jsonObject.put("lineaNegocio", datos.getNombreLineaNegocio());
            jsonObject.put("puntoVenta", datos.getNombrePuntoVenta());
            jsonObject.put("ubicacionPuntoVenta", datos.getUbicacionPuntoVenta());
            jsonObject.put("codigoPuntoVenta", datos.getCodigoPuntoVenta());
            jsonObject.put("colocador", datos.getColocador());
            jsonObject.put("hora", datos.getFechaInicio().getHours());
            jsonObject.put("numeroConvenio", datos.getCodigoConvenio());
            jsonObject.put("nombreConvenio", datos.getNombreConvenio());
            jsonObject.put("factura", datos.getReferencia());
            jsonObject.put("idTransaccion", datos.getIdTransaccion());
            jsonObject.put("referencia", datos.getReferencia());
            jsonObject.put("mensaje", datos.getMensaje());
            jsonObject.put("error", datos.getError());
            jsonObject.put("mensajeError", datos.getMensajeError());


            String json = objectMapper.writeValueAsString(jsonObject);
            if(datos.getEstado().equalsIgnoreCase("Completada")){
                mensaje.setPlantilla( ConstantesPlantillasCorreo.PLANTILLA_ENVIO_COMPROBANTE );
            }
            else{
                mensaje.setPlantilla( ConstantesPlantillasCorreo.PLANTILLA_ENVIO_COMPROBANTE_FALLIDA );
            }

            mensaje.setContenido(json);
            mensaje.setAsunto( "Comprobante" );

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.DEBUG, mensaje.toString(), this.getClass() );

            servicioMensajeria.envioMensaje(mensaje);

            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
        } catch (Exception e) {
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_COLOCADOR_01);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje() + e.getMessage());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
            return respuesta;
        }
        return respuesta;
    }
}
