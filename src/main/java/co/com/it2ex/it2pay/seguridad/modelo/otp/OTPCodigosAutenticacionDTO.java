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

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class OTPCodigosAutenticacionDTO extends AuditoriaDTO implements Serializable{
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5013174623145558651L;

	/** The id registro otp. */
	private Long idRegistroOtp;

	/** The idUsuario. */
	private Long idUsuario;

	private String login;

	/** The codigo otp. */
	private String codigoOtp;
	
	/** The estado codigo otp. */
	private String estadoCodigoOtp;
	
}