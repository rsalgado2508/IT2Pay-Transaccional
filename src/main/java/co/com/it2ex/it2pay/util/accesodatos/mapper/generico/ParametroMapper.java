package co.com.it2ex.it2pay.util.accesodatos.mapper.generico;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 18/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.*;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface ParametroMapper {

    @Cacheable("consultarParametros")
    @Select(" select gp.id_parametro idParametro, gp.nombre nombreParametro, " +
            "gp.descripcion descripcionParametro, gp.expresion, gp.valor, " +
            "gp.valores, gp.estado, gp.orden ordenParametro " +
            "from \"GEN_PARAMETRO\" gp " +
            "where gp.estado = 'A' and gp.id_grupo_parametro = #{idGrupoParametro} " +
            "order by gp.orden asc ")
    public List<ParametroDTO> consultarParametros (@Param("idGrupoParametro") Long idGrupoParametro);

    @Cacheable("consultarGruposParametros")
    @Select(" select distinct ggp.id_grupo_parametro idGrupoParametro, ggp.nombre nombreGrupoParametro, ggp.descripcion descripcionGrupoParametro, ggp.orden " +
            "   from \"GEN_GRUPO_PARAMETRO\" ggp " +
            "   order by ggp.orden asc ")
    public List<ParametroDTO> consultarGruposParametros ();

    @CacheEvict(cacheNames = "consultarParametros", allEntries = true)
    @Update("UPDATE \"GEN_PARAMETRO\" " +
            "SET nombre=#{nombreParametro}, descripcion=#{descripcionParametro}, estado=#{estado}, " +
            "expresion=#{expresion}, valores=#{valores}, orden=#{ordenParametro}, valor=#{valor}, " +
            "fecha_modificacion=CURRENT_TIMESTAMP, usuario_modificacion=#{usuarioModificacion} " +
            "WHERE id_parametro = #{idParametro} ")
    public void modificarParametro(ParametroDTO parametroDTO);

    @Select("select NOMBRE nombre, VALOR valor , EXPRESION expresion, VALORES valores "
            + "from \"GEN_PARAMETRO\" "
            + "WHERE NOMBRE = #{nombre} ")
    public ParametroConsultaDTO selectValorParametroPorNombre(ParametroConsultaDTO dto);

    @Select("select NOMBRE nombre, VALOR valor , EXPRESION expresion, VALORES valores "
            + "from \"GEN_PARAMETRO\" "
            + "WHERE ID_PARAMETRO = #{id} ")
    public ParametroConsultaDTO selectValorParametroPorId(ParametroConsultaDTO dto);

}
