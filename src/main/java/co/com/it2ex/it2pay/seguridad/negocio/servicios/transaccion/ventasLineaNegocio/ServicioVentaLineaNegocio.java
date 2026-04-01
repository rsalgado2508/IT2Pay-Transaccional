package co.com.it2ex.it2pay.seguridad.negocio.servicios.transaccion.ventasLineaNegocio;

import co.com.it2ex.it2pay.seguridad.modelo.graficos.PantallaGraficosDTO;
import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;

import java.util.List;
import java.util.Map;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 11/08/2023
 */
public interface ServicioVentaLineaNegocio {
    public PantallaGraficosDTO consultarVentasTransaccionesFecha(String fechaInicial, String fechaFinal,Long rol,Long idPersona) throws Exception;
    public Map<String, Object> consultarNumeroTransaccionesFecha(List<MovimientoTransaccionDTO> listado) throws Exception;

}
