package co.com.it2ex.it2pay.util.negocio.servicios.parametros;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 18/5/2023
 */

import co.com.it2ex.it2pay.util.accesodatos.mapper.generico.ParametroMapper;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroConsultaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.enums.TipoValorEnum;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioParametrosImpl implements ServicioParametros{

    @Autowired
    private ServiciosComun serviciosComun;

    @Autowired
    private LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    private ParametroMapper parametroMapper;

    @Override
    public ListaDTO consultarParametros(ParametroDTO params) throws Exception {

        ListaDTO lista = new ListaDTO();
        List<ParametroDTO> listaParametros;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, "", this.getClass() );

            listaParametros = parametroMapper.consultarParametros(params.getIdGrupoParametro());

            if (listaParametros.isEmpty()) {
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getMensajeRespuesta()).getMensaje() );
            } else {
                lista.setLista(listaParametros);
                lista.setTotalPaginas(listaParametros.size() > 0 ? 1 : 0);
                lista.setTotalRegistros(listaParametros.size());
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

    @Override
    public ListaDTO consultarGruposParametros() throws Exception {

        ListaDTO lista = new ListaDTO();
        List<ParametroDTO> listaParametros;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, "", this.getClass() );

            listaParametros = parametroMapper.consultarGruposParametros();

            if (listaParametros.isEmpty()) {
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getMensajeRespuesta()).getMensaje() );
            } else {
                lista.setLista(listaParametros);
                lista.setTotalPaginas(listaParametros.size() > 0 ? 1 : 0);
                lista.setTotalRegistros(listaParametros.size());
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

    @Override
    public BaseDTO modificarParametro(ParametroDTO params) throws Exception {
        BaseDTO respuesta = new BaseDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, params.toString(), this.getClass() );

            parametroMapper.modificarParametro(params);

            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuesta.getCodigoRespuesta()).getMensaje() );

        } catch (Exception e){
            respuesta = new BaseDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }

    public ParametroConsultaDTO obtenerValorParametro(String nombre) throws Exception {

        ParametroConsultaDTO dto = new ParametroConsultaDTO();

        dto.setNombre(nombre);

        ParametroConsultaDTO parametro = parametroMapper.selectValorParametroPorNombre(dto);

        if(parametro!=null){
            if(parametro.getValor()==null || parametro.getValor().isEmpty()){
                parametro.setValor(TipoValorEnum.VACIO.name());
            }
        }
        parametro.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
        parametro.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(parametro.getCodigoRespuesta()).getMensaje());

        return parametro;
    }

    public ParametroConsultaDTO obtenerValorParametroPorId(Long id) throws Exception {

        ParametroConsultaDTO dto = new ParametroConsultaDTO();

        dto.setId(id);

        ParametroConsultaDTO parametro = parametroMapper.selectValorParametroPorId(dto);

        if(parametro!=null){
            if(parametro.getValor()==null || parametro.getValor().isEmpty()){
                parametro.setValor(TipoValorEnum.VACIO.name());
            }
        }
        parametro.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
        parametro.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(parametro.getCodigoRespuesta()).getMensaje());

        return parametro;
    }
}
