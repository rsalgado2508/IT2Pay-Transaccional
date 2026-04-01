package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.login;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 26/6/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.login.LoginCambioClaveDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginEntradaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginRespuestaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;

@Mapper
public interface LoginAutenticacionMapper {

    @Select("select gu.login, gu.estado, gu.acepta_terminos aceptaTerminos, gu.id_usuario idUsuario, gu.clave, sp.correo, sur.id_rol rol  " +
            "             from \"SEG_USUARIO\" gu inner join \"SEG_PERSONA\" sp ON sp.id_persona = gu.id_persona " +
            "             inner join \"SEG_USUARIO_ROL\" sur ON gu.id_usuario = sur.id_usuario AND sur.id_rol = 2 "
            + " where gu.login=#{login} ")
    public LoginRespuestaDTO validarClaveLogin(LoginEntradaDTO pCodigosAutLogin);

}
