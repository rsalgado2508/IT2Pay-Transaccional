package co.com.it2ex.it2pay.seguridad.negocio.servicios.recuperarclave;


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

import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarCambioClaveDTO;
import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarEntradaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarRespuestaDTO;

public interface ServicioRecuperarClave {

    public RecuperarRespuestaDTO validarLoginDocumento(RecuperarEntradaDTO dto) throws Exception;

    public RecuperarRespuestaDTO cambiarClaveLogin(RecuperarCambioClaveDTO dto) throws Exception;

}
