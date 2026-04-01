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

import java.io.Serializable;

import co.com.it2ex.it2pay.util.modelo.generico.MensajeDTO;

public interface ServiciosComun extends Serializable{

	public MensajeDTO consultarMensajePorIdioma(String codigo) throws Exception;

	public MensajeDTO consultarConfiguracionPorCodigo(String codigo) throws Exception;

}