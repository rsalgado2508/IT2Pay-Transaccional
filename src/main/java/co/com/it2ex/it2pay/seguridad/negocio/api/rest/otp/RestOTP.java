package co.com.it2ex.it2pay.seguridad.negocio.api.rest.otp;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPRespuestaDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.otp.ServiciosCodigosOTP;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.components.UsuarioUtil;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesLogger;
import co.com.it2ex.it2pay.util.otros.constantes.path.ConstantesSeguridadPathRest;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class TokenController.
 */
@RestController
public class RestOTP {

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;
	
	/** The servicios codigos OTP. */
	@Autowired
	private ServiciosCodigosOTP serviciosCodigosOTP;

	private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
    
    /**
     * Val OTP.
     *
     * @param datos the datos
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_SWVALIDAROTP, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OTPRespuestaDTO> valOTP(@RequestBody OTPCodigosAutenticacionDTO datos, ModelMap model, Principal principal) throws Exception{

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
		
		OTPRespuestaDTO respuesta = null;

		long currentTimeMillis = System.currentTimeMillis();
		Date horaInicio = new Date();
    	
		try {
			DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

			datos.setIdUsuario( usuarioSesion.getIdUsuario() );
			datos.setUsuarioModificacion( usuarioSesion.getLogin() );

			respuesta = serviciosCodigosOTP.validarCodigoOTP(datos);
			
			if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

				return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.OK);	       
			} else {
				if (respuesta.getMensajeRespuesta() == null
						|| respuesta.getMensajeRespuesta().trim().equals("")) {
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
				}

				loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
						+ ": " + respuesta.getCodigoRespuesta()
						+ " - " + respuesta.getMensajeRespuesta()), this.getClass());

				return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
       } catch (Exception e) {
			if (respuesta == null) {
				respuesta = new OTPRespuestaDTO();
			}
			if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}

			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

			return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			Date horaFin = new Date();
			loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
					+ "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
					" fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
					+" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
		}

    }

	@RequestMapping(value = ConstantesSeguridadPathRest.PATH_SWVALIDAROTP_PUBLIC, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<OTPRespuestaDTO> valOTPPublic(@RequestBody OTPCodigosAutenticacionDTO datos) throws Exception{

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

		OTPRespuestaDTO respuesta = null;

		long currentTimeMillis = System.currentTimeMillis();
		Date horaInicio = new Date();

		try {

			datos.setUsuarioModificacion( datos.getLogin() );

			respuesta = serviciosCodigosOTP.validarCodigoOTP(datos);

			if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

				return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.OK);
			} else {
				if (respuesta.getMensajeRespuesta() == null
						|| respuesta.getMensajeRespuesta().trim().equals("")) {
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
				}

				loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
						+ ": " + respuesta.getCodigoRespuesta()
						+ " - " + respuesta.getMensajeRespuesta()), this.getClass());

				return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			if (respuesta == null) {
				respuesta = new OTPRespuestaDTO();
			}
			if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}

			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

			return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			Date horaFin = new Date();
			loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
					+ "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
					" fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
					+" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
		}

	}

	@RequestMapping(value = ConstantesSeguridadPathRest.PATH_SWGENERAROTP, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<OTPRespuestaDTO> getOTP(@RequestBody String url, ModelMap model, Principal principal) throws Exception{

		loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

		OTPRespuestaDTO respuesta = null;

		long currentTimeMillis = System.currentTimeMillis();
		Date horaInicio = new Date();

		try {
			DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

			respuesta = serviciosCodigosOTP.generarOTPPrivado(url, usuarioSesion);

			if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

				return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.OK);
			} else {
				if (respuesta.getMensajeRespuesta() == null
						|| respuesta.getMensajeRespuesta().trim().equals("")) {
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
				}

				loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
						+ ": " + respuesta.getCodigoRespuesta()
						+ " - " + respuesta.getMensajeRespuesta()), this.getClass());

				return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			if (respuesta == null) {
				respuesta = new OTPRespuestaDTO();
			}
			if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}

			loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

			return new ResponseEntity<OTPRespuestaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			Date horaFin = new Date();
			loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
					+ "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
					" fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
					+" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
		}

	}

	/**
	 * Format con la solución de la vulnerabilidad Concurrent Execution using Shared Resource
	 * with Improper Synchronization ('Race Condition').
	 *
	 * @param date the date
	 * @return the string
	 */
	public synchronized String format(Date date) {
		return formato.format(date);
	}
}
