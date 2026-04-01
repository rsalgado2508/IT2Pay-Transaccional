package co.com.it2ex.it2pay.seguridad.modelo.aliado;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 21/7/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
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
public class AliadoDTO extends AuditoriaDTO implements Serializable{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5013174623145178651L;

	private Long idAliado;
	private String nombreAliado;
	private String imagen;
	private String idEstadoAliado;
	private String estadoAliado;
	private String idTipoDocumento;
	private String nombreTipoDocumento;
	private String numeroDocumento;
	private String usuarioDeModificacionAliado;
	private String usuarioCreacionAliado;
	private String valorCaracteresImagen;





}