package co.com.it2ex.it2pay.util.modelo.generico;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 19/5/2023
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
public class EstadoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2884196942476445705L;
	
	
	private boolean estado;
	private String codigoRespuesta;
	private String mensajeRespuesta;

	

}
