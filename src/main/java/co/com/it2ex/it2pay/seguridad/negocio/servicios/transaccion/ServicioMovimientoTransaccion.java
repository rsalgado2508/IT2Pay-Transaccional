package co.com.it2ex.it2pay.seguridad.negocio.servicios.transaccion;

import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.transaccion.MovimientoTransaccionRespuestaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo backend
 * @Copyright IT2Ex
 *
 * @Autor: jlalfonso
 * @FechaCreación: 27/07/2023
 */
public interface ServicioMovimientoTransaccion {
    public ListaDTO listarMovTransacciones() throws Exception;
    public ListaDTO consultarTransaccionesFecha(String fechaInicial, String fechaFinal) throws Exception;
    public MovimientoTransaccionRespuestaDTO reversarTransaccion(MovimientoTransaccionDTO movimientoTransaccionDTO) throws Exception;
    public MovimientoTransaccionRespuestaDTO enviarComprobante(MovimientoTransaccionDTO datos) throws Exception;
}
