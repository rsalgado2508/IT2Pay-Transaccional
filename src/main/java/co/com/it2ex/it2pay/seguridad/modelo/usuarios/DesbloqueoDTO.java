package co.com.it2ex.it2pay.seguridad.modelo.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 20/6/2023
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
public class DesbloqueoDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3574580482323723836L;
	
	private Long idFuncionalidad;
	private String idTipoBloqueo;
	private Long idBloqueo;
	private String idUsuario;
	private String idPersona;
	private Long idParametro;
	private String idRol;
	private String nombreFuncionalidad;
	private String tipoDocumento;
	private String nroDocumento;
	private String nombreUsuario;
	private String fechaBloqueo;
	private String paraDescri;
	private String paraValor;
	private String estadoBloqueo;
	private String rol;
	private String fechaPreparador;
	private String usuarioModificacion;
	private String fechaLiberacion;
	private String usuarioLiberacion;
	private String nombreEmpresa;
	private String nroEmpresa;
	private String descripcionBloqueo;	
	private String usuarioEdito;
	private String bloqueoDestino;
	private String loginActual;

			
}
