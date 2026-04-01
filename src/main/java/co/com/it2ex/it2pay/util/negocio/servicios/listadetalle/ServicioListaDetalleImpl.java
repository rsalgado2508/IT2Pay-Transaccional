package co.com.it2ex.it2pay.util.negocio.servicios.listadetalle;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 4/5/2023
 */

import co.com.it2ex.it2pay.util.accesodatos.mapper.listadetalle.ListaDetalleMapper;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.modelo.listadetalle.DetalleListaDTO;
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
public class ServicioListaDetalleImpl implements ServicioListaDetalle {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    ListaDetalleMapper listaDetalleMapper;


    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ListaDTO consultaListaDetallePorId(Long id) throws Exception {

        ListaDTO respuesta;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            List<DetalleListaDTO> listado = listaDetalleMapper.listaDetallePorParametro( id );

            respuesta = new ListaDTO();
            respuesta.setLista( listado );
            respuesta.setTotalPaginas( listado.size() > 0 ? 1 : 0 );
            respuesta.setTotalRegistros( listado.size() );
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_EXITO );

        } catch ( Exception e ) {

            respuesta = new ListaDTO();
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuesta.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

        }

        return respuesta;
    }

}