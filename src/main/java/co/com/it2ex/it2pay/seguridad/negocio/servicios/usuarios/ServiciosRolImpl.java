package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 25/5/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.RolMapper;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.RolDTO;
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
public class ServiciosRolImpl implements ServiciosRol{

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    RolMapper rolMapper;

    @Override
    public ListaDTO listarRoles() throws Exception {

        ListaDTO lista = new ListaDTO();
        List<RolDTO> listaRoles;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, "", this.getClass() );

            listaRoles = rolMapper.consultarRoles();

            if (listaRoles.isEmpty()) {
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getMensajeRespuesta()).getMensaje() );
            } else {
                lista.setLista(listaRoles);
                lista.setTotalPaginas(listaRoles.size() > 0 ? 1 : 0);
                lista.setTotalRegistros(listaRoles.size());
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getMensajeRespuesta()).getMensaje() );
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
