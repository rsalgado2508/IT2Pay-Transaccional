package co.com.it2ex.it2pay.util.modelo.mensajeria;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 30/6/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class ContenidoFuncionLambdaCorreoDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6021995564727145031L;

	private String sender;

	private String recipient;

	private String templateName;

	private String values;

}
