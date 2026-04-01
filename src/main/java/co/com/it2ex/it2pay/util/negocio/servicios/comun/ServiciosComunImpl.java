package co.com.it2ex.it2pay.util.negocio.servicios.comun;


/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 4/5/2023
 */

import co.com.it2ex.it2pay.util.accesodatos.mapper.codigoserror.CodigoErrorIdiomaMapper;
import co.com.it2ex.it2pay.util.accesodatos.mapper.configuracion.GenConfiguracionMapper;
import co.com.it2ex.it2pay.util.modelo.generico.MensajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiciosComunImpl implements ServiciosComun {

	/**
	 *
	 */
	private static final long serialVersionUID = -6557402733495070473L;

	/** CodigoErrorIdiomaMapper */
	@Autowired
	private CodigoErrorIdiomaMapper codigoErrorIdiomaMapper;

	@Autowired
	private GenConfiguracionMapper genConfiguracionMapper;

	/**
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	public MensajeDTO consultarMensajePorIdioma(String codigo) throws Exception {

		if( codigo == null || codigo.equals("") ){
			MensajeDTO mensajeDto = new MensajeDTO();
			mensajeDto.setCodigo(codigo);
			mensajeDto.setMensaje("");
			return mensajeDto;
		}

		String mensaje = codigoErrorIdiomaMapper.consultarMensajePorIdioma( codigo );

		if(mensaje==null){
			mensaje = "";
		}

		MensajeDTO mensajeDto = new MensajeDTO();
		mensajeDto.setCodigo(codigo);
		mensajeDto.setMensaje(mensaje);
		return mensajeDto;
	}

	public MensajeDTO consultarConfiguracionPorCodigo(String codigo) throws Exception{
		if( codigo == null || codigo.equals("") ){
			MensajeDTO mensajeDto = new MensajeDTO();
			mensajeDto.setCodigo(codigo);
			mensajeDto.setMensaje("");
			return mensajeDto;
		}

		String mensaje = genConfiguracionMapper.consultarValorParamConfig( codigo );

		if(mensaje==null){
			mensaje = "";
		}

		MensajeDTO mensajeDto = new MensajeDTO();
		mensajeDto.setCodigo(codigo);
		mensajeDto.setMensaje(mensaje);
		return mensajeDto;
	}

}
