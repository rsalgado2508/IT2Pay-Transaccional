package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

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
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.RespuestaBaseDTO;

public interface ServiciosDesbloqueo {

    public RespuestaBaseDTO desbloqueoAutomatico(Long idFuncionalidad, UsuarioDTO usuarioDto, Long idPersona)throws  Exception;

}