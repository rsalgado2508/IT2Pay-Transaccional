package co.com.it2ex.it2pay.seguridad.modelo.recuperarclave;



/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Jorge Ruiz
 * @FechaCreación: 26/4/2023
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
public class RecuperarEntradaDTO extends AuditoriaDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1100331961304579493L;

    private String login;

    private Long idUsuario;

    private String tipoDocumento;

    private String numeroDocumento;

    }
