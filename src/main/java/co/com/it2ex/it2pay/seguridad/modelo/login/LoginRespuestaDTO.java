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
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class LoginRespuestaDTO extends AuditoriaDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4605307376486243243L;

    private Long idUsuario;

    /** The codigo respuesta. */
    private String login;

    @JsonIgnore
    @ToString.Exclude
    private String clave;

    /** The descripcion respuesta. */
    private String estado;

    private String aceptaTerminos;

    @JsonIgnore
    @ToString.Exclude
    private String correo;

    private Long rol;

    /** The codigo respuesta. */
    private String codigoRespuesta;

    /** The descripcion respuesta. */
    private String mensajeRespuesta;


}
