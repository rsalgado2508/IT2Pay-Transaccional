package co.com.it2ex.it2pay.util.modelo.generico;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 28/4/2023
 */

import java.io.Serializable;

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class ParametrosLoginDTO extends AuditoriaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6117842173001155685L;

	private String login; 
	
	private String usuario;

	private String loginActual;

}
