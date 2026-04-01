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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class LoginDTO implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5013174623145558651L;

    /** usuario punto de venta. */
    private String login;

    /** clave usuario punto de venta. */
    private String clave;

    /** Aceptar términos y condiciones. */
    private String acepta_terminos;

    /** Fecha de aceptación de los términos y condiciones */
    private Date fecha_acepta_terminos;

    /** Identificación de la persona */
    private String  id_persona;

    /** Estado del usuario del punto de venta. */
    private String estado;

    /** Fecha último login */
    private Date fecha_ultimo_login;

    /** Fecha creación usuario punto de venta. */
    private Date fecha_creacion;

    /** Usuario quien creó al usuario punto de venta. */
    private String usuario_creacion;

    /** Fecha modificación usuario punto de venta. */
    private Date fecha_modificacion;

    /** Usuario quien modificó al usuario punto de venta. */
    private String usuario_modificacion;

    /** Id punto de venta. */
    private Long id_punto_venta;

    /** Id términos y condiciones */
    private Long id_terminos_condiciones;

}
