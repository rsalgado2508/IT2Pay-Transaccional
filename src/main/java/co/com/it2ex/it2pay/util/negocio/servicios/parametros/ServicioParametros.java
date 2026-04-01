package co.com.it2ex.it2pay.util.negocio.servicios.parametros;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 18/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroConsultaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroDTO;

public interface ServicioParametros {

    public ListaDTO consultarParametros(ParametroDTO params) throws Exception;

    ListaDTO consultarGruposParametros() throws Exception;

    public BaseDTO modificarParametro (ParametroDTO params)  throws Exception;

    public ParametroConsultaDTO obtenerValorParametro(String nombre) throws Exception;

    public ParametroConsultaDTO obtenerValorParametroPorId(Long id) throws Exception;
}
