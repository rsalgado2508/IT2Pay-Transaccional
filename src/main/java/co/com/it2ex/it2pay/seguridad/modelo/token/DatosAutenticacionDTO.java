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

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
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
public class DatosAutenticacionDTO extends AuditoriaDTO implements Serializable {

    private static final long serialVersionUID = 5558830820971548283L;

    private String usuario;

    @ToString.Exclude
    private String usuarioContr;

    /** The codigo respuesta. */
    private String codigoRespuesta;

    /** The descripcion respuesta. */
    private String mensajeRespuesta;

}
