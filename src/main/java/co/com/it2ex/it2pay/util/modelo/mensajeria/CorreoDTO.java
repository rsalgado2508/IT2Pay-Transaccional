package co.com.it2ex.it2pay.util.modelo.mensajeria;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * The Class MensajeDTO.
 * DTO contiene la información de los mensajes a enviar en el módulo de notificaciones
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class CorreoDTO extends AuditoriaDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8062739606658529923L;

	/** The tipo. */
	private Integer tipo;

	/** The para. */
	private String para;

	/** The para nombre. */
	private String paraNombre;

	/** The asunto. */
	private String asunto;

	/** The contenido. */
	private String contenido;

	/** The id. */
	private Long id;

	/** The adjunto. */
	private ArchivoAdjuntoDTO adjunto;
	
	/** The adjunto. */
	private List<ArchivoAdjuntoDTO> listaAdjunto;

	/** The remitente. */
	private String remitente;
	
	/** The copia. */
	private String[] copia;
	
	/** The copia oculto. */
	private String[] copiaOculto;

	private String plantilla;

}
