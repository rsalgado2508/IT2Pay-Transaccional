package co.com.it2ex.it2pay.seguridad.negocio.servicios.terminos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 2/5/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.terminos.TerminosMapper;
import co.com.it2ex.it2pay.seguridad.modelo.terminos.TerminosDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioTerminosImpl implements ServicioTerminos {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    TerminosMapper terminosMapper;

    @Override
    public TerminosDTO consultarTerminosYCondicionesActivo() throws Exception {

        TerminosDTO respuesta;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            respuesta = terminosMapper.consultarTerminosYCondicionesActivo( );

            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_EXITO );

        } catch ( Exception e ) {

            respuesta = new TerminosDTO();
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuesta.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

        }

        return respuesta;
    }

    @Override
    public TerminosDTO actualizarAceptarTerminosYCondiciones(TerminosDTO dto) throws Exception {

        TerminosDTO respuesta;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, dto.toString(), this.getClass() );

            dto.setUsuarioModificacion(dto.getUsuario());

            terminosMapper.actualizarAceptarTerminosYCondiciones( dto );

            respuesta = new TerminosDTO();
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_EXITO );

        } catch ( Exception e ) {

            respuesta = new TerminosDTO();
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuesta.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

        }

        return respuesta;

    }

}