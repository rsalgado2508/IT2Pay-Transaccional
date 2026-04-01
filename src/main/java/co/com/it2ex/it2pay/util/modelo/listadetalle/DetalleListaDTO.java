package co.com.it2ex.it2pay.util.modelo.listadetalle;

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
public class DetalleListaDTO implements Serializable {

	private static final long serialVersionUID = 6765539190582534855L;
	private Long id_detalle_lista;
	private Long  idLista;
	private String valor;
	private String descripcion;
	private Long orden;


}
