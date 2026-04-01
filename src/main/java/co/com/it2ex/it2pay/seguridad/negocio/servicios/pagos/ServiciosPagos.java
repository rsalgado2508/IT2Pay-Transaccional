package co.com.it2ex.it2pay.seguridad.negocio.servicios.pagos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 28/8/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.pagos.PagoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;

public interface ServiciosPagos {

    public BaseDTO validarPagoAliadoProducto(PagoDTO paramsIn) throws Exception;

    public BaseDTO componentePagoAliadoProducto(PagoDTO paramsIn, String tipoConsumo) throws Exception;

    public BaseDTO enviarComprobantePago(UsuarioDTO paramsIn) throws Exception;

}
