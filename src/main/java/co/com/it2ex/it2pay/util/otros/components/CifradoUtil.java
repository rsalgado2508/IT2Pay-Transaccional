package co.com.it2ex.it2pay.util.otros.components;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 08/05/2023
 */

import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
@Component
public class CifradoUtil {

    @Autowired
    private LoggerAuditoriasComponent loggerAuditoriasComponent;

    public String cifrarCadenaSha512 (String cadena) {

        StringBuilder sb = new StringBuilder();

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = cadena.getBytes();
            md.update(bytes);
            byte[] digest = md.digest();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

        } catch (Exception e) {
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( new IT2PayException().getStackTrace()[0].getMethodName() ), this.getClass(), e);
        }

        return sb.toString();
    }

}
