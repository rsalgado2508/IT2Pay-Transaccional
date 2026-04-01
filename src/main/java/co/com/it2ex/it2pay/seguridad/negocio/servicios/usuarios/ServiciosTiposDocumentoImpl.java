package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 27/4/2023
 */

import co.com.it2ex.it2pay.util.accesodatos.mapper.tiposdocumentos.TipoDocumentoMapper;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.modelo.tiposdocumentos.TipoDocumentoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiciosTiposDocumentoImpl implements ServiciosTiposDocumento {

    @Autowired
    private TipoDocumentoMapper tipoDocumentoMapper;

    @Autowired
    private ServiciosComun serviciosComun;

    @Autowired
    private LoggerAuditoriasComponent loggerAuditoriasComponent;


    public ListaDTO consultarTiposDeDocumento() throws Exception {

        ListaDTO lista = new ListaDTO();
        List<TipoDocumentoDTO> listaTiposDocumento = new ArrayList<TipoDocumentoDTO>();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(
                    ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO +
                        new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            listaTiposDocumento = tipoDocumentoMapper.obtenerTiposDeDocumento();

            if (listaTiposDocumento.isEmpty()) {

                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                lista.setMensajeRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
            } else {
                lista.setLista(listaTiposDocumento);
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                lista.setMensajeRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }

        } catch (Exception e){
            lista = new ListaDTO();
            lista.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( lista.getMensajeRespuesta() ), this.getClass(), e);
        }

        return lista;

    }
}
