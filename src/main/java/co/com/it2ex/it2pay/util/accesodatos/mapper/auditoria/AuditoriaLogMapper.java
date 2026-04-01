package co.com.it2ex.it2pay.util.accesodatos.mapper.auditoria;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/8/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;


@Mapper
public interface AuditoriaLogMapper {

    @Cacheable("consultarFuncionalidadPorURL")
    @Select("select sf.id_funcionalidad id from \"SEG_FUNCIONALIDAD\" sf where sf.url = #{ estado }")
    public Long consultarFuncionalidadPorURL( @Param("estado") String estado );

    @Insert("INSERT INTO public.\"SEG_AUDITORIA\" " +
            "( id_funcionalidad, id_rol, descripcion, ip, parametros, estado, clase, fecha_creacion, usuario_creacion, fecha, hora ) " +
            "VALUES( #{ idFuncionalidadAuditoria }, #{ idRol }, #{ descripcion }, #{ ip }, #{ parametros }, #{ estadoAuditoria }, #{ clase }, current_timestamp, #{ usuarioCreacion }, #{ fecha }, #{ hora } )")
    @Options(useGeneratedKeys=true, keyProperty="idAuditoria")
    public void insertarAuditoria( AuditoriaDTO dto );

}

