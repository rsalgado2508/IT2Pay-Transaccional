package co.com.it2ex.it2pay.util.otros.exception;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 26/4/2023
 */

public class IT2PayException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3548673470399128582L;
	private Exception excepcion = null;
	
	public IT2PayException() {
		super();
	}

	public IT2PayException(String message) {
		super(message);
	}

	public IT2PayException(Exception e) {
		super(e);
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public static IT2PayException detalleError(Exception e) {
		IT2PayException it2PayException;
		if (e instanceof IT2PayException) {
			it2PayException = (IT2PayException) e;
		} else {
			it2PayException = new IT2PayException(e);
		}
		Throwable t;
		if (e.getCause() != null) {
			for (t = e.getCause(); t.getCause() != null; t = t.getCause()) {
				it2PayException = new IT2PayException(t.getMessage());
			}
			String mensaje = it2PayException.getMessage();
			int inicio = mensaje.indexOf("Detail:");
			int fin = mensaje.indexOf("Error Code:");
			if (inicio > 0 && fin > inicio) {
				mensaje = mensaje.substring(inicio, fin);
			}
			if (!"".equals(mensaje)) {
				if (!mensaje.contains("NoResultException")) {
					it2PayException = new IT2PayException(mensaje);
				} else {
					it2PayException = new IT2PayException("SIN_DATOS");
				}
			}
		}
		return it2PayException;
	}

}
