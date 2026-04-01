package co.com.it2ex.it2pay.util.negocio.servicios.log;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/8/2023
 */



import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;

public interface ServiciosAuditoriaLog {


	public void insertarAuditoria(AuditoriaDTO dto) throws Exception;


}