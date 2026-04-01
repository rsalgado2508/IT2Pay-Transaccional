package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 25/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.RolDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface RolMapper {

    @Cacheable("consultarRoles")
    @Select(" select sr.id_rol idRol, sr.nombre nombreRol, sr.estado from \"SEG_ROL\" sr ")
    public List<RolDTO> consultarRoles ();

}
