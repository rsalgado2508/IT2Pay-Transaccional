package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 27/4/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

public interface ServiciosTiposDocumento {

    public ListaDTO consultarTiposDeDocumento() throws Exception;

}
