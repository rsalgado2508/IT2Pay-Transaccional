package co.com.it2ex.it2pay.seguridad.negocio.servicios.login;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Jorge Ruiz
 * @FechaCreación: 03/4/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.login.LoginAutenticacionMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginCambioClaveDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginEntradaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.login.LoginRespuestaDTO;

import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPRespuestaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.otp.ServiciosCodigosOTP;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServiciosDesbloqueo;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServiciosUsuarios;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.generico.RespuestaBaseDTO;
import co.com.it2ex.it2pay.util.modelo.intento.IntentoUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;
import co.com.it2ex.it2pay.util.modelo.session.ParametrosValidacionURLDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.correo.ServicioMensajeria;
import co.com.it2ex.it2pay.util.negocio.servicios.funcionalidad.ServiciosFuncionalidad;
import co.com.it2ex.it2pay.util.negocio.servicios.intento.ServiciosIntentoUsuario;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.negocio.servicios.parametros.ServicioParametros;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesPlantillasCorreo;
import co.com.it2ex.it2pay.util.otros.constantes.enums.EstadosUsuariosEnum;
import co.com.it2ex.it2pay.util.otros.constantes.enums.TipoIntentoEnum;
import co.com.it2ex.it2pay.util.otros.constantes.parametros.ConstantesParametros;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class ServicioLoginImpl implements ServicioLogin {

    @Autowired
    ServiciosComun serviciosComun;
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    private LoginAutenticacionMapper loginAutenticacionMapper;

    @Autowired
    private ServiciosUsuarios serviciosUsuarios;

    @Autowired
    private ServiciosCodigosOTP serviciosCodigosOTP;

    @Autowired
    private ServicioMensajeria servicioMensajeria;

    @Autowired
    private ServicioParametros servicioParametros;

    @Autowired
    private ServiciosIntentoUsuario serviciosIntentoUsuario;

    @Autowired
    private ServiciosDesbloqueo serviciosDesbloqueo;

    LocalDateTime fechaHoy = LocalDateTime.now();

    @Autowired
    HttpServletRequest request;

    @Autowired
    ServiciosFuncionalidad serviciosFuncionalidad;

    @Autowired
    UsuarioMapper usuarioMapper;


    public LoginRespuestaDTO validarClaveLogin(LoginEntradaDTO dto) throws Exception {

        LoginRespuestaDTO respuesta = new LoginRespuestaDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, dto.toString(), this.getClass() );

            IntentoUsuarioDTO intento = new IntentoUsuarioDTO(dto.getLogin(),
                    TipoIntentoEnum.TIPO_INTENTO_CONTRASENA.name(),
                    servicioParametros.obtenerValorParametro(ConstantesParametros.NUMERO_MAX_INTENTOS_CONTRASENA_CLIENTE).getValor());

            respuesta = loginAutenticacionMapper.validarClaveLogin(dto);

            if (respuesta != null && respuesta.getIdUsuario() != null) {

                if ( respuesta.getEstado().equals(EstadosUsuariosEnum.BLOQUEADO.getCodigo()) ) {
                    RespuestaBaseDTO respuestaDesbloqueo = new RespuestaBaseDTO();


                    Long idUsuario = usuarioMapper.consultarIdUsuarioPorLogin(dto.getLogin());

                    UsuarioDTO usuarioTemporal = new UsuarioDTO();

                    usuarioTemporal.setLogin(dto.getLogin());
                    usuarioTemporal.setIdUsuario(idUsuario);
                    usuarioTemporal.setUsuarioModificacion(dto.getLogin());

                    ParametrosValidacionURLDTO parametro =  new ParametrosValidacionURLDTO();
                    String estadoPagina  = request.getHeader("state");
                    Long idFuncionalidad;
                    parametro.setUrlFuncionalidad(estadoPagina);

                    idFuncionalidad = serviciosFuncionalidad.consultarIdFuncionalidad(parametro);

                    respuestaDesbloqueo = serviciosDesbloqueo.desbloqueoAutomatico(idFuncionalidad, usuarioTemporal, null);

                    if (respuestaDesbloqueo != null && respuestaDesbloqueo.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {
                        respuesta = loginAutenticacionMapper.validarClaveLogin(dto);
                    }

                }

                if ( respuesta.getEstado().equals(EstadosUsuariosEnum.ACTIVO.getCodigo()) ||
                        respuesta.getEstado().equals(EstadosUsuariosEnum.REGISTRADO.getCodigo()) ) {

                    String estado = respuesta.getEstado();

                    if ( respuesta.getClave().equals( dto.getClave() ) ) {

                        if ( estado.equals(EstadosUsuariosEnum.ACTIVO.getCodigo()) ) {
                            serviciosIntentoUsuario.eliminarIntento(intento);
                        }

                        OTPCodigosAutenticacionDTO datosOTP = new OTPCodigosAutenticacionDTO();
                        datosOTP.setUsuarioCreacion( dto.getLogin() );
                        datosOTP.setUsuarioModificacion( dto.getLogin() );
                        datosOTP.setIdUsuario( respuesta.getIdUsuario() );

                        OTPRespuestaDTO respuestaOTP = serviciosCodigosOTP.crearCodigoOTP(datosOTP);

                        CorreoDTO mensaje = new CorreoDTO();

                        mensaje.setPara( respuesta.getCorreo() );
                        mensaje.setParaNombre( respuesta.getCorreo() );

                        ObjectMapper objectMapper = new ObjectMapper();

                        Map<String, Object> jsonObject = new HashMap<>();
                        jsonObject.put("correo", respuesta.getCorreo());
                        jsonObject.put("otp", respuestaOTP.getCodigoOtp());

                        String json = objectMapper.writeValueAsString(jsonObject);

                        mensaje.setContenido( json );
                        mensaje.setAsunto( "OTP Portal Transaccional" );
                        mensaje.setPlantilla( ConstantesPlantillasCorreo.PLANTILLA_GENERACION_OTP_AUTENTICACION_PORTAL_TRANSACCIONAL );

                        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.DEBUG, mensaje.toString(), this.getClass() );

                        servicioMensajeria.envioMensaje( mensaje );

                        if ( respuestaOTP.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO) ) {
                            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
                        } else {
                            respuesta = new LoginRespuestaDTO();
                            respuesta.setCodigoRespuesta(respuestaOTP.getCodigoRespuesta());
                            respuesta.setMensajeRespuesta(respuesta.getMensajeRespuesta());
                        }

                    } else {
                        respuesta = new LoginRespuestaDTO();
                        respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_NO_ENCONTRADO);
                        respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());

                        if ( estado.equals(EstadosUsuariosEnum.ACTIVO.getCodigo()) ) {
                            BaseDTO validacionIntento = serviciosIntentoUsuario.registrarIntento(intento);

                            if (!validacionIntento.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)) {
                                respuesta.setCodigoRespuesta(validacionIntento.getCodigoRespuesta());
                                respuesta.setMensajeRespuesta(validacionIntento.getMensajeRespuesta());
                                return respuesta;
                            }

                        }

                    }

                } else if ( respuesta.getEstado().equals(EstadosUsuariosEnum.BLOQUEADO.getCodigo()) ) {

                    respuesta = new LoginRespuestaDTO();
                    respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_BLOQUEADO);
                    respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());

                } else if ( respuesta.getEstado().equals(EstadosUsuariosEnum.INACTIVO.getCodigo()) ) {

                    respuesta = new LoginRespuestaDTO();
                    respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_INACTIVO);
                    respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());

                }

            } else{
                respuesta = new LoginRespuestaDTO();
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_NO_ENCONTRADO);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            }

        } catch (Exception e) {
            respuesta = new LoginRespuestaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }

    public LoginRespuestaDTO cambiarClaveLogin(LoginCambioClaveDTO dto) throws Exception {

        LoginRespuestaDTO respuesta = new LoginRespuestaDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, dto.toString(), this.getClass() );

            LoginEntradaDTO dtoEntrada = new LoginEntradaDTO();

            dtoEntrada.setLogin( dto.getLogin() );

            respuesta = loginAutenticacionMapper.validarClaveLogin(dtoEntrada);

            if (respuesta != null && respuesta.getIdUsuario() != null) {

                if ( respuesta.getClave().equals( dto.getClave() ) ) {

                    UsuarioDTO usuarioEntrada = new UsuarioDTO();

                    usuarioEntrada.setLogin( dto.getLogin() );
                    usuarioEntrada.setClave( dto.getClaveSin() );
                    usuarioEntrada.setIdUsuario( respuesta.getIdUsuario() );
                    usuarioEntrada.setClaveRenovar( dto.getClaveNva() );

                    UsuarioDTO usuarioRespuesta = new UsuarioDTO();

                    usuarioRespuesta = serviciosUsuarios.actualizarClaveSinValidacion( usuarioEntrada );

                    if ( usuarioRespuesta.getCodigoRespuesta().equals( ConstantesCodigosError.CODIGO_EXITO ) ) {

                        usuarioEntrada.setEstado( EstadosUsuariosEnum.ACTIVO.getCodigo() );
                        serviciosUsuarios.cambiarEstadoUsuario( usuarioEntrada );

                    }

                    respuesta.setCodigoRespuesta(usuarioRespuesta.getCodigoRespuesta());
                    respuesta.setMensajeRespuesta(usuarioRespuesta.getMensajeRespuesta());

                } else {
                    respuesta = new LoginRespuestaDTO();
                    respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_NO_ENCONTRADO);
                    respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                }

            } else{
                respuesta = new LoginRespuestaDTO();
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_NO_ENCONTRADO);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            }

        } catch (Exception e) {
            respuesta = new LoginRespuestaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;

    }

}
