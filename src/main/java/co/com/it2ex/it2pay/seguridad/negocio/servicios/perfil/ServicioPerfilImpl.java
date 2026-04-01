package co.com.it2ex.it2pay.seguridad.negocio.servicios.perfil;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 16/5/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.perfil.PerfilMapper;
import co.com.it2ex.it2pay.seguridad.modelo.perfil.PerfilDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServicioPerfilImpl implements ServicioPerfil {

    @Autowired
    ServiciosComun serviciosComun;
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    private PerfilMapper perfilMapper;


    public PerfilDTO consultarDatosPerfil(String login) throws Exception {

        PerfilDTO respuesta = new PerfilDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            respuesta = perfilMapper.consultarDatosPerfil( login );

            if ( respuesta == null ) {
                respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS );
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                return respuesta;
            }

            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_EXITO );

        } catch (Exception e) {
            respuesta = new PerfilDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }

}
