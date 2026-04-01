package co.com.it2ex.it2pay.seguridad.modelo.login;



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
public class LoginEntradaDTO extends AuditoriaDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8238600844491931442L;

    /** usuario punto de venta. */
    private String login;

    /** clave usuario punto de venta. */
    @ToString.Exclude
    private String clave;

    }
