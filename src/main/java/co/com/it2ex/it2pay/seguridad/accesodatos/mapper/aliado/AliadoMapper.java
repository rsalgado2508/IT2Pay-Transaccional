/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 10/5/2023
 */

package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.aliado;

import co.com.it2ex.it2pay.seguridad.modelo.aliado.AliadoDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 21/7/2023
 */

@Mapper
public interface AliadoMapper {

    public final String CONSULTAR_ALIADOS = "select pa.id_aliado idAliado, pa.nombre nombreAliado,count(pa.id_aliado) numero,pa.imagen imagen \n" +
            "from \"PDV_PRODUCTO_PUNTO_VENTA\" pppv \n" +
            "inner join \"PRO_PRODUCTO\" pp on pppv.id_producto =pp.id_producto \n" +
            "inner join \"PRO_ALIADO\" pa on pp.id_aliado=pa.id_aliado \n" +
            "where pp.estado='A' and id_punto_venta=(select id_punto_venta from \"PDV_COLOCADOR\" pc where id_persona=#{idPersona} ) and pppv.estado='A'\n" +
            "GROUP by pa.id_aliado order by numero desc limit #{limite}";

    public final String LIMITE_ALIADOS="select valor from \"GEN_PARAMETRO\" gp where nombre='cantidad_aliados_a_mostrar_portal_tran' \n";

    @Select(CONSULTAR_ALIADOS)
    public List<AliadoDTO> consultarAliados(@Param("idPersona") Long idPersona,@Param("limite") Long limite);
    @Select(LIMITE_ALIADOS)
    public Long limiteAliados();

}

