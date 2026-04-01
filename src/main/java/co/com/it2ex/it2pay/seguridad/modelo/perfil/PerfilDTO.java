package co.com.it2ex.it2pay.seguridad.modelo.perfil;

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
public class PerfilDTO implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5069774505891161642L;

    /** usuario punto de venta. */
    private String tipoDocumento;

    private String numeroDocumento;

    private String primerNombre;

    private String segundoNombre;

    private String primerApellido;

    private String segundoApellido;

    private String nombreRol;

    private String estado;

    private String email;

    private String celular;

    /** The codigo respuesta. */
    private String codigoRespuesta;

    /** The descripcion respuesta. */
    private String mensajeRespuesta;

}
