package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 9/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.token.DatosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;

public interface ServicioValidacionUsuario {

    public DatosAutenticacionDTO validarUsuario(DatosAutenticacionDTO usuario) throws Exception;

    public DatosBasicosUsuarioDTO validarContrasena(UsuarioDTO usuario) throws Exception;


}