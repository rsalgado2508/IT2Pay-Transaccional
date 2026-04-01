package co.com.it2ex.it2pay.seguridad.negocio.servicios.aliado;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 21/7/2023
 */


import co.com.it2ex.it2pay.seguridad.modelo.aliado.AliadoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.aliado.AliadoRespuestaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

/**
 * The Interface ServiciosColocador.
 */
public interface ServiciosAliado {


	public ListaDTO consultarListaAliados(Long idPersona) throws Exception;


}