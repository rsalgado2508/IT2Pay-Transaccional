package co.com.it2ex.it2pay.util.negocio.servicios.listadetalle;

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

import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

public interface ServicioListaDetalle {

    public ListaDTO consultaListaDetallePorId(Long id) throws Exception;


}