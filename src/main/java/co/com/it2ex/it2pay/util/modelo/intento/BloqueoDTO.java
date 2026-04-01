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

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class BloqueoDTO extends AuditoriaDTO implements Serializable{

	
	
	private static final long serialVersionUID = 6889037993049635133L;
	private String idBloqueo;
	private Long idTipoBloqueo;
	private Long idUsuarioBloqueo;
	private String idPersonaBloqueo;
	private String estadoBloqueo;
	private String loginUsuarioBloqueo;
	private String bloqueoDestino;
	private String idParametro;
	private String tipoIntento;
	private Long idFuncionalidad;
	private Long id;
	private String codigoRespuesta;

	private String mensajeRespuesta;


}
