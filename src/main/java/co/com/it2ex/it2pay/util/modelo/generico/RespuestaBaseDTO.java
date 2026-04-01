package co.com.it2ex.it2pay.util.modelo.generico;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 20/6/2023
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class RespuestaBaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 463674934623146998L;
	
	
	/** The codigo. */
	private String codigoRespuesta;
	
	/** The mensaje. */
	private String mensajeRespuesta;
	
	/** The mensaje. */
	private String mensajeInterno;
	
	/** The isValido */
	private boolean esValido;

}
