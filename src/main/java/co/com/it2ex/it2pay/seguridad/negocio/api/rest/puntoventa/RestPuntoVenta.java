package co.com.it2ex.it2pay.seguridad.negocio.api.rest.puntoventa;
/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional backend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 18/7/2023
 */


import co.com.it2ex.it2pay.seguridad.negocio.servicios.puntoventa.ServicioPuntoVenta;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.components.UsuarioUtil;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;

import co.com.it2ex.it2pay.util.otros.constantes.path.ConstantesSeguridadPathRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class RestPuntoVenta {
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;



    @Autowired
    private ServicioPuntoVenta servicioPuntoVenta;
    private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");


    public synchronized String format(Date date) {
        return formato.format(date);
    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_CONSULTA_CUPOS_PUNTO_VENTA, method = RequestMethod.GET, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ListaDTO> consultacuposPuntoVenta(ModelMap model, Principal principal) throws Exception {
        ListaDTO respuesta = null;
        try {
            DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);
            respuesta = servicioPuntoVenta.consultaCupo(usuarioSesion.getIdPersona());
        } catch (Exception e) {
            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {
            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity<ListaDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
