package co.com.it2ex.it2pay.util.otros.components;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 26/4/2023
 */

import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class CodigosOtpUtil.
 */
public class CodigosOtpUtil {

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;
		
	/**
	 * Crifrar cadena.
	 *
	 * @param cadena the cadena
	 * @return the string
	 */
	public String crifrarCadena (String cadena){
		
		String returnCadena = "";
		
		try {
			
			returnCadena = DigestUtils.sha256Hex(cadena);
			
		} catch (Exception e) {
			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( new IT2PayException().getStackTrace()[0].getMethodName() ), this.getClass(), e);
		}
		
		return returnCadena;
	}

}