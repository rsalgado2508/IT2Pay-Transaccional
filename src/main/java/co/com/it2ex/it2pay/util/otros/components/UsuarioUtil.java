package co.com.it2ex.it2pay.util.otros.components;

import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesAutenticacion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.ModelMap;

import java.security.Principal;

public class UsuarioUtil {

    /**
     * Gets the usuario sesion.
     *
     * @param model the model
     * @param principal the principal
     * @return the usuario sesion
     */
    public static DatosBasicosUsuarioDTO getUsuarioSesion(ModelMap model, Principal principal){

        UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken )principal;
        User user = (User)userDetails.getPrincipal();
        DatosBasicosUsuarioDTO datosUsuario = new DatosBasicosUsuarioDTO();

        String usuario = user.getUsername();

        String[] resultado = usuario.split(ConstantesAutenticacion.DELIMITADOR);

        datosUsuario.setLogin(resultado[0]);
        datosUsuario.setTipo(resultado[1]);

        datosUsuario.setIdUsuario(new Long(resultado[2]));
        datosUsuario.setUsuarioCreacion(resultado[0]);
        datosUsuario.setUsuarioModificacion(resultado[0]);
        datosUsuario.setTpId(resultado[3]);
        datosUsuario.setNroId(resultado[4]);
        datosUsuario.setApellidos(resultado[6]);
        datosUsuario.setNombres(resultado[5]);
        datosUsuario.setIdRol(resultado[7] != null && !resultado[7].equals("NO_ROL") ? new Long(resultado[7]) : null);
        datosUsuario.setIdPersona(resultado[8] != null ? new Long(resultado[8]) : null);

        return datosUsuario;

    }

    /**
     * Sets the usuario sesion.
     *
     * @param model the model
     * @param principal the principal
     * @param idRol the id rol
     * @return the datos basicos usuario DTO
     */
    public static DatosBasicosUsuarioDTO setUsuarioSesion(ModelMap model, Principal principal, Long idRol, String nombreRol){
        UsernamePasswordAuthenticationToken  userDetails = (UsernamePasswordAuthenticationToken )principal;
        DatosBasicosUsuarioDTO datosUsuario = (DatosBasicosUsuarioDTO)userDetails.getPrincipal();

        datosUsuario.setIdRol(idRol);
        datosUsuario.setNombreRol(nombreRol);

        userDetails.setDetails(datosUsuario);

        return datosUsuario;
    }

}
