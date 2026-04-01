package co.com.it2ex.it2pay.seguridad.modelo.token;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 9/5/2023
 */

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class TokenDTO implements Serializable{

    private static final long serialVersionUID = 60843451802640667L;
    private String token;

    /** The codigo respuesta. */
    private String codigoRespuesta;

    /** The descripcion respuesta. */
    private String mensajeRespuesta;

}
