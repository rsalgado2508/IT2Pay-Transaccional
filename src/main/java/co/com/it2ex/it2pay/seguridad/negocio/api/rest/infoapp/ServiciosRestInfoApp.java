package co.com.it2ex.it2pay.seguridad.negocio.api.rest.infoapp;

import co.com.it2ex.it2pay.seguridad.modelo.infoapp.InfoAppDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.infoapp.ServiciosInfoApp;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ServiciosRestInfoApp {

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;

	@Autowired
	private ServiciosInfoApp serviciosInfoApp;

	private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");

		
	@RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTA_SYSINFO_APP, method = RequestMethod.GET,  headers = "Accept="+MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<InfoAppDTO> consultarInfoApp( )  throws Exception{

		loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

		long currentTimeMillis = System.currentTimeMillis();
		Date horaInicio = new Date();
		InfoAppDTO respuesta = null;

		try {
			respuesta = serviciosInfoApp.consultarInfoApp();
			if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

				return new ResponseEntity<InfoAppDTO>(respuesta, HttpStatus.OK);

			} else {
				if (respuesta.getMensajeRespuesta() == null
						|| respuesta.getMensajeRespuesta().trim().equals("")) {
					respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
				}

				loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
						+ ": " + respuesta.getCodigoRespuesta()
						+ " - " + respuesta.getMensajeRespuesta()), this.getClass());



				return new ResponseEntity<InfoAppDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			if (respuesta == null) {
				respuesta = new InfoAppDTO();
			}
			if (respuesta.getCodigoRespuesta() == null || respuesta.getCodigoRespuesta().equals("")) {
				respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
				respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);
			}

			loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

			return new ResponseEntity<InfoAppDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);


		} finally {
			Date horaFin = new Date();
			loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase " + this.getClass().getSimpleName()
					+ "- Metodo -" + new IT2PayException().getStackTrace()[0].getMethodName() +
					" fue de " + ((System.currentTimeMillis() - currentTimeMillis) / 1000d)
					+ " segundos - Hora inicio " + horaInicio + " - Hora fin " + format(horaFin)), this.getClass());

		}
	}

	public synchronized String format(Date date) {
		return formato.format(date);
	}

}
