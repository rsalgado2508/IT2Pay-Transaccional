package co.com.it2ex.it2pay.seguridad.modelo.otp;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class OTPRespuestaDTO implements Serializable{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3928763216954140756L;
	
	/** The codigo respuesta. */
	private String codigoRespuesta;	
	
	/** The descripcion respuesta. */
	private String mensajeRespuesta;
	
	/** The codigo otp. */
	private String codigoOtp;

	private boolean solicitaOTP;
	
}