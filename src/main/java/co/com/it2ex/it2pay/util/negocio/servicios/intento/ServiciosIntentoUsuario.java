package co.com.it2ex.it2pay.util.negocio.servicios.intento;

import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import co.com.it2ex.it2pay.util.modelo.intento.IntentoUsuarioDTO;

public interface ServiciosIntentoUsuario {
	
	public IntentoUsuarioDTO consultarIntentoUsuario(IntentoUsuarioDTO intentoUsuarioDTO) throws Exception;

	public BaseDTO registrarIntento(IntentoUsuarioDTO intentoUsuarioDTO) throws Exception;
	
	public BaseDTO eliminarIntento(IntentoUsuarioDTO intentoUsuarioDTO) throws Exception;

}
