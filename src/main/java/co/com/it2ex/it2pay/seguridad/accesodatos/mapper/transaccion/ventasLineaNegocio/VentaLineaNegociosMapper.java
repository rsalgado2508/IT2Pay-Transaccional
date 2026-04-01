package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.transaccion.ventasLineaNegocio;

import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 11/08/2023
 */
@Mapper
public interface VentaLineaNegociosMapper {
    public final String LISTAR_TRANSACCION_VENTAS_ENTRE_FECHA="select id_linea_negocio idLineaNegocio ,monto monto,fecha_creacion::timestamp fechaCreacion  from \"TRA_MOVIMIENTO_TRANSACCION\" tmt  \n" +
            "where tmt.estado='K' and fecha_creacion between #{fechaInicial} and #{fechaFinal} order by fecha_creacion ";
   public final String LISTAR_NUMERO_TRANSACCION_ENTRE_FECHA="select tmt.id_linea_negocio idLineaNegocio ,DATE_TRUNC ('hour', tmt.fecha_creacion) fechaCreacion,count(tmt.id_movimiento_transaccion) monto  from \"TRA_MOVIMIENTO_TRANSACCION\" tmt \n" +
            "where tmt.estado='K' and fecha_creacion between #{fechaInicial} and #{fechaFinal} \n" +
            "GROUP BY DATE_TRUNC('hour', tmt.fecha_creacion),id_linea_negocio\n" +
            "order by DATE_TRUNC('hour', tmt.fecha_creacion) ";

    public final String LISTAR_TRANSACCION_VENTAS_ENTRE_FECHA_PUNTO_VENTA="select id_linea_negocio idLineaNegocio ,monto monto,fecha_creacion::timestamp fechaCreacion  from \"TRA_MOVIMIENTO_TRANSACCION\" tmt  \n" +
            "where tmt.estado='K' and tmt.id_punto_venta=#{pVenta} and fecha_creacion between #{fechaInicial} and #{fechaFinal} order by fecha_creacion ";
    public final String LISTAR_NUMERO_TRANSACCION_ENTRE_FECHA_PUNTO_VENTA="select tmt.id_linea_negocio idLineaNegocio ,DATE_TRUNC ('hour', tmt.fecha_creacion) fechaCreacion,count(tmt.id_movimiento_transaccion) monto  from \"TRA_MOVIMIENTO_TRANSACCION\" tmt \n" +
            "where tmt.estado='K' and tmt.id_punto_venta=#{pVenta} and fecha_creacion between #{fechaInicial} and #{fechaFinal} \n" +
            "GROUP BY DATE_TRUNC('hour', tmt.fecha_creacion),id_linea_negocio\n" +
            "order by DATE_TRUNC('hour', tmt.fecha_creacion) ";



    @Select(LISTAR_NUMERO_TRANSACCION_ENTRE_FECHA)
    public List<MovimientoTransaccionDTO> listarNumeroMovTransaccionesEntreFechas(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);
    @Select(LISTAR_TRANSACCION_VENTAS_ENTRE_FECHA)
    public List<MovimientoTransaccionDTO> listarMovTransaccionesEntreFechas(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

    @Select(LISTAR_TRANSACCION_VENTAS_ENTRE_FECHA_PUNTO_VENTA)
    public List<MovimientoTransaccionDTO> listarMovTransaccionesEntreFechasPuntoVenta(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal,@Param("pVenta") Long pVenta);

    @Select(LISTAR_NUMERO_TRANSACCION_ENTRE_FECHA_PUNTO_VENTA)
    public List<MovimientoTransaccionDTO> listarNumeroMovTransaccionesEntreFechasPuntoVenta(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal,@Param("pVenta") Long pVenta);


}
