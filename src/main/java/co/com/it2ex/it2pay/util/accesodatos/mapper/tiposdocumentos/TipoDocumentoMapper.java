package co.com.it2ex.it2pay.util.accesodatos.mapper.tiposdocumentos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 4/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.tiposdocumentos.TipoDocumentoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Mapper
public interface TipoDocumentoMapper {

    @Cacheable("obtenerTiposDeDocumento")
    @Select("select std.id_tipo_documento idTipoDocumento, std.nombre, std.naturaleza from \"SEG_TIPO_DOCUMENTO\" std order by std.nombre")
    public List<TipoDocumentoDTO> obtenerTiposDeDocumento();



}
