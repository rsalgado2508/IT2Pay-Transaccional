package co.com.it2ex.it2pay.seguridad.negocio.servicios.perfil;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 16/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.perfil.PerfilDTO;

public interface ServicioPerfil {

    public PerfilDTO consultarDatosPerfil(String login) throws Exception;


}
