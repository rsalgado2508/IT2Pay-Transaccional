package co.com.it2ex.it2pay.util.accesodatos.mapper.codigoserror;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 26/4/2023
 */

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface CodigoErrorIdiomaMapper {


	/**
	 * @param codigo
	 * @param idioma
	 * @return
	 */
	@Cacheable("consultarMensajePorIdioma")
	@Select("select '(' || gci.codigo_equivalente || ') ' || gci.descripcion mensaje " +
			"from \"GEN_CODERROR_IDIOMA\" gci " +
			"where gci.id_coderror_idioma = #{ codigo } " +
			"and gci.idioma = ( select gp.valor from \"GEN_PARAMETRO\" gp where gp.nombre = 'idioma_aplicacion' )")
	public String consultarMensajePorIdioma(@Param("codigo") String codigo);

}