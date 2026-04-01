package co.com.it2ex.it2pay.util.modelo.menu;

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
import java.util.ArrayList;
import java.util.List;

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
public class MenuDTO extends AuditoriaDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5859397854876994098L;

	private Long idFuncionalidad;

	/** The id menu. */
	private Long idMenu;

	/** The nombre. */
	private String nombre;

	/** The id padre menu. */
	private Long idPadreMenu;

	private String imagen;

	/** The sub menu. */
	private List<MenuDTO> subMenu = new ArrayList<MenuDTO>();

	/** The orden. */
	private Long orden;

	/** The url. */
	private String url;

	private Long tipo;


}
