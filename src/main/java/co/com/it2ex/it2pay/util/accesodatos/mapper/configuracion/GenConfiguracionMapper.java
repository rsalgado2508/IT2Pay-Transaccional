package co.com.it2ex.it2pay.util.accesodatos.mapper.configuracion;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/5/2023
 */

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;


@Mapper
public interface GenConfiguracionMapper {

	/** The consultar param gen config. */
	public final String CONSULTAR_PARAM_GEN_CONFIG = "SELECT g.valor FROM \"GEN_CONFIGURACION\" g WHERE g.id_configuracion = #{paramId}";

	/**
	 * Consultar valor param config.
	 *
	 * @param paramId the param id
	 * @return the string
	 */
	@Cacheable("consultarValorParamConfig")
	@Select(value = CONSULTAR_PARAM_GEN_CONFIG)
	public String consultarValorParamConfig (@Param("paramId")String paramId);

	@Select("select TO_CHAR(current_timestamp, 'dd-MM-yyyy HH24:MI:SS')")
	public String consultarFechaSysdate();
	
}