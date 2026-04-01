package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.recuperarclave;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 25/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarEntradaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarRespuestaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecuperarClaveMapper {

    @Select("select gu.login, gu.id_usuario idUsuario, sp.correo  " +
            "             from \"SEG_USUARIO\" gu inner join \"SEG_PERSONA\" sp ON sp.id_persona = gu.id_persona " +
            " where gu.login=#{login} AND sp.numero_documento=#{numeroDocumento} AND sp.id_tipo_documento=#{tipoDocumento}  ")
    public RecuperarRespuestaDTO validarLoginDocumento(RecuperarEntradaDTO dto);

}
