package co.com.it2ex.it2pay.util.accesodatos.mapper.listadetalle;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 28/4/2023
 */

import java.util.List;

import co.com.it2ex.it2pay.util.modelo.listadetalle.DetalleListaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface ListaDetalleMapper {

	@Cacheable("listaDetallePorParametro")
	@Select(" select id_detalle_lista id_detalle_lista,ID_LISTA idLista, valor, descripcion, orden  from  \"GEN_DETALLE_LISTA\" where ID_LISTA = #{idLista} and estado = 'A' ORDER BY orden ")
	public List<DetalleListaDTO> listaDetallePorParametro(Long idLista);
}
