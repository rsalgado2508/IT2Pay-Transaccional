package co.com.it2ex.it2pay.util.negocio.servicios.correo;

import co.com.it2ex.it2pay.util.modelo.mensajeria.CorreoDTO;

public interface ServicioCorreoSpring {

	public void enviarCorreo(CorreoDTO dto) throws Exception;
	
	public void enviarCorreoSincrono(CorreoDTO dto) throws Exception;

}
