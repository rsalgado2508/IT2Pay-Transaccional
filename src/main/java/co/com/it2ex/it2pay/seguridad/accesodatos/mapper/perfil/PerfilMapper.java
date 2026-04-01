package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.perfil;

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
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@Mapper
public interface PerfilMapper {

    @Select("select std.nombre tipoDocumento, sp.numero_documento numeroDocumento, " +
            "sp.primer_nombre primerNombre, sp.segundo_nombre segundoNombre, sp.primer_apellido primerApellido, sp.segundo_apellido segundoApellido, " +
            "sp.telefono celular, sp.correo email, sp.estado estado, sr.nombre nombreRol  " +
            "from  \"SEG_USUARIO\" su " +
            "inner join \"SEG_PERSONA\" sp on su.id_persona = sp.id_persona " +
            "inner join \"SEG_TIPO_DOCUMENTO\" std on std.id_tipo_documento = sp.id_tipo_documento " +
            "inner join \"SEG_USUARIO_ROL\" sur on sur.id_usuario = su.id_usuario " +
            "inner join \"SEG_ROL\" sr on sr.id_rol = sur.id_rol " +
            "where su.login = #{login} ")
    public PerfilDTO consultarDatosPerfil( @Param("login") String login );


}
