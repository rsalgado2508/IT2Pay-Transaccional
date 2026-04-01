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

import java.io.File;
import java.io.Serializable;

/**
 * The Class ArchivoAdjuntoDTO.
 * DTO  con la información de los archivos adjuntos a enviar junto con el mensaje.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class ArchivoAdjuntoDTO extends AuditoriaDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4591647001172864997L;
	
	/** The path. */
	private String path;
	
	/** The url. */
	private String url;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The nombre. */
	private String nombre;
	
	private File archivo;


}
