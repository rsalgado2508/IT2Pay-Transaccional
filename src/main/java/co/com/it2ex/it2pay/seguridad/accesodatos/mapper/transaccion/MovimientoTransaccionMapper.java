package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.transaccion;

import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo backend
 * @Copyright IT2Ex
 *
 * @Autor: jlalfonso
 * @FechaCreación: 27/07/2023
 */
@Mapper
public interface MovimientoTransaccionMapper {
    public final String LISTAR_TRANSACCION="SELECT\n" +
            "    t.id_reporte AS idReporte,\n" +
            "    t.id_transaccion AS idTransaccion,\n" +
            "    pln.nombre AS nombreLineaNegocio,\n" +
            "    pa.nombre AS nombreAliado,\n" +
            "    ppv.codigo AS codigoPuntoVenta,\n" +
            "    pp.codigo_propio AS codigoProducto,\n" +
            "    CONCAT(p.primer_nombre, ' ', p.primer_apellido) AS colocador,\n" +
            "    CONCAT(pu.direccion, ' ', INITCAP(gm.nombre), ', ', INITCAP(gd.nombre)) AS ubicacionPuntoVenta,\n" +
            "    pp.nombre AS nombreProducto,\n" +
            "    t.monto, t.codigo_error error, t.mensaje_error mensajeError,\n" +
            "    t.moneda,\n" +
            "    t.fecha_inicio AS fechaInicio,\n" +
            "    '' AS correoEnvioComprobante,\n" +
            "    ppv.nombre AS nombrePuntoVenta,\n" +
            "    cn.codigo AS codigoConvenio,\n" +
            "    cn.nombre AS nombreConvenio,\n" +
            "    t.fecha_fin AS fechaFin,\n" +
            "    gdl.descripcion AS estado,\n" +
            "    CONCAT(sp.primer_nombre, ' ', sp.primer_apellido) AS nombreColocador,\n" +
            "    sp.id_persona, pp.mensaje,\n" +
            "    t.id_movimiento_transaccion AS idMovimientoTransaccion,\n" +
            "    t.cliente,t.referencia factura,\n" +
            "    STRING_AGG(ppr.nombre, ', ') AS referencia\n" +
            "FROM \"TRA_MOVIMIENTO_TRANSACCION\" t\n" +
            "JOIN \"PRO_LINEA_NEGOCIO\" pln ON t.id_linea_negocio = pln.id_linea_negocio\n" +
            "JOIN \"PRO_ALIADO\" pa ON t.id_aliado = pa.id_aliado\n" +
            "JOIN \"PRO_PRODUCTO\" pp ON t.id_producto = pp.id_producto\n" +
            "JOIN \"PRO_CONVENIO\" cn ON cn.id_producto = t.id_producto\n" +
            "JOIN \"PDV_PUNTO_VENTA\" ppv ON t.id_punto_venta = ppv.id_punto_venta\n" +
            "JOIN \"PDV_COLOCADOR\" pc ON t.id_colocador = pc.id_persona AND pc.id_punto_venta = t.id_punto_venta \n" +
            "JOIN \"SEG_PERSONA\" sp ON pc.id_persona = sp.id_persona\n" +
            "JOIN \"GEN_DETALLE_LISTA\" gdl ON t.estado = gdl.valor\n" +
            "JOIN \"PDV_UBICACION\" pu ON pu.id_punto_venta = t.id_punto_venta\n" +
            "JOIN \"SEG_PERSONA\" p ON p.id_persona = pc.id_persona\n" +
            "JOIN \"GEN_MUNICIPIO\" gm ON gm.id_municipio = pu.id_municipio\n" +
            "JOIN \"GEN_DEPARTAMENTO\" gd ON gd.id_departamento = gm.id_departamento\n" +
            "JOIN \"PRO_PRODUCTO_REFERENCIA\" ppr ON ppr.id_producto = t.id_producto\n" +
            "WHERE t.fecha_inicio >= CURRENT_DATE\n" +
            "AND t.fecha_fin < CURRENT_DATE + INTERVAL '1 day' AND gdl.id_lista = 9\n" +
            "GROUP BY\n" +
            "    t.id_reporte,\n" +
            "    t.id_transaccion,\n" +
            "    pln.nombre,\n" +
            "    pa.nombre,\n" +
            "    ppv.codigo,\n" +
            "    pp.codigo_propio,\n" +
            "    CONCAT(p.primer_nombre, ' ', p.primer_apellido),\n" +
            "    CONCAT(pu.direccion, ' ', INITCAP(gm.nombre), ', ', INITCAP(gd.nombre)),\n" +
            "    pp.nombre,\n" +
            "    t.monto, t.codigo_error, t.mensaje_error,\n" +
            "    t.moneda,\n" +
            "    t.fecha_inicio,\n" +
            "    t.fecha_fin,\n" +
            "    gdl.descripcion,\n" +
            "    CONCAT(sp.primer_nombre, ' ', sp.primer_apellido),\n" +
            "    sp.id_persona,\n" +
            "    t.id_movimiento_transaccion,\n" +
            "    PPV.nombre,\n" +
            "    cn.nombre , pp.mensaje,\n" +
            "    cn.codigo,\n" +
            "    t.cliente;\n";
    public final String CONSULTAR_NUMERO_DIAS ="select gp.valor numeroDias from \"GEN_PARAMETRO\" gp \n" +
            "where nombre='numero_de_dias_transacciones'";
    public final String CONSULTAR_TRANSACCIONES_FECHA = "SELECT\n" +
            "    t.id_reporte AS idReporte,\n" +
            "    t.id_transaccion AS idTransaccion,\n" +
            "    pln.nombre AS nombreLineaNegocio,\n" +
            "    pa.nombre AS nombreAliado,\n" +
            "    ppv.codigo AS codigoPuntoVenta,\n" +
            "    pp.codigo_propio AS codigoProducto,\n" +
            "    CONCAT(p.primer_nombre, ' ', p.primer_apellido) AS colocador,\n" +
            "    CONCAT(pu.direccion, ' ', INITCAP(gm.nombre), ', ', INITCAP(gd.nombre)) AS ubicacionPuntoVenta,\n" +
            "    pp.nombre AS nombreProducto,\n" +
            "    t.monto, pp.mensaje,\n" +
            "    t.moneda, t.codigo_error error, t.mensaje_error mensajeError,\n" +
            "    t.fecha_inicio AS fechaInicio,\n" +
            "    '' AS correoEnvioComprobante,\n" +
            "    ppv.nombre AS nombrePuntoVenta,\n" +
            "    cn.codigo AS codigoConvenio,\n" +
            "    cn.nombre AS nombreConvenio,\n" +
            "    t.fecha_fin AS fechaFin,\n" +
            "    gdl.descripcion AS estado,\n" +
            "    CONCAT(sp.primer_nombre, ' ', sp.primer_apellido) AS nombreColocador,\n" +
            "    sp.id_persona,\n" +
            "    t.id_movimiento_transaccion AS idMovimientoTransaccion,\n" +
            "    t.cliente,t.referencia factura,\n" +
            "    STRING_AGG(ppr.nombre, ', ') AS referencia\n" +
            "FROM \"TRA_MOVIMIENTO_TRANSACCION\" t\n" +
            "JOIN \"PRO_LINEA_NEGOCIO\" pln ON t.id_linea_negocio = pln.id_linea_negocio\n" +
            "JOIN \"PRO_ALIADO\" pa ON t.id_aliado = pa.id_aliado\n" +
            "JOIN \"PRO_PRODUCTO\" pp ON t.id_producto = pp.id_producto\n" +
            "LEFT JOIN \"PRO_CONVENIO\" cn ON cn.id_producto = t.id_producto\n" +
            "JOIN \"PDV_PUNTO_VENTA\" ppv ON t.id_punto_venta = ppv.id_punto_venta\n" +
            "JOIN \"PDV_COLOCADOR\" pc ON t.id_colocador = pc.id_persona AND pc.id_punto_venta = t.id_punto_venta \n" +
            "JOIN \"SEG_PERSONA\" sp ON pc.id_persona = sp.id_persona\n" +
            "JOIN \"GEN_DETALLE_LISTA\" gdl ON t.estado = gdl.valor\n" +
            "JOIN \"PDV_UBICACION\" pu ON pu.id_punto_venta = t.id_punto_venta\n" +
            "JOIN \"SEG_PERSONA\" p ON p.id_persona = pc.id_persona\n" +
            "JOIN \"GEN_MUNICIPIO\" gm ON gm.id_municipio = pu.id_municipio\n" +
            "JOIN \"GEN_DEPARTAMENTO\" gd ON gd.id_departamento = gm.id_departamento\n" +
            "JOIN \"PRO_PRODUCTO_REFERENCIA\" ppr ON ppr.id_producto = t.id_producto\n" +
            "WHERE t.fecha_inicio >= #{fechaInicial}\n" +
            "AND t.fecha_fin < #{fechaFinal}::DATE + INTERVAL '1 day' AND gdl.id_lista = 9\n" +
            "GROUP BY\n" +
            "    t.id_reporte,\n" +
            "    t.id_transaccion,\n" +
            "    pln.nombre,\n" +
            "    pa.nombre, t.codigo_error, t.mensaje_error,\n" +
            "    ppv.codigo,\n" +
            "    pp.codigo_propio,\n" +
            "    CONCAT(p.primer_nombre, ' ', p.primer_apellido),\n" +
            "    CONCAT(pu.direccion, ' ', INITCAP(gm.nombre), ', ', INITCAP(gd.nombre)),\n" +
            "    pp.nombre,\n" +
            "    t.monto, pp.mensaje,\n" +
            "    t.moneda,\n" +
            "    t.fecha_inicio,\n" +
            "    t.fecha_fin,\n" +
            "    gdl.descripcion,\n" +
            "    CONCAT(sp.primer_nombre, ' ', sp.primer_apellido),\n" +
            "    sp.id_persona,\n" +
            "    t.id_movimiento_transaccion,\n" +
            "    PPV.nombre,\n" +
            "    cn.nombre ,\n" +
            "    cn.codigo,\n" +
            "    t.cliente;\n";

    public final String REVERSAR_TRANSACCION = "UPDATE public.\"TRA_MOVIMIENTO_TRANSACCION\"\n" +
            "SET estado='V', fecha_modificacion=current_timestamp, usuario_modificacion=#{usuarioModificacion}\n" +
            "WHERE id_movimiento_transaccion=#{idMovimientoTransaccion}";

    @Select(LISTAR_TRANSACCION)
    public List<MovimientoTransaccionDTO> listarMovTransacciones();
    @Select(CONSULTAR_NUMERO_DIAS)
    public List<MovimientoTransaccionDTO> consultarNumeroDias();
    @Select(CONSULTAR_TRANSACCIONES_FECHA)
    public List<MovimientoTransaccionDTO> consultarTransaccionesFecha(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);
    @Update(value = REVERSAR_TRANSACCION)
    public void reversarTransaccion(MovimientoTransaccionDTO MovimientoTransaccionDTO);


}
