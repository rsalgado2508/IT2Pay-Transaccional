package co.com.it2ex.it2pay.seguridad.negocio.servicios.producto;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 19/05/2023
 */


import co.com.it2ex.it2pay.seguridad.modelo.producto.ProductoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

/**
 * The Interface ServiciosColocador.
 */
public interface ServiciosProducto {
    public ListaDTO consultarProducto(Long idPersonaPunto) throws Exception;

    public ListaDTO consultarProductosPorAliado(UsuarioDTO paramsIn) throws Exception;

    public ProductoDTO consultarDetalleProductoPorAliado(UsuarioDTO paramsIn) throws Exception;

}