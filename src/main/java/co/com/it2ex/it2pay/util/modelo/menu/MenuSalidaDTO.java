package co.com.it2ex.it2pay.util.modelo.menu;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 16/5/2023
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class MenuSalidaDTO implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4079090936167138908L;

	/** The id menu. */
	private Long idMenu;
	
	/** The nombre. */
	private String nombre;
	
	/** The id padre menu. */
	private Long idPadreMenu;
	
	/** The sub menu. */
	private List<MenuSalidaDTO> subMenu = new ArrayList<MenuSalidaDTO>();
	
	/** The orden. */
	private Long orden;
	
	/** The url. */
	private String url;

	private String imagen;

	private Long tipo;

	public MenuSalidaDTO(MenuDTO menuDto){
		this.idMenu = menuDto.getIdMenu();
		this.nombre = menuDto.getNombre();
		this.idPadreMenu = menuDto.getIdPadreMenu();
		this.orden = menuDto.getOrden();
		this.url = menuDto.getUrl();
		this.imagen = menuDto.getImagen();
		this.tipo = menuDto.getTipo();
	}

}
