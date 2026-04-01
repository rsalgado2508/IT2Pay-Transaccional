package co.com.it2ex.it2pay.seguridad.negocio.servicios.aliado;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 21/7/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.aliado.AliadoMapper;
import co.com.it2ex.it2pay.seguridad.modelo.aliado.AliadoDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosAliadoImpl implements ServiciosAliado {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    AliadoMapper aliadoMapper;


    @Override
    public ListaDTO consultarListaAliados(Long idPersona) throws Exception {
        ListaDTO respuesta;
        try {
            loggerAuditoriasComponent.registrarLogger(ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            Long limiteAliado=aliadoMapper.limiteAliados();
            List<AliadoDTO> listado = aliadoMapper.consultarAliados(idPersona,limiteAliado);
            respuesta = new ListaDTO();
            if (listado.isEmpty()) {
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
            } else {
                respuesta.setLista(listado);
                respuesta.setTotalPaginas(listado.size() > 0 ? 1 : 0);
                respuesta.setTotalRegistros(listado.size());
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }


        } catch (Exception e) {
            respuesta = new ListaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);

        }
        return respuesta;
    }

}