package co.com.it2ex.it2pay.seguridad.negocio.servicios.infoapp;


import co.com.it2ex.it2pay.seguridad.modelo.infoapp.InfoAppDTO;
import co.com.it2ex.it2pay.util.modelo.generico.MensajeDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.otros.components.ReadVersionApp;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesConfiguracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serviciosInfoAppImpl")
public class ServiciosInfoAppImpl implements ServiciosInfoApp {

	
	@Autowired
	ServiciosComun serviciosComun;


	
	@Override
	public InfoAppDTO consultarInfoApp( ) throws Exception {
		InfoAppDTO infoApp = new InfoAppDTO();
		MensajeDTO infoAppConfig = serviciosComun.consultarConfiguracionPorCodigo( ConstantesConfiguracion.VERSION_BD );
		infoApp.setVersionBD( infoAppConfig.getMensaje() );

		ReadVersionApp readVersion;

		readVersion = new ReadVersionApp();
		infoApp.setVersionAPP(readVersion.readVersion(""));

		infoApp.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
		infoApp.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(infoApp.getCodigoRespuesta()).getMensaje());
		return infoApp;
	}


	
}