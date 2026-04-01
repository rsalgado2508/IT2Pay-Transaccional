package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.pagos;/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 28/10/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.pagos.ServicioDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CodigoRespuestaMapper {

    @Select(" select scr.codigo " +
            " from \"SER_CODIGO_RESPUESTA\" scr " +
            " where scr.es_exitoso = 'S' and scr.estado = 'A' and scr.id_servicio = #{idServicio} ")
    public List<String> consultarCodigosRespOK (@Param("idServicio") Long idServicio);

}
