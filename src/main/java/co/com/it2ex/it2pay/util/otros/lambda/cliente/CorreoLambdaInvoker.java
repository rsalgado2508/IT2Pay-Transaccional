package co.com.it2ex.it2pay.util.otros.lambda.cliente;

import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 30/6/2023
 */

public class CorreoLambdaInvoker {

    @Async
    public void envioCorreo(LoggerAuditoriasComponent loggerAuditoriasComponent, String lambdaUrl, String jsonInput ){
        try {
            // Crear el cliente HTTP
            HttpClient httpClient = HttpClients.createDefault();

            // Crear la solicitud POST con la URL de la función de Lambda
            HttpPost httpPost = new HttpPost(lambdaUrl);

            // Configurar el cuerpo de la solicitud con el JSON de entrada
            StringEntity requestEntity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            // Ejecutar la llamada HTTP y obtener la respuesta
            HttpResponse response = httpClient.execute(httpPost);

            // Procesar la respuesta
            HttpEntity responseEntity = response.getEntity();
            String jsonResponse = EntityUtils.toString(responseEntity);

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(jsonResponse), this.getClass() );
        } catch (Exception e) {
            BaseDTO respuesta = new BaseDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
    }

}
