package co.com.it2ex.it2pay.util.negocio.servicios.log;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/8/2023
 */

import co.com.it2ex.it2pay.util.accesodatos.mapper.auditoria.AuditoriaLogMapper;
import co.com.it2ex.it2pay.util.accesodatos.mapper.configuracion.GenConfiguracionMapper;
import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.enums.EstadosAuditoriaEnum;
import co.com.it2ex.it2pay.util.otros.constantes.enums.RolesUsuarioEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@Service
public class LoggerAuditoriasComponentImpl implements LoggerAuditoriasComponent {

	@Autowired
	private ServiciosComun serviciosComun;

	@Autowired
	HttpServletRequest request;

	@Autowired
	private AuditoriaLogMapper auditoriaLogMapper;

	@Autowired
	private GenConfiguracionMapper genConfiguracionMapper;

	@Autowired
	private ServiciosAuditoriaLog serviciosAuditoriaLog;

	private SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");

	private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

	public void registrarLogger (String tipoLogger, String mensaje, Class clase) {

		Logger LOGGER = LoggerFactory.getLogger(clase);

		AuditoriaDTO auditoria = construirDTOAuditoria( mensaje, clase );

		auditoria.setDescripcion( mensaje );

		if (tipoLogger.equals(ConstantesCodigosLogger.ERROR)) {
			LOGGER.error(mensaje);
			auditoria.setEstadoAuditoria( EstadosAuditoriaEnum.ERROR.getCodigo() );
		} else if (tipoLogger.equals(ConstantesCodigosLogger.WARN)) {
			LOGGER.warn(mensaje);
			auditoria.setEstadoAuditoria( EstadosAuditoriaEnum.WARN.getCodigo() );
		} else if (tipoLogger.equals(ConstantesCodigosLogger.DEBUG)) {
			LOGGER.debug(mensaje);
			auditoria.setEstadoAuditoria( EstadosAuditoriaEnum.DEBUG.getCodigo() );
		} else if (tipoLogger.equals(ConstantesCodigosLogger.TRACE)) {
			LOGGER.trace(mensaje);
			auditoria.setEstadoAuditoria( EstadosAuditoriaEnum.TRACE.getCodigo() );
		} else {
			LOGGER.info(mensaje);
			auditoria.setEstadoAuditoria( EstadosAuditoriaEnum.INFO.getCodigo() );
		}

		String tipoAuditoria = null;
		try {
			tipoAuditoria = serviciosComun.consultarConfiguracionPorCodigo("TIPO_AUDITORIA").getMensaje();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		if ( tipoAuditoria.equals( "AWS" ) ) {

		} else if ( tipoAuditoria.equals( "JAVA" ) ) {

			try {
				if (tipoLogger.equals(ConstantesCodigosLogger.ERROR) ||
						tipoLogger.equals(ConstantesCodigosLogger.WARN) ||
						tipoLogger.equals(ConstantesCodigosLogger.INFO)) {
					serviciosAuditoriaLog.insertarAuditoria(auditoria);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}

	public void registrarLoggerError (String mensaje, Class clase, Exception e) {

		Logger LOGGER = LoggerFactory.getLogger(clase);

		LOGGER.error(mensaje, e);

		String tipoAuditoria = null;
		try {
			tipoAuditoria = serviciosComun.consultarConfiguracionPorCodigo("TIPO_AUDITORIA").getMensaje();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		AuditoriaDTO auditoria = construirDTOAuditoria( mensaje, clase );

		auditoria.setDescripcion( e.getMessage() );
		auditoria.setEstadoAuditoria( EstadosAuditoriaEnum.ERROR.getCodigo() );

		if ( tipoAuditoria.equals( "AWS" ) ) {

		} else if ( tipoAuditoria.equals( "JAVA" ) ) {

			try {
				serviciosAuditoriaLog.insertarAuditoria( auditoria );
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}

		}

	}

	private AuditoriaDTO construirDTOAuditoria( String mensaje, Class clase){
		AuditoriaDTO auditoria = new AuditoriaDTO();

		auditoria.setIp( ipAccesoUsuario(request) );

		String estadoPagina = obtenerHeader("state");

		Long idRol = RolesUsuarioEnum.COLOCADOR.getCodigo();

		Date fechaAuditoria = new Date();

		auditoria.setFecha( formatFecha( fechaAuditoria ) );
		auditoria.setHora( formatHora( fechaAuditoria ) );

		auditoria.setIdRol( idRol );
		auditoria.setClase( clase.getName() );
		auditoria.setParametros( mensaje );

		if ( estadoPagina != null ) {

			if (estadoPagina.contains("/h/")) {
				auditoria.setUsuarioCreacion( "USUARIO PUBLICO TRANSACCIONAL" );
			} else {

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication != null && authentication.isAuthenticated()) {
					String nombreUsuario = authentication.getName();
					if ( nombreUsuario.contains( ";" ) ) {
						nombreUsuario = nombreUsuario.split(";")[0];
						auditoria.setUsuarioCreacion(nombreUsuario);
					} else {
						auditoria.setUsuarioCreacion( "SIN USUARIO LOGEADO TRANSACCIONAL" );
					}
				} else {
					auditoria.setUsuarioCreacion( "SIN USUARIO LOGEADO TRANSACCIONAL" );
				}


			}

			auditoria.setIdFuncionalidadAuditoria(auditoriaLogMapper.consultarFuncionalidadPorURL(estadoPagina));

			if (auditoria.getIdFuncionalidadAuditoria() == null) {
				if (estadoPagina.contains("/h/")) {
					auditoria.setIdFuncionalidadAuditoria(auditoriaLogMapper.consultarFuncionalidadPorURL("/h/"));
				}
			}

		}

		return auditoria;
	}

	private String obtenerHeader(String header){
		String nameHeader =null;

		if(request!=null){
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				if(key!=null){
					if(key.equals(header)) {
						nameHeader = request.getHeader(key);
						break;
					}
				}
			}
		}
		return nameHeader;
	}

	private String ipAccesoUsuario(HttpServletRequest request){

		String ipAcceso = null;
		Object obj;
		Object obj1;

		try {
        /*	if (ipAcceso == null) {
				ipAcceso = request.getHeader("X-FORWARDED-FOR");
			}

			if (ipAcceso == null) {
				ipAcceso = request.getHeader("x-forwarded-for");
			}

			if (ipAcceso == null) {
				ipAcceso = request.getHeader("REMOTE_HOST");
			}

			if (ipAcceso == null) {
				ipAcceso = request.getHeader("remote_host");
			}


			if (ipAcceso == null || ipAcceso.equals("::1")) {
				ipAcceso = request.getRemoteAddr();
				if(ipAcceso.equals("0:0:0:0:0:0:0:1")){
					ipAcceso = null;
				}
			}
*/
			if(ipAcceso ==null){

				outerloop:
				for (Enumeration<?> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {
					obj = e.nextElement();
					if ( obj instanceof NetworkInterface ) {
						NetworkInterface ni = (NetworkInterface) obj;
						for (Enumeration<?> ee = ni.getInetAddresses(); ee.hasMoreElements();) {
							obj1 = ee.nextElement();
							if ( obj1 instanceof InetAddress) {
								InetAddress ip = (InetAddress) obj1;
								if (ip instanceof Inet4Address && !ip.getHostAddress().equals("127.0.0.1")){
									ipAcceso = ip.getHostAddress();
									break outerloop;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}

		return ipAcceso;
	}

	public synchronized String formatHora(Date date) {
		return formato.format(date);
	}

	public synchronized String formatFecha(Date date) {
		return formatoFecha.format(date);
	}


}