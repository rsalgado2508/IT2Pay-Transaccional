package co.com.it2ex.it2pay.util.negocio.servicios.intento;

import co.com.it2ex.it2pay.util.modelo.intento.BloqueoDTO;

public interface ServiciosBloqueo {

	public BloqueoDTO bloquearProceso(Long idFuncionalidad, String tipoIntento, String usuario, String persona, Long idUsuario) throws Exception ;
	
	
}
