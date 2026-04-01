package co.com.it2ex.it2pay.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.it2ex.it2pay.config.exception.InvalidJwtAuthenticationException;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios.ServicioValidacionUsuario;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesAutenticacion;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    DatosBasicosUsuarioDTO authUser = null;

    private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
    @Autowired
    private ServicioValidacionUsuario servicioValidacionUsuario;

    @Autowired
    private LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        Logger LOGGER = LoggerFactory.getLogger(this.getClass());

        LOGGER.info(CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()));

        long currentTimeMillis = System.currentTimeMillis();
        Date horaInicio = new Date();

        Object token= authentication.getCredentials();
        try{

            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.toString());

            String subject = claims.getBody().getSubject();

            int i = subject.indexOf(ConstantesAutenticacion.DELIMITADOR);

            String u = subject.substring(0, i);
            String p = subject.substring(i, subject.length());

            p = p.substring(ConstantesAutenticacion.DELIMITADOR.length(), p.length());

            p = p.replace(ConstantesAutenticacion.DELIMITADOR, "");

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setLogin(u);
            usuarioDTO.setClave(p);

            authUser = servicioValidacionUsuario.validarContrasena(usuarioDTO);


            if(authUser != null && authUser.getIdUsuario() != null){
                if(!validateToken(claims)){
                    throw new InvalidJwtAuthenticationException(ConstantesCodigosError.MENSAJE_ERROR_TOKEN_INVALIDO_VENCIDO);
                }


                User user= new User(
                        u +
                                ConstantesAutenticacion.DELIMITADOR +
                                "t" +
                                ConstantesAutenticacion.DELIMITADOR +
                                authUser.getIdUsuario()+
                                ConstantesAutenticacion.DELIMITADOR +
                                authUser.getTpId()+
                                ConstantesAutenticacion.DELIMITADOR +
                                authUser.getNroId()+
                                ConstantesAutenticacion.DELIMITADOR +
                                authUser.getPrimerNombre()+ ( authUser.getSegundoNombre() == null ? "" : " " + authUser.getSegundoNombre() ) +
                                ConstantesAutenticacion.DELIMITADOR +
                                authUser.getPrimerApellido() + ( authUser.getSegundoApellido() == null ? "" : " " + authUser.getSegundoApellido() ) +
                                ConstantesAutenticacion.DELIMITADOR +
                                (authUser.getIdRol() != null ? authUser.getIdRol().toString() : "NO_ROL")+
                                ConstantesAutenticacion.DELIMITADOR +
                                (authUser.getIdPersona())



                        , token.toString(), true, true, true, true,
                        AuthorityUtils.createAuthorityList("USER_ROLE"));

                return user;

            } else {
                throw new UsernameNotFoundException(u);
            }

        } catch (Exception e) {
            loggerAuditoriasComponent.registrarLoggerError(e.getMessage(), this.getClass(), e);
            throw new InvalidJwtAuthenticationException(ConstantesCodigosError.MENSAJE_ERROR_TOKEN_INVALIDO_VENCIDO);
        } finally {
            Date horaFin = new Date();
            LOGGER.info( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage("Obtención de clase "+ this.getClass().getSimpleName()
                    + "- Metodo -"+new IT2PayException().getStackTrace()[0].getMethodName()+
                    " fue de "+((System.currentTimeMillis() - currentTimeMillis) / 1000d)
                    +" segundos - Hora inicio " + horaInicio + " - Hora fin "+format(horaFin)), this.getClass());
        }

    }

    private boolean validateToken(Jws<Claims> claims) {
        try {

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            loggerAuditoriasComponent.registrarLoggerError(e.getMessage(), this.getClass(), e);
            throw new InvalidJwtAuthenticationException(ConstantesCodigosError.MENSAJE_ERROR_TOKEN_INVALIDO_VENCIDO);
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