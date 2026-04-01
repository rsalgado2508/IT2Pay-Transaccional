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

import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;

import java.io.Serializable;

public interface ServicioMensajeria extends Serializable{

	public CorreoDTO envioMensaje(CorreoDTO dto) throws Exception;

}