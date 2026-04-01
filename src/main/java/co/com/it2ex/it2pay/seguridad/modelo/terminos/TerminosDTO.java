package co.com.it2ex.it2pay.seguridad.modelo.terminos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 2/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TerminosDTO extends AuditoriaDTO implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5057350471213236803L;
	
	/** The codigo respuesta. */
	private String codigoRespuesta;	
	
	/** The descripcion respuesta. */
	private String mensajeRespuesta;
	
	/** The terminos. */
	private String terminos;

	/** The id terminos. */
	private Long idTerminos;

	/** The id usuario. */
	private Long idUsuario;

	private String usuario;
	
}