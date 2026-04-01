package co.com.it2ex.it2pay.util.negocio.servicios.funcionalidad;


import javax.servlet.http.HttpServletRequest;

import co.com.it2ex.it2pay.util.accesodatos.mapper.funcionalidad.FuncionalidadMapper;
import co.com.it2ex.it2pay.util.modelo.session.ParametrosValidacionURLDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("serviciosFuncionalidad")
@Transactional
public class ServiciosFuncionalidadImpl implements ServiciosFuncionalidad {

	@Autowired
	private FuncionalidadMapper funcionalidadMapper;

	@Autowired
	HttpServletRequest request;


//	@Override
	public Long consultarIdFuncionalidad(ParametrosValidacionURLDTO parametro) throws Exception {
		
		Long id = funcionalidadMapper.consultarIdFuncionalidadPorUrl(parametro);
		
		if(id == null){
			String parametroEnvio = parametro.getUrlFuncionalidad();
			if(parametroEnvio != null){
				parametroEnvio = parametroEnvio + "/%";
				parametro.setUrlFuncionalidad(parametroEnvio);
				id = funcionalidadMapper.consultarIdFuncionalidadPorUrlConParametro(parametro);
			}			
		}

		return id;

	}

}
