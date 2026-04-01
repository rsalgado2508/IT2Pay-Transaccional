package co.com.it2ex.it2pay.seguridad.modelo.infoapp;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 31/7/2023
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
public class InfoAppDTO implements Serializable{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3626932022405627155L;

	private String versionAPP;
	private String versionBD;
	private String codigoRespuesta;
	private String mensajeRespuesta;






}