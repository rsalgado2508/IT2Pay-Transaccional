package co.com.it2ex.it2pay.util.otros.components;/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 23/10/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.pagos.ResponseClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestServiceClient {

    public ResponseClientDTO consumeRestService(String url, String jsonPayload, HttpHeaders headers) {

        ResponseClientDTO tempResponseClientDTO = new ResponseClientDTO();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(jsonPayload, headers), String.class);
        tempResponseClientDTO.setBody(responseEntity.getBody());
        tempResponseClientDTO.setStatusCode((long) responseEntity.getStatusCodeValue());
        return tempResponseClientDTO;

    }

    public ResponseClientDTO consumeRestService(String url, HttpHeaders headers) {

        ResponseClientDTO tempResponseClientDTO = new ResponseClientDTO();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        tempResponseClientDTO.setBody(responseEntity.getBody());
        tempResponseClientDTO.setStatusCode((long) responseEntity.getStatusCodeValue());
        return tempResponseClientDTO;

    }

    public HttpHeaders createHeaders(Map<String, String> headersMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.add("Authorization", "Bearer YourAccessToken");
        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        return headers;
    }

    public <T> T mapResponseToClass(String jsonResponse, Class<T> responseType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, responseType);
        } catch (Exception e) {
            // Maneja excepciones o errores de mapeo aquí
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> mapResponseToMap(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, HashMap.class);
        } catch (Exception e) {
            // Maneja excepciones o errores de mapeo aquí
            e.printStackTrace();
            return null;
        }
    }

    public String reemplazarValoresJSON(String jsonStr, HashMap<String, String> nuevasLlaves, HashMap<String, String> nuevosValores) throws Exception {
        // Parsear el JSON original
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonStr);

        // Crear un nuevo nodo JSON para el resultado
        ObjectNode newJson = objectMapper.createObjectNode();

        // Iterar a través de las llaves del JSON original
        rootNode.fields().forEachRemaining(entry -> {
            String originalLlave = entry.getKey();
            JsonNode valor = entry.getValue();

            // Verificar si la llave está en el mapa de nuevas llaves
            if (nuevasLlaves.containsKey(originalLlave)) {
                // Obtener la nueva llave
                String nuevaLlave = nuevasLlaves.get(originalLlave);
                newJson.set(nuevaLlave, valor);
            } else {
                // Conservar la llave original
                newJson.set(originalLlave, valor);
            }
        });

        // Iterar a través de los valores del JSON original y reemplazarlos si es necesario
        newJson.fields().forEachRemaining(entry -> {
            String llave = entry.getKey();
            JsonNode valor = entry.getValue();

            if (valor.isTextual() && nuevosValores.containsKey(valor.textValue())) {
                ((ObjectNode) entry.getValue()).put(valor.textValue(), nuevosValores.get(valor.textValue()));
            }
        });

        // Convertir el nuevo JSON a una cadena
        String nuevoJsonStr = objectMapper.writeValueAsString(newJson);

        return nuevoJsonStr;
    }
}