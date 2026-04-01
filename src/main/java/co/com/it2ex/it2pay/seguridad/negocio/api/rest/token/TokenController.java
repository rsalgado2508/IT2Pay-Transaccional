package co.com.it2ex.it2pay.seguridad.negocio.api.rest.token;

import co.com.it2ex.it2pay.seguridad.modelo.token.DatosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.token.TokenDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServicioValidacionUsuario;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServiciosUsuarios;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesAutenticacion;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesLogger;
import co.com.it2ex.it2pay.util.otros.constantes.path.ConstantesSeguridadPathRest;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.otros.components.UsuarioUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TokenController {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @Autowired
    private LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    private ServicioValidacionUsuario servicioValidacionUsuario;

    @Autowired
    private ServiciosUsuarios serviciosUsuarios;

    private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_TOKEN, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TokenDTO> getToken(@RequestBody DatosAutenticacionDTO datos, HttpServletRequest request) throws Exception{

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());
        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO,datos.toString(), this.getClass());

        TokenDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        try {

            DatosAutenticacionDTO datoSalida = servicioValidacionUsuario.validarUsuario(datos);

            respuesta = new TokenDTO();

            respuesta.setCodigoRespuesta(datoSalida.getCodigoRespuesta());
            respuesta.setMensajeRespuesta(datoSalida.getMensajeRespuesta());

            if(respuesta.getCodigoRespuesta().equals(ConstantesCodigosError.CODIGO_EXITO)){

                String username = datos.getUsuario();
                String password = datos.getUsuarioContr();

                DatosBasicosUsuarioDTO usuarioSesion = new DatosBasicosUsuarioDTO();
                usuarioSesion.setLogin(username);

                List<String> roles = new ArrayList<String>();
                roles.add("USER_ROLE");

                Map<String, Object> claims = new HashMap<>();

                String token = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(username + ConstantesAutenticacion.DELIMITADOR + password)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                        .signWith(SignatureAlgorithm.HS512, secretKey)
                        .compact();

                respuesta.setToken(token);

                serviciosUsuarios.guardarUltimaConexion(usuarioSesion.getLogin());

                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

                return new ResponseEntity<TokenDTO>(respuesta, HttpStatus.OK);

            } else {
                if (respuesta.getMensajeRespuesta() == null
                        || respuesta.getMensajeRespuesta().trim().equals("")) {
                    respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_NO_ESPECIFICADO);
                }

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.WARN, CaracteresUtil.neutralizeMessage(ConstantesLogger.RESPUESTA_METODO + new IT2PayException().getStackTrace()[0].getMethodName()
                        + ": " + respuesta.getCodigoRespuesta()
                        + " - " + respuesta.getMensajeRespuesta()), this.getClass());

                return new ResponseEntity<TokenDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            respuesta = new TokenDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

            return new ResponseEntity<TokenDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }

    }

    @RequestMapping(value = ConstantesSeguridadPathRest.PATH_LOGOUT, method = RequestMethod.POST, headers = "Accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TokenDTO> getSalir(ModelMap model, Principal principal, HttpServletRequest request) throws Exception{

        loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        TokenDTO respuesta = null;

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();
        try {
            respuesta = new TokenDTO();

            DatosBasicosUsuarioDTO usuarioSesion = UsuarioUtil.getUsuarioSesion(model, principal);

            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_EXITO);

            return new ResponseEntity<TokenDTO>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            respuesta = new TokenDTO();
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO);
            respuesta.setMensajeRespuesta(ConstantesCodigosError.MENSAJE_CODIGO_ERROR_NO_CONTROLADO);

            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

            return new ResponseEntity<TokenDTO>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Date horaFin = new Date();
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }
    }

    /**
     * Format con la solución de la vulnerabilidad Concurrent Execution using Shared Resource
     * with Improper Synchronization ('Race Condition').
     *
     * @param date the date
     * @return the string
     */
    public synchronized String format(Date date) {
        return formato.format(date);
    }

}