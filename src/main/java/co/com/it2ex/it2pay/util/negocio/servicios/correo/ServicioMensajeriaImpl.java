package co.com.it2ex.it2pay.util.negocio.servicios.correo;


/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/5/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.otp.OTPCodigosAutenticacionMapper;
import co.com.it2ex.it2pay.util.modelo.mensajeria.ContenidoFuncionLambdaCorreoDTO;
import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.lambda.cliente.CorreoLambdaInvoker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioMensajeriaImpl implements ServicioMensajeria {

	@Autowired
	private OTPCodigosAutenticacionMapper otpCodigosAutenticacionMapper;

	@Autowired
	private ServicioCorreoSpring servicioCorreoSpring;

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;

	@Autowired
	ServiciosComun serviciosComun;

	public CorreoDTO envioMensaje(CorreoDTO dto) throws Exception {

		String tipoEnvioCorreo = serviciosComun.consultarConfiguracionPorCodigo("TIPO_ENVIO_CORREO").getMensaje();

		CorreoDTO mensajeDto = new CorreoDTO();

		if ( tipoEnvioCorreo.equals( "AWS" ) ) {

			String url = serviciosComun.consultarConfiguracionPorCodigo("URL_LAMBDA_CORREO").getMensaje();

			CorreoLambdaInvoker lambda = new CorreoLambdaInvoker();

			ContenidoFuncionLambdaCorreoDTO dtoLambda = new ContenidoFuncionLambdaCorreoDTO();

			String sender = serviciosComun.consultarConfiguracionPorCodigo("SENDER_LAMBDA_CORREO").getMensaje();

			dtoLambda.setRecipient( dto.getPara() );
			dtoLambda.setSender( sender );
			dtoLambda.setTemplateName( dto.getPlantilla() );
			dtoLambda.setValues( dto.getContenido() );

			ObjectMapper mapper = new ObjectMapper();

			String json = mapper.writeValueAsString( dtoLambda );

			lambda.envioCorreo( loggerAuditoriasComponent, url, json );

		} else if ( tipoEnvioCorreo.equals( "JAVA" ) ) {

			servicioCorreoSpring.enviarCorreo( dto );

		}

		return mensajeDto;
	}

}
