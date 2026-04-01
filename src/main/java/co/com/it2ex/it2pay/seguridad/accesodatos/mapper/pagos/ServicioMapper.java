package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.pagos;/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 27/10/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.pagos.ServicioDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ServicioMapper {

    @Select(" select ss.nombre, ss.id_servicio idServicio, ss.endpoint, ss.tipo_consumo tipoConsumo, ss.seguridad, ss.timeout, " +
            " ss.metodo, ss.cantidad_reintentos cantidadReintentos, ss.usuario, ss.clave, ss.solicitud " +
            " from \"SER_SERVICIO\" ss where ss.estado = 'A' and ss.id_aliado = #{idAliado} and ss.tipo_consumo = #{tipoConsumo} ")
    public ServicioDTO consultarServicioPorAliado(@Param("idAliado") Long idAliado, @Param("tipoConsumo") String tipoConsumo);
}
