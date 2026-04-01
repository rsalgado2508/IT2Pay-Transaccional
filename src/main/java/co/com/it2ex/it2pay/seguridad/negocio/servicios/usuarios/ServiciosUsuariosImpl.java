package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;
/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 28/4/2023
 */


import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.PersonaMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioRolMapper;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.accesodatos.mapper.generico.ParametroMapper;
import co.com.it2ex.it2pay.util.modelo.generico.*;
import co.com.it2ex.it2pay.util.negocio.servicios.correo.ServicioMensajeria;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.components.CifradoUtil;
import co.com.it2ex.it2pay.util.otros.components.ContrasenaUtil;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.parametros.ConstantesParametros;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ServiciosUsuariosImpl implements ServiciosUsuarios{

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    ParametroMapper parametroMapper;

    @Autowired
    PersonaMapper personaMapper;

    @Autowired
    CifradoUtil cifradoUtil;

    @Autowired
    ContrasenaUtil contrasenaUtil;

    @Autowired
    UsuarioRolMapper usuarioRolMapper;

    @Autowired
    ServicioMensajeria servicioMensajeria;

    @Override
    public ListaDTO listarUsuarios() throws Exception {

        ListaDTO lista = new ListaDTO();
        List<UsuarioDTO> listaUsuarios;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, "", this.getClass() );

            listaUsuarios = usuarioMapper.listarUsuarios();

            if (listaUsuarios.isEmpty()) {
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getMensajeRespuesta()).getMensaje() );
            } else {
                lista.setLista(listaUsuarios);
                lista.setTotalPaginas(listaUsuarios.size() > 0 ? 1 : 0);
                lista.setTotalRegistros(listaUsuarios.size());
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
    public UsuarioDTO consultarUsuarioPorId(UsuarioDTO params)  throws Exception {

        UsuarioDTO response;

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, params.toString(), this.getClass() );

            response = usuarioMapper.consultarUsuarioPorId(params);

            if (response != null) {
                response.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );
            } else {
                response = new UsuarioDTO();
                response.setCodigoRespuesta(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS);
                response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );
            }

        } catch (Exception e){
            response = new UsuarioDTO();
            response.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( response.getMensajeRespuesta() ), this.getClass(), e);
        }

        return response;
    }



    public BaseDTO cambiarEstadoUsuario (UsuarioDTO params)  throws Exception {

        BaseDTO response = new BaseDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, params.toString(), this.getClass() );

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO,
            "JMGR" + params.toString(),
                    this.getClass() );

            usuarioMapper.cambiarEstadoUsuario(params);

            response.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );

        } catch (Exception e){
            response = new BaseDTO();
            response.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( response.getMensajeRespuesta() ), this.getClass(), e);
        }

        return response;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public BaseDTO desbloquearUsuario (UsuarioDTO params)  throws Exception {

        BaseDTO response = new BaseDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, params.toString(), this.getClass() );

            usuarioMapper.desbloquearUsuario(params);

            //Agregar insert nueva tabla

            response.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );

        } catch (Exception e){
            response = new BaseDTO();
            response.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            response.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( response.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( response.getMensajeRespuesta() ), this.getClass(), e);
        }

        return response;
    }

    public String cargarUltimaConexion(Long idUsuario) throws Exception{
        String ultimaConexion = "";
        java.sql.Timestamp fecha = usuarioMapper.consultaUltimaConexion(idUsuario);
        if(fecha != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            ultimaConexion = df.format(fecha);
        }
        else{
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlFechaHora = new java.sql.Date(utilDate.getTime());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            ultimaConexion = df.format(sqlFechaHora);
        }
        return ultimaConexion;
    }

    public String cargarFechaActual() throws Exception{
        String ultimaConexion = "";
        java.sql.Date fecha = new Date(new java.util.Date().getTime());
        if(fecha != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            ultimaConexion = df.format(fecha);
        }
        return ultimaConexion;
    }


    public UsuarioDTO guardarUltimaConexion(String login) throws Exception{
        UsuarioDTO usuario = new UsuarioDTO();
        usuarioMapper.insertarUltimaConexion(login);
        return usuario;
    }

    public String obtenerNombreRol( Long id )  throws Exception{
        return usuarioMapper.obtenerNombreRol( id );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public UsuarioDTO actualizarClave(UsuarioDTO dto, DatosBasicosUsuarioDTO usuarioSesion) throws Exception{

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        UsuarioDTO usuario = new UsuarioDTO();

        String login = usuarioMapper.validarUsuarioYPass( usuarioSesion.getLogin(), dto.getClave() );

        if ( login == null || login.equals("")) {
            usuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_CONTRASENA_NO_VAL);
            usuario.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(usuario.getCodigoRespuesta()).getMensaje());

            return usuario;
        }

        dto.setClave( dto.getClaveRenovar2() );

        EstadoDTO validaParametrosContrasena = contrasenaUtil.validarContrasenaParametros(dto);
        if(!validaParametrosContrasena.isEstado()){

            usuario.setCodigoRespuesta(validaParametrosContrasena.getCodigoRespuesta());
            usuario.setMensajeRespuesta(validaParametrosContrasena.getMensajeRespuesta());

            return usuario;
        }
        else{

            dto.setNumeroHistorico(Long.parseLong(obtenerParametro(ConstantesParametros.NUMERO_HISTORICO_CLAVES).getValor()));

            Integer existe = usuarioMapper.existeClaveHistorialUsuario(dto);

            if (existe == null) {
                existe = 0;
            }

            boolean existeClave = existe != 0 ? true : false;
            if(existeClave){
                usuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXISTE_CONTRASENA);
                usuario.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(usuario.getCodigoRespuesta()).getMensaje());
            }
            else {
                usuarioMapper.actualizarUsuario(dto);
                guardarHistorialUsuario(dto);

                usuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }
        }
        return usuario;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public UsuarioDTO actualizarClaveSinValidacion(UsuarioDTO dto) throws Exception{

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        UsuarioDTO usuario = new UsuarioDTO();

        EstadoDTO validaParametrosContrasena = contrasenaUtil.validarContrasenaParametros(dto);
        if(!validaParametrosContrasena.isEstado()){

            usuario.setCodigoRespuesta(validaParametrosContrasena.getCodigoRespuesta());
            usuario.setMensajeRespuesta(validaParametrosContrasena.getMensajeRespuesta());

            return usuario;
        }
        else{

            dto.setNumeroHistorico(Long.parseLong(obtenerParametro(ConstantesParametros.NUMERO_HISTORICO_CLAVES).getValor()));

            Integer existe = usuarioMapper.existeClaveHistorialUsuario(dto);

            if (existe == null) {
                existe = 0;
            }

            boolean existeClave = existe != 0 ? true : false;
            if(existeClave){
                usuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXISTE_CONTRASENA);
                usuario.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(usuario.getCodigoRespuesta()).getMensaje());
            }
            else {
                usuarioMapper.actualizarUsuario(dto);
                guardarHistorialUsuario(dto);

                usuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }
        }
        return usuario;
    }

    private ParametroConsultaDTO obtenerParametro(String nombre){
        ParametroConsultaDTO parametro = new ParametroConsultaDTO();
        parametro.setNombre(nombre);
        return parametroMapper.selectValorParametroPorNombre(parametro);
    }

    public void guardarHistorialUsuario(UsuarioDTO dto) throws Exception{

        dto.setClave(dto.getClaveRenovar());
        HistoricoClaveDTO historicoClave = new HistoricoClaveDTO();
        historicoClave.setClave(dto.getClave());
        historicoClave.setUsuario(dto.getIdUsuario());
        historicoClave.setUsuarioModificacion(dto.getLogin());
        historicoClave.setUsuarioCreacion(dto.getLogin());
        usuarioMapper.insertarHistorialUsuario(historicoClave);
    }
}
