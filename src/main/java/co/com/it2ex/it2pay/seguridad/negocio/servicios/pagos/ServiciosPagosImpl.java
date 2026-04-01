package co.com.it2ex.it2pay.seguridad.negocio.servicios.pagos;

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

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.pagos.CodigoRespuestaMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.pagos.ServicioMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.producto.ProductoMapper;
import co.com.it2ex.it2pay.seguridad.modelo.pagos.PagoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.pagos.ResponseClientDTO;
import co.com.it2ex.it2pay.seguridad.modelo.pagos.ServicioDTO;
import co.com.it2ex.it2pay.seguridad.modelo.producto.ProductoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.components.RestServiceClient;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class ServiciosPagosImpl implements ServiciosPagos{

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    RestServiceClient restServiceClient;

    @Autowired
    ProductoMapper productoMapper;

    @Autowired
    ServicioMapper servicioMapper;

    @Autowired
    CodigoRespuestaMapper codigoRespuestaMapper;

    @Override
    public BaseDTO componentePagoAliadoProducto(PagoDTO paramsIn, String tipoConsumo) throws Exception {
        BaseDTO respuesta = new BaseDTO();
        try {
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            System.out.println("JMGR PARAMS IN P: " + paramsIn);

            ServicioDTO tempServicioDTO = servicioMapper.consultarServicioPorAliado(paramsIn.getIdAliado(), tipoConsumo);

            if (tempServicioDTO.getMetodo().equals("REST")) {

                String serviceUrl = tempServicioDTO.getEndpoint();
                Map<String, String> headersMap = new HashMap<>();
                HttpHeaders headersParam = restServiceClient.createHeaders(headersMap);
                List<String> tempCodigosOK = codigoRespuestaMapper.consultarCodigosRespOK(tempServicioDTO.getIdServicio());

                //
                //Aqui se formatea o convierte el JSON si es POST o se completa la URL
                //
                String tempJsonPayload = "{ \"REFERENCIA1\": \"VALOR1\", \"REFERENCIA2\": \"VALOR2\", \"REFERENCIA3\": VALOR3, \"REFERENCIA4\": \"VALOR4\", \"REFERENCIA5\": \"VALOR5\" }";
                serviceUrl += "?" + "idPersona=123456" + "&" + "idTipoDocumento=98765";
                //
                //
                HashMap<String, String> nuevasLlaves = new HashMap<>();
                nuevasLlaves.put("REFERENCIA1", "NUM_DOC");
                nuevasLlaves.put("REFERENCIA2", "NUM_PAGO");
                nuevasLlaves.put("REFERENCIA3", "NUM_CON");
                nuevasLlaves.put("REFERENCIA4", "NUM_ALI");
                nuevasLlaves.put("REFERENCIA5", "VAL");

                HashMap<String, String> nuevosValores = new HashMap<>();
                nuevosValores.put("VALOR1", "CC11998822");
                nuevosValores.put("VALOR2", "1133557799");
                nuevosValores.put("VALOR3", "335577");
                nuevosValores.put("VALOR4", "667788");
                nuevosValores.put("VALOR5", "900652");

                String jsonPayload = restServiceClient.reemplazarValoresJSON(tempJsonPayload, nuevasLlaves, nuevosValores);
                //
                //


                for (int i = 1; i <= tempServicioDTO.getCantidadReintentos(); i++) {

                    ResponseClientDTO tempReturn;

                    if (tempServicioDTO.getSolicitud().equals("POST")) {
                        System.out.println("JMGR PARAMS RQ: " + serviceUrl + ";POST;" + headersParam+ ";" + jsonPayload);
                        tempReturn = restServiceClient.consumeRestService(serviceUrl, jsonPayload, headersParam);
                        System.out.println("JMGR1: " + tempReturn);
                    } else {
                        System.out.println("JMGR PARAMS RQ: " + serviceUrl + ";GET;" + headersParam);
                        tempReturn = restServiceClient.consumeRestService(serviceUrl, headersParam);
                        System.out.println("JMGR1: " + tempReturn);
                    }

                    if (tempReturn.getStatusCode() == 200L) {
                        //
                        //Se puede mapear todas las respuestas en un DTO o incluir IF con diferentes DTO
                        Map<String, Object>  mapResponse = restServiceClient.mapResponseToMap(tempReturn.getBody());
                        System.out.println("JMGR2: " + mapResponse.toString());
                        //En el metodo de mapeo debe validarse variable con codigo OK
                        respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                        respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                        return respuesta;
                    }
                }

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);

            } else if (tempServicioDTO.getMetodo().equals("SOAP")) {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);

            } else if (tempServicioDTO.getMetodo().equals("ISO8583")) {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);

            } else {

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);

            }

        } catch (Exception e) {
            respuesta = new ProductoDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

        }
        return respuesta;
    }

    @Override
    public BaseDTO validarPagoAliadoProducto(PagoDTO paramsIn) throws Exception {
        BaseDTO respuesta;
        try {
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            System.out.println("JMGR PARAMS IN V: " + paramsIn);

            Date horaActual = new Date();
            respuesta = new BaseDTO();

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setIdAliado(paramsIn.getIdAliado());
            usuarioDTO.setIdProducto(paramsIn.getIdProducto());

            ProductoDTO detalleProductoTemp = productoMapper.consultarDetalleProductoPorAliado(usuarioDTO);

            //Validar fecha actual entre intervalos
            if (!validarFechaEnRango(detalleProductoTemp.getVentanaOperacionInicio(),
                    detalleProductoTemp.getVentanaOperacionFinal(), horaActual)) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                return respuesta;
            }

            //Validar valor pago en intervalo
            if (!validarValorEnRango(detalleProductoTemp.getMontoMinimo(),
                    detalleProductoTemp.getMontoMaximo(), paramsIn.getValor())) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                return respuesta;
            }

            //Validar si es multiplo
            if (!validarMultiplo(detalleProductoTemp.getMultiplosPagos(),paramsIn.getValor())) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                return respuesta;
            }

            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_EXITO);


        } catch (Exception e) {
            respuesta = new ProductoDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

        }
        return respuesta;
    }

    public boolean validarMultiplo (String valorMultiplo, String valorActual) {

        boolean esMultiplo = false;

        System.out.println("validarMultiplo" + valorActual + "@" + valorMultiplo);

        try {

            long multiplo = Long.parseLong(valorMultiplo);
            long valor = Long.parseLong(valorActual);

            if (valor % multiplo == 0) {
                System.out.println(valorActual + " es múltiplo de " + valorMultiplo);
                esMultiplo = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir los valores a números long: " + e.getMessage());
            esMultiplo = false;
        }

        return esMultiplo;
    }

    public boolean validarValorEnRango (String valorMinimo, String valorMaximo, String valorActual) {

        boolean valorEnIntervalo = false;

        System.out.println("validarValorEnRango" + valorMinimo + "@" + valorMaximo + "@" + valorActual);

        try {
            long min = Long.parseLong(valorMinimo);
            long max = Long.parseLong(valorMaximo);
            long actual = Long.parseLong(valorActual);

            if (actual >= min && actual <= max) {
                System.out.println("El valor actual está dentro del rango.");
                valorEnIntervalo = true;
            }

        } catch (NumberFormatException e) {
            System.out.println("Error al convertir los valores a números long: " + e.getMessage());
            valorEnIntervalo = false;
        }

        return valorEnIntervalo;

    }

    public boolean validarFechaEnRango (String fechaInicial, String fechaFinal, Date horaActual) {

        System.out.println("validarFechaEnRango" + fechaInicial + "@" + fechaFinal + "@" + horaActual);

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        boolean horaEnIntervalo = false;

        try {

            String[] intervalosInicio = fechaInicial.split(",");
            String[] intervalosFin = fechaFinal.split(",");

            for (int i = 0; i < intervalosInicio.length; i++) {

                Date horaInicio = quitarDiaMesAnio(formatoHora.parse(intervalosInicio[i]));
                Date horaFin = quitarDiaMesAnio(formatoHora.parse(intervalosFin[i]));
                Date horaActualSinFecha = quitarDiaMesAnio(horaActual);

                if (horaActualSinFecha.after(horaInicio) && horaActualSinFecha.before(horaFin)) {
                    System.out.println("La hora actual está dentro del rango");
                    horaEnIntervalo = true;
                    break;
                }

            }

        } catch (Exception e) {
            System.out.println("Error al comparar las horas: " + e.getMessage());
            horaEnIntervalo = false;
        }

        return horaEnIntervalo;

    }

    public Date quitarDiaMesAnio (Date horaIn) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(horaIn);

        cal.set(Calendar.YEAR, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);

        Date horaActualSinFecha = cal.getTime();

        return horaActualSinFecha;

    }

    @Override
    public BaseDTO enviarComprobantePago(UsuarioDTO paramsIn) throws Exception {
        BaseDTO respuesta;
        try {
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            respuesta = new BaseDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_EXITO);


        } catch (Exception e) {
            respuesta = new ProductoDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

        }
        return respuesta;
    }
}
