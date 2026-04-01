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

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.otp.OTPCodigosAutenticacionMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPRespuestaDTO;
import co.com.it2ex.it2pay.util.accesodatos.mapper.funcionalidad.FuncionalidadMapper;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.FuncionalidadDTO;
import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.correo.ServicioMensajeria;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesPlantillasCorreo;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.otros.components.CodigosOtpUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The Class ServiciosCodigosOTPImpl.
 */
@Service
public class ServiciosCodigosOTPImpl implements ServiciosCodigosOTP {

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;

	@Autowired
	ServiciosComun serviciosComun;

	@Autowired
	FuncionalidadMapper funcionalidadMapper;

	@Autowired
	UsuarioMapper usuarioMapper;

	@Autowired
	ServicioMensajeria servicioMensajeria;

	/** The otp codigos autenticacion mapper. */
	@Autowired
	private OTPCodigosAutenticacionMapper otpCodigosAutenticacionMapper;

	public OTPRespuestaDTO crearCodigoOTP (OTPCodigosAutenticacionDTO codigosAutOTP)  throws Exception {

		Random rnd = new Random();
		CodigosOtpUtil otpUtil = new CodigosOtpUtil();
		OTPRespuestaDTO respuestaOTP = new OTPRespuestaDTO();

		String codigoOtpTemp = "";

		try {

			String numeroCaracteres = serviciosComun.consultarConfiguracionPorCodigo("NUMERO_CARACTERES_CODIGO_OTP").getMensaje();

			for(int i = 1; i<= Integer.parseInt(numeroCaracteres) ; i++) {
				codigoOtpTemp += Integer.toString(rnd.nextInt(9));
			}

			OTPCodigosAutenticacionDTO codigosAutOtpTemp = otpCodigosAutenticacionMapper.consultarCodigoOtpPorUsuario(codigosAutOTP);

			codigosAutOTP.setEstadoCodigoOtp("CR");
			codigosAutOTP.setCodigoOtp(otpUtil.crifrarCadena(codigoOtpTemp));

			if (codigosAutOtpTemp == null) {
				otpCodigosAutenticacionMapper.insertarNuevoCodigoOtp(codigosAutOTP);
			} else {
				otpCodigosAutenticacionMapper.actualizarCodigoOtp(codigosAutOTP);
			}

			respuestaOTP = new OTPRespuestaDTO();
			respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
			respuestaOTP.setCodigoOtp(codigoOtpTemp);


		} catch (Exception e) {

			respuestaOTP = new OTPRespuestaDTO();
			respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_OTP_01);
			respuestaOTP.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuestaOTP.getCodigoRespuesta()).getMensaje());
			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuestaOTP.getMensajeRespuesta() ), this.getClass(), e);

			return respuestaOTP;
		}

		return respuestaOTP;

	}

	public OTPRespuestaDTO validarCodigoOTP (OTPCodigosAutenticacionDTO codigosAutOTP) throws Exception {

		CodigosOtpUtil crCadena = new CodigosOtpUtil();

		OTPRespuestaDTO respuestaOTP = new OTPRespuestaDTO();

		try {

			OTPCodigosAutenticacionDTO codigosAutOtpTemp = otpCodigosAutenticacionMapper.consultarCodigoOtpPorUsuario(codigosAutOTP);

			if (codigosAutOtpTemp != null) {

				String tiempoValidez = serviciosComun.consultarConfiguracionPorCodigo("TIEMPO_VALIDEZ_CODIGO_OTP").getMensaje();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(codigosAutOtpTemp.getFechaModificacion());
				calendar.add(Calendar.MINUTE, Integer.parseInt(tiempoValidez));
				Date fechaSalidaMaxima = calendar.getTime();

				Date fechaActual = new Date();

				if (fechaActual.compareTo(fechaSalidaMaxima) <= 0 && codigosAutOtpTemp.getEstadoCodigoOtp().equalsIgnoreCase("CR")) {


					if (codigosAutOtpTemp.getCodigoOtp().equals(crCadena.crifrarCadena(codigosAutOTP.getCodigoOtp()))) {

						codigosAutOTP.setEstadoCodigoOtp("VA");
						otpCodigosAutenticacionMapper.actualizarEstadoCodigoOtp(codigosAutOTP);

						respuestaOTP = new OTPRespuestaDTO();
						respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);

					} else {

						respuestaOTP = new OTPRespuestaDTO();
						respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_OTP_04);
						respuestaOTP.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuestaOTP.getCodigoRespuesta()).getMensaje());

					}

				} else {

					codigosAutOTP.setEstadoCodigoOtp("VE");
					otpCodigosAutenticacionMapper.actualizarEstadoCodigoOtp(codigosAutOTP);

					respuestaOTP = new OTPRespuestaDTO();
					respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_OTP_03);
					respuestaOTP.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuestaOTP.getCodigoRespuesta()).getMensaje());
				}

			} else {

				respuestaOTP = new OTPRespuestaDTO();
				respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_OTP_02);
				respuestaOTP.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuestaOTP.getCodigoRespuesta()).getMensaje());

			}


		} catch (Exception e) {

			respuestaOTP = new OTPRespuestaDTO();
			respuestaOTP.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_OTP_05);
			respuestaOTP.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuestaOTP.getCodigoRespuesta()).getMensaje());
			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuestaOTP.getMensajeRespuesta() ), this.getClass(), e);

			return respuestaOTP;
		}

		return respuestaOTP;

	}

	public OTPRespuestaDTO generarOTPPrivado( String url, DatosBasicosUsuarioDTO usuarioSesion ) throws Exception {

		OTPRespuestaDTO respuesta = new OTPRespuestaDTO();

		try {

			loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

			String urlVerdadera = url != null ? url : null;

			if (urlVerdadera != null && !urlVerdadera.equals("")) {
				FuncionalidadDTO funDto = funcionalidadMapper.obtenerFuncionalidadRequiereOTPPorUrl(urlVerdadera);

				if (funDto != null) {

					if (funDto.getRequiereOtp() == null) {
						respuesta.setSolicitaOTP(false);
					} else {
						if (funDto.getRequiereOtp().equals("S")) {
							respuesta.setSolicitaOTP(true);
						} else {
							respuesta.setSolicitaOTP(false);
						}
					}

					if ( !respuesta.isSolicitaOTP() ) {
						respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
						respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
						return respuesta;
					}

					OTPCodigosAutenticacionDTO datosOTP = new OTPCodigosAutenticacionDTO();
					datosOTP.setUsuarioCreacion( usuarioSesion.getLogin() );
					datosOTP.setUsuarioModificacion( usuarioSesion.getLogin() );
					datosOTP.setIdUsuario( usuarioSesion.getIdUsuario() );

					OTPRespuestaDTO respuestaOTP = this.crearCodigoOTP(datosOTP);

					if ( respuestaOTP.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO) ) {

						String correo = usuarioMapper.consultaCorreoUsuarioPorIdUsuario( usuarioSesion );

						CorreoDTO mensaje = new CorreoDTO();

						mensaje.setPara( correo );
						mensaje.setParaNombre( correo );

						ObjectMapper objectMapper = new ObjectMapper();

						Map<String, Object> jsonObject = new HashMap<>();
						jsonObject.put("correo", correo);
						jsonObject.put("otp", respuestaOTP.getCodigoOtp());

						String json = objectMapper.writeValueAsString(jsonObject);

						mensaje.setContenido( json );
						mensaje.setAsunto( "OTP Portal Transaccional Funcionalidades" );
						mensaje.setPlantilla( ConstantesPlantillasCorreo.PLANTILLA_GENERACION_OTP_FUNCIONALIDADES_PORTAL_TRANSACCIONAL );

						loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.DEBUG, mensaje.toString(), this.getClass() );

						servicioMensajeria.envioMensaje( mensaje );

						respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
						respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
					} else {
						respuesta = new OTPRespuestaDTO();
						respuesta.setCodigoRespuesta(respuestaOTP.getCodigoRespuesta());
						respuesta.setMensajeRespuesta(respuesta.getMensajeRespuesta());
					}

				} else {
					respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
					return respuesta;
				}

			} else {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
				return respuesta;
			}


		} catch (Exception e) {
			respuesta = new OTPRespuestaDTO();
			respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
			respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
			loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
		}
		return respuesta;
	}

}