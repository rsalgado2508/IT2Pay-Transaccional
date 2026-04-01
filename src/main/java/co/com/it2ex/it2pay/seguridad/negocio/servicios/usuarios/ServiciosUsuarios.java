package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;
/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 28/4/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

public interface ServiciosUsuarios {

    public ListaDTO listarUsuarios() throws Exception;

    public UsuarioDTO consultarUsuarioPorId(UsuarioDTO params) throws Exception;

    public BaseDTO cambiarEstadoUsuario (UsuarioDTO params)  throws Exception;

    public BaseDTO desbloquearUsuario (UsuarioDTO params)  throws Exception;

    public String cargarUltimaConexion(Long idUsuario) throws Exception;

    public String cargarFechaActual() throws Exception;

    public UsuarioDTO guardarUltimaConexion(String login) throws Exception;

    public String obtenerNombreRol( Long id )  throws Exception;

    public UsuarioDTO actualizarClaveSinValidacion(UsuarioDTO dto) throws Exception;

    public UsuarioDTO actualizarClave(UsuarioDTO dto, DatosBasicosUsuarioDTO usuarioSesion) throws Exception;


}
