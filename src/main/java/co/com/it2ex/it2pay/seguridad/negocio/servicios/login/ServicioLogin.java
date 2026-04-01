package co.com.it2ex.it2pay.seguridad.negocio.servicios.login;


/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Jorge Ruiz
 * @FechaCreación: 27/4/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.login.LoginCambioClaveDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginEntradaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginRespuestaDTO;

public interface ServicioLogin {

    public LoginRespuestaDTO validarClaveLogin(LoginEntradaDTO pCodigosAutLogin) throws Exception;

    public LoginRespuestaDTO cambiarClaveLogin(LoginCambioClaveDTO pCodigosAutLoginDTO) throws Exception;

}
