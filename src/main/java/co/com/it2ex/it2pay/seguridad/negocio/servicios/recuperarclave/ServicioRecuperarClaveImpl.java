package co.com.it2ex.it2pay.seguridad.negocio.servicios.recuperarclave;

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

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.recuperarclave.RecuperarClaveMapper;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPRespuestaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarCambioClaveDTO;
import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarEntradaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.recuperarclave.RecuperarRespuestaDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.otp.ServiciosCodigosOTP;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServiciosUsuarios;
import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.correo.ServicioMensajeria;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesPlantillasCorreo;
import co.com.it2ex.it2pay.util.otros.constantes.enums.EstadosUsuariosEnum;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class ServicioRecuperarClaveImpl implements ServicioRecuperarClave {

    @Autowired
    ServiciosComun serviciosComun;
    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;
    @Autowired
    private RecuperarClaveMapper recuperarClaveMapper;

    @Autowired
    private ServiciosUsuarios serviciosUsuarios;

    @Autowired
    private ServiciosCodigosOTP serviciosCodigosOTP;

    @Autowired
    private ServicioMensajeria servicioMensajeria;

    LocalDateTime fechaHoy = LocalDateTime.now();


    public RecuperarRespuestaDTO validarLoginDocumento(RecuperarEntradaDTO dto) throws Exception {

        RecuperarRespuestaDTO respuesta = new RecuperarRespuestaDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, dto.toString(), this.getClass() );

            respuesta = recuperarClaveMapper.validarLoginDocumento(dto);

            if (respuesta != null && respuesta.getIdUsuario() != null) {

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
                    mensaje.setPlantilla( ConstantesPlantillasCorreo.PLANTILLA_GENERACION_OTP_RECORDAR_CONTRASENA_PORTAL_TRANSACCIONAL );

                    mensaje.setAsunto( "OTP Portal Transaccional Recordar contraseña" );

                    loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.DEBUG, mensaje.toString(), this.getClass() );

                    servicioMensajeria.envioMensaje( mensaje );

                    if ( respuestaOTP.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO) ) {
                        respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                        respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);
                    } else {
                        respuesta = new RecuperarRespuestaDTO();
                        respuesta.setCodigoRespuesta(respuestaOTP.getCodigoRespuesta());
                        respuesta.setMensajeRespuesta(respuesta.getMensajeRespuesta());
                    }

                } else {
                    respuesta = new RecuperarRespuestaDTO();
                    respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_USUARIO_NO_ENCONTRADO_RECUPERAR);
                    respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                }

        } catch (Exception e) {
            respuesta = new RecuperarRespuestaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;
    }

    public RecuperarRespuestaDTO cambiarClaveLogin(RecuperarCambioClaveDTO dto) throws Exception {

        RecuperarRespuestaDTO respuesta = new RecuperarRespuestaDTO();

        try {

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, dto.toString(), this.getClass() );

            RecuperarEntradaDTO dtoEntrada = new RecuperarEntradaDTO();

            dtoEntrada.setLogin( dto.getLogin() );

            UsuarioDTO usuarioEntrada = new UsuarioDTO();

            usuarioEntrada.setLogin( dto.getLogin() );
            usuarioEntrada.setClave( dto.getClaveSin() );
            usuarioEntrada.setIdUsuario( dto.getIdUsuario() );
            usuarioEntrada.setClaveRenovar( dto.getClaveNva() );

            UsuarioDTO usuarioRespuesta = new UsuarioDTO();

            usuarioRespuesta = serviciosUsuarios.actualizarClaveSinValidacion( usuarioEntrada );

            if ( usuarioRespuesta.getCodigoRespuesta().equals( ConstantesCodigosError.CODIGO_EXITO ) ) {

                usuarioEntrada.setEstado( EstadosUsuariosEnum.ACTIVO.getCodigo() );
                serviciosUsuarios.cambiarEstadoUsuario( usuarioEntrada );

            }

            respuesta.setCodigoRespuesta(usuarioRespuesta.getCodigoRespuesta());
            respuesta.setMensajeRespuesta(usuarioRespuesta.getMensajeRespuesta());

        } catch (Exception e) {
            respuesta = new RecuperarRespuestaDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage(respuesta.getMensajeRespuesta()), this.getClass(), e);
        }
        return respuesta;

    }

}
