package co.com.it2ex.it2pay.util.accesodatos.mapper.funcionalidad;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 13/6/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.FuncionalidadDTO;
import co.com.it2ex.it2pay.util.modelo.session.ParametrosValidacionURLDTO;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;


@Mapper
public interface FuncionalidadMapper {

	
	@Select("Select fun.id_funcionalidad from \"SEG_FUNCIONALIDAD\" fun where fun.url = #{urlFuncionalidad} limit 1 ")
	public Long consultarIdFuncionalidadPorUrl(ParametrosValidacionURLDTO parametros);
	
	@Select("Select fun.id_funcionalidad from \"SEG_FUNCIONALIDAD\" fun where fun.url like #{urlFuncionalidad} limit 1 ")
	public Long consultarIdFuncionalidadPorUrlConParametro(ParametrosValidacionURLDTO parametros);

	@Cacheable("obtenerFuncionalidadRequiereOTPPorUrl")
	@Select("select ID_FUNCIONALIDAD idFuncionalidad, "
			+ "NOMBRE nombre, "
			+ "URL url, "
			+ "REQUIERE_OTP requiereOtp  " +
			"            FROM \"SEG_FUNCIONALIDAD\""
			+ "WHERE URL = #{url} AND estado = 'A'")
	public FuncionalidadDTO obtenerFuncionalidadRequiereOTPPorUrl(@Param("url") String url);

}
