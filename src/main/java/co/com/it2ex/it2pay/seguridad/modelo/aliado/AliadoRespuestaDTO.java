package co.com.it2ex.it2pay.seguridad.modelo.aliado;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 21/7/2023
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
public class AliadoRespuestaDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3728723216964140756L;

	/** The codigo respuesta. */
	private String codigoRespuesta;

	/** The descripcion respuesta. */
	private String mensajeRespuesta;

	/** The codigo otp. */
	private AliadoDTO lista;
	

	
}