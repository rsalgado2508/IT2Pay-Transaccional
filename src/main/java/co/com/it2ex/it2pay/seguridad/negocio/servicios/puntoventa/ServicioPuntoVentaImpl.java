package co.com.it2ex.it2pay.seguridad.negocio.servicios.puntoventa;

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.puntoventa.PuntoVentaMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioRolMapper;

import co.com.it2ex.it2pay.seguridad.modelo.puntoventa.CupoPuntoVentaDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServiciosUsuariosImpl;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.correo.ServicioMensajeria;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service
public class ServicioPuntoVentaImpl implements ServicioPuntoVenta {
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    ServiciosComun serviciosComun;
    @Autowired
    PuntoVentaMapper puntoVentaMapper;
    @Autowired
    ServiciosUsuariosImpl serviciosUsuariosImpl;

    @Autowired
    UsuarioMapper usuarioMapper;
    @Autowired
    ServicioMensajeria servicioMensajeria;
    @Autowired
    UsuarioRolMapper usuarioRolMapper;


    public ListaDTO consultaCupo(Long idPersonaPunto) throws Exception {
        ListaDTO respuesta = new ListaDTO();
        try {
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(
                    ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO +
                            new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            List<CupoPuntoVentaDTO> detalle = puntoVentaMapper.consultaDetalle(idPersonaPunto);
            if (detalle == null) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
            } else {
                respuesta.setLista(detalle);
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }
        } catch (Exception e) {
            respuesta = new ListaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }
    /********************************/


}
