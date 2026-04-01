package co.com.it2ex.it2pay.seguridad.negocio.servicios.otp;

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

import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPRespuestaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;

/**
 * The Interface ServiciosCodigosOTP.
 */
public interface ServiciosCodigosOTP {
	

	/**
	 * Crear codigo OTP.
	 *
	 * @param datos the datos
	 * @return the OTP respuesta DTO
	 * @throws Exception the exception
	 */
	public OTPRespuestaDTO crearCodigoOTP (OTPCodigosAutenticacionDTO datos)  throws Exception;
	
	/**
	 * Validar codigo OTP.
	 *
	 * @param datos the datos
	 * @return the OTP respuesta DTO
	 * @throws Exception the exception
	 */
	public OTPRespuestaDTO validarCodigoOTP (OTPCodigosAutenticacionDTO datos) throws Exception;

	public OTPRespuestaDTO generarOTPPrivado(String url, DatosBasicosUsuarioDTO usuarioSesion) throws Exception;

}