package co.com.it2ex.it2pay.seguridad.negocio.servicios.puntoventa;
/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional backend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 18/7/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

public interface ServicioPuntoVenta {

    public ListaDTO consultaCupo(Long idPuntoVenta) throws Exception;

}
