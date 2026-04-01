package co.com.it2ex.it2pay.seguridad.negocio.servicios.terminos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 2/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.terminos.TerminosDTO;

public interface ServicioTerminos {

    public TerminosDTO consultarTerminosYCondicionesActivo() throws Exception;

    public TerminosDTO actualizarAceptarTerminosYCondiciones(TerminosDTO dto) throws Exception;

}