package co.com.it2ex.it2pay.util.modelo.intento;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 13/6/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class IntentoUsuarioDTO extends AuditoriaDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5435952397069117679L;

	private Long idUsuario;

	private String usuario;

	private String tipoIntento;

	private int numeroIntento;

	private String parametroIntento;

	private Long idFuncionalidad;

	private String codigoRespuesta;

	private String mensajeRespuesta;

	public IntentoUsuarioDTO(String usuario, String tipoIntento, String parametroIntento){
		this.usuario = usuario;
		this.tipoIntento = tipoIntento;
		this.parametroIntento = parametroIntento;
	}


}
