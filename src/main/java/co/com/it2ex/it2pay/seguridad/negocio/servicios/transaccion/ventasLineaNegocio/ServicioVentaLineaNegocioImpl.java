package co.com.it2ex.it2pay.seguridad.negocio.servicios.transaccion.ventasLineaNegocio;

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.puntoventa.PuntoVentaMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.transaccion.ventasLineaNegocio.VentaLineaNegociosMapper;
import co.com.it2ex.it2pay.seguridad.modelo.graficos.PantallaGraficosDTO;
import co.com.it2ex.it2pay.seguridad.modelo.puntoventa.PuntoVentaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;
import co.com.it2ex.it2pay.util.accesodatos.mapper.generico.ParametroMapper;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroConsultaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

@Service
public class ServicioVentaLineaNegocioImpl implements ServicioVentaLineaNegocio {
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    ServiciosComun serviciosComun;
    @Autowired
    VentaLineaNegociosMapper ventaLineaNegociosMapper;
    @Autowired
    ParametroMapper parametroMapper;
    @Autowired
    PuntoVentaMapper puntoVentaMapper;


    @Override
    public PantallaGraficosDTO consultarVentasTransaccionesFecha(String fechaInicial, String fechaFinal,Long rol,Long idPersona) throws Exception {
        PantallaGraficosDTO respuesta;
        try {
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            List<MovimientoTransaccionDTO> listado = new ArrayList<MovimientoTransaccionDTO>();
            List<MovimientoTransaccionDTO> listadoNumeroTransacciones = new ArrayList<MovimientoTransaccionDTO>();



            respuesta = new PantallaGraficosDTO();
            Map<String, Object> mapaGraficos = new HashMap<>();
            Date s=new Date();
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date fechaI = dateFormat.parse(fechaInicial);
            Date fechaF = dateFormat.parse(fechaFinal);
            LocalDate localDate1 = fechaI.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = fechaF.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long diferenciaDias = ChronoUnit.DAYS.between(localDate1, localDate2); // Diferencia en días
            ParametroConsultaDTO parametro =new ParametroConsultaDTO();
            parametro.setId(20L);

            long numeroDiasPermitidos = Long.parseLong(parametroMapper.selectValorParametroPorId(parametro).getValor());
            if (!(diferenciaDias <= numeroDiasPermitidos)) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_AUDOTORIA_02);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje() + numeroDiasPermitidos);
                respuesta.setMapaGraficos(mapaGraficos);
                return respuesta;
            }

            if (rol==1){
                listado = ventaLineaNegociosMapper.listarMovTransaccionesEntreFechas(fechaI,fechaF);
                listadoNumeroTransacciones = ventaLineaNegociosMapper.listarNumeroMovTransaccionesEntreFechas(fechaI,fechaF);
            }else{

                List<PuntoVentaDTO> listadoPV = puntoVentaMapper.listarPuntoVentaRol(idPersona);

                if ( listadoPV != null && listadoPV.size() > 0) {
                    Long pVenta = listadoPV.get(0).getIdPuntoVenta();
                    listado = ventaLineaNegociosMapper.listarMovTransaccionesEntreFechasPuntoVenta(fechaI,fechaF,pVenta);
                    listadoNumeroTransacciones = ventaLineaNegociosMapper.listarNumeroMovTransaccionesEntreFechasPuntoVenta(fechaI,fechaF,pVenta);
                }

            }

            Map<String, Object> mapaGraficos2=consultarNumeroTransaccionesFecha(listadoNumeroTransacciones);
            if (listado.isEmpty()) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_AUDOTORIA_01);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                respuesta.setMapaGraficos(mapaGraficos);
                return respuesta;
            }


            List<Long> arregloDepo = new ArrayList<Long>();
            List<Long> arregloRetiro = new ArrayList<Long>();
            List<Long> arregloBill = new ArrayList<Long>();
            List<Long> arregloPines = new ArrayList<Long>();

            List<Date> arregloDepoFecha = new ArrayList<Date>();
            List<Date> arregloRetiroFecha = new ArrayList<Date>();
            List<Date> arregloBillFecha = new ArrayList<Date>();
            List<Date> arregloPinesFecha = new ArrayList<Date>();
            for (MovimientoTransaccionDTO temp : listado) {
                if (temp.getIdLineaNegocio()==1){
                    arregloRetiro.add(temp.getMonto());
                    arregloRetiroFecha.add(temp.getFechaCreacion());
                }

                if (temp.getIdLineaNegocio()==2){
                    arregloDepo.add(temp.getMonto());
                    arregloDepoFecha.add(temp.getFechaCreacion());
                }
                if (temp.getIdLineaNegocio()==3){
                    arregloBill.add(temp.getMonto());
                    arregloBillFecha.add(temp.getFechaCreacion());
                }
                if (temp.getIdLineaNegocio()==4){
                    arregloPines.add(temp.getMonto());
                    arregloPinesFecha.add(temp.getFechaCreacion());
                }

            }
            mapaGraficos.put("GRAFICO_RETIRO", arregloRetiro);
            mapaGraficos.put("GRAFICO_DEPOSITO", arregloDepo);
            mapaGraficos.put("GRAFICO_PINES", arregloPines);
            mapaGraficos.put("GRAFICO_BILLPAYMENT", arregloBill);

            mapaGraficos.put("GRAFICO_RETIRO_FECHA", arregloRetiroFecha);
            mapaGraficos.put("GRAFICO_DEPOSITO_FECHA", arregloDepoFecha);
            mapaGraficos.put("GRAFICO_PINES_FECHA", arregloPinesFecha);
            mapaGraficos.put("GRAFICO_BILLPAYMENT_FECHA", arregloBillFecha);
            mapaGraficos.putAll(mapaGraficos2);


            respuesta.setMapaGraficos(mapaGraficos);
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

        } catch (Exception e) {
            respuesta = new PantallaGraficosDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }

    @Override
    public Map<String, Object> consultarNumeroTransaccionesFecha(List<MovimientoTransaccionDTO> listado) throws Exception {

            Map<String, Object> mapaGraficos = new HashMap<>();


            List<Long> arregloDepo = new ArrayList<Long>();
            List<Long> arregloRetiro = new ArrayList<Long>();
            List<Long> arregloBill = new ArrayList<Long>();
            List<Long> arregloPines = new ArrayList<Long>();

            List<Date> arregloDepoFecha = new ArrayList<Date>();
            List<Date> arregloRetiroFecha = new ArrayList<Date>();
            List<Date> arregloBillFecha = new ArrayList<Date>();
            List<Date> arregloPinesFecha = new ArrayList<Date>();

            for (MovimientoTransaccionDTO temp : listado) {
                if (temp.getIdLineaNegocio()==1){
                    arregloRetiro.add(temp.getMonto());
                    arregloRetiroFecha.add(temp.getFechaCreacion());
                }

                if (temp.getIdLineaNegocio()==2){
                    arregloDepo.add(temp.getMonto());
                    arregloDepoFecha.add(temp.getFechaCreacion());
                }
                if (temp.getIdLineaNegocio()==3){
                    arregloBill.add(temp.getMonto());
                    arregloBillFecha.add(temp.getFechaCreacion());
                }
                if (temp.getIdLineaNegocio()==4){
                    arregloPines.add(temp.getMonto());
                    arregloPinesFecha.add(temp.getFechaCreacion());
                }

            }
            mapaGraficos.put("GRAFICO_RETIRO_N", arregloRetiro);
            mapaGraficos.put("GRAFICO_DEPOSITO_N", arregloDepo);
            mapaGraficos.put("GRAFICO_PINES_N", arregloPines);
            mapaGraficos.put("GRAFICO_BILLPAYMENT_N", arregloBill);

            mapaGraficos.put("GRAFICO_RETIRO_FECHA_N", arregloRetiroFecha);
            mapaGraficos.put("GRAFICO_DEPOSITO_FECHA_N", arregloDepoFecha);
            mapaGraficos.put("GRAFICO_PINES_FECHA_N", arregloPinesFecha);
            mapaGraficos.put("GRAFICO_BILLPAYMENT_FECHA_N", arregloBillFecha);

        return mapaGraficos;

    }

}
