/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 25/7/2023
 */

package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.producto;


import co.com.it2ex.it2pay.seguridad.modelo.producto.ProductoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProductoMapper {

    public final String CONSULTAR_PRODUCTOS = "select pp.id_producto idProducto,pp.nombre nommbreProducto, pa.id_aliado idAliado, pa.nombre nombreAliado, pa.imagen imagen  " +
            "from \"PDV_PRODUCTO_PUNTO_VENTA\" pppv \n" +
            "inner join \"PRO_PRODUCTO\" pp on pppv.id_producto =pp.id_producto \n" +
            "inner join \"PRO_ALIADO\" pa on pp.id_aliado=pa.id_aliado  \n" +
            "where id_punto_venta=(select id_punto_venta from \"PDV_COLOCADOR\" pc where id_persona=#{idPersona} ) and pppv.estado='A' and pp.estado='A' \n" +
            "order by pp.nombre";


    @Select(CONSULTAR_PRODUCTOS)
    public List<ProductoDTO> consultarProductos(@Param("idPersona") Long idPersona);

    @Select(" select pp.id_producto idProducto, pp.nombre nommbreProducto, pa.id_aliado idAliado, pa.nombre nombreAliado, pa.imagen imagen  " +
            " from \"PDV_PRODUCTO_PUNTO_VENTA\" pppv  " +
            " inner join \"PRO_PRODUCTO\" pp on pppv.id_producto =pp.id_producto  " +
            " inner join \"PRO_ALIADO\" pa on pp.id_aliado=pa.id_aliado " +
            " where id_punto_venta=(select id_punto_venta from \"PDV_COLOCADOR\" pc where id_persona= #{idPersona} ) " +
            " and pa.id_aliado = #{idAliado} " +
            " order by pp.nombre ")
    public List<ProductoDTO> consultarProductosPorAliado(UsuarioDTO usuarioDTO);

    @Select(" select pp.id_producto idProducto, pp.nombre nommbreProducto, pp.codigo_propio codigoPropioProducto, pp.id_linea_negocio idLineaNegocioProducto, " +
            " pp.id_tipo_producto idTipoProducto, pp.id_categoria idCategoriaProducto, pp.lectura_codigo_barras lecturaCodigoBarras, " +
            " pp.acepta_facturas_vencidas aceptaFacturasVencidas, pp.acepta_pagos_parciales aceptaPagosParciales, pp.acepta_pagos_dobles, " +
            " pp.monto_minimo montoMinimo, pp.monto_maximo montoMaximo, pp.ventana_operacion_inicio ventanaOperacionInicio, " +
            " pp.ventana_operacion_final ventanaOperacionFinal, pp.permite_anulacion permiteAnulacion, pp.referencia_captura referenciaCaptura, " +
            " pp.monto_editable montoEditable, pp.multiplos_pagos multiplosPagos, pp.mensaje, " +
            " pp.fijo, pp.variable " +
            " from \"PRO_PRODUCTO\" pp " +
            " where pp.id_aliado = #{idAliado} and pp.id_producto = #{idProducto} ")
    public ProductoDTO consultarDetalleProductoPorAliado(UsuarioDTO usuarioDTO);


}

