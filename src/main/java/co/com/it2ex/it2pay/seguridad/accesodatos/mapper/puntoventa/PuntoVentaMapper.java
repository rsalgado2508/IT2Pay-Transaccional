/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 18/7/2023
 */

package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.puntoventa;

import co.com.it2ex.it2pay.seguridad.modelo.puntoventa.CupoPuntoVentaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.puntoventa.PuntoVentaDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PuntoVentaMapper {
    public final String CUPOS_PUNTO_VENTA = "select pln.nombre lineaNegocio, pc.cupo_total cupoGlobal, pc.cupo_usado cupoDisponible \n" +
            "from \"PDV_CUPO\" pc \n" +
            "left join \"PRO_LINEA_NEGOCIO\" pln \n" +
            "on pln.id_linea_negocio = pc.id_linea_negocio \n" +
            "where pc.estado = 'A' \n" +
            "and pc.id_punto_venta =(select id_punto_venta from \"PDV_COLOCADOR\" pc where id_persona=#{idPersona} ) ";


    public final String LISTAR_PUNTO_VENTA_ROL="select ppv.nombre nombrePuntoVenta, ppv.codigo codigo, pc.nombre cadena,\n" +
            "spe.razon_social razonSocial, spe.numero_documento usuNit,\n" +
            "spe.razon_social empresa,\n" +
            "gm.nombre municipio, gd.nombre departamento, pu.barrio, pu.direccion,\n" +
            "ppv.id_punto_venta idPuntoVenta,\n" +
            "spc.primer_nombre primerNombre, spc.segundo_nombre segundoNombre, spc.primer_apellido primerApellido, spc.segundo_apellido segundoApellido,\n" +
            "concat(spc.primer_nombre, ' ', spc.segundo_nombre, ' ', spc.primer_apellido, ' ', spc.segundo_apellido) nombreContacto,\n" +
            "spc.correo correoContacto, spc.telefono telefonoContacto, spc.celular celularContacto, spc.direccion direccionContacto,\n" +
            "std.nombre nombreTipoDocumento, spc.numero_documento numeroDocumento, gd.id_departamento idDepartamento, 5 as idRol, \n" +
            "dl.descripcion estado\n" +
            "from \"PDV_PUNTO_VENTA\" ppv\n" +
            "left join \"PDV_CADENA\" pc\n" +
            "on ppv.id_punto_venta = pc.id_punto_venta\n" +
            "left join \"SEG_PERSONA\" spe\n" +
            "on ppv.id_empresa = spe.id_persona\n" +
            "left join \"SEG_PERSONA\" spc\n" +
            "on ppv.id_contacto = spc.id_persona\n" +
            "left join \"PDV_UBICACION\" pu\n" +
            "on ppv.id_punto_venta = pu.id_punto_venta\n" +
            "left join \"GEN_MUNICIPIO\" gm\n" +
            "on pu.id_municipio = gm.id_municipio\n" +
            "left join \"GEN_DEPARTAMENTO\" gd\n" +
            "on gm.id_departamento = gd.id_departamento\n" +
            "left join \"SEG_TIPO_DOCUMENTO\" std\n" +
            "on spc.id_tipo_documento = std.id_tipo_documento\n" +
            "left join \"GEN_DETALLE_LISTA\" dl\n" +
            "on ppv.estado = dl.valor\n" +
            "where ppv.id_punto_venta = (select pc.id_punto_venta  from \"PDV_COLOCADOR\" pc inner join \"SEG_USUARIO\" su on su.id_persona = pc.id_persona where su.id_usuario = #{idUsuario})\n" +
            "order by ppv.nombre\n";

    @Select(CUPOS_PUNTO_VENTA)
    public List<CupoPuntoVentaDTO> consultaDetalle(@Param("idPersona") Long idPersona );

    @Select(LISTAR_PUNTO_VENTA_ROL)
    public List<PuntoVentaDTO> listarPuntoVentaRol(Long idUsuario);


}
