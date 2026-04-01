package co.com.it2ex.it2pay.seguridad.modelo.transaccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo backend
 * @Copyright IT2Ex
 *
 * @Autor: jlalfonso
 * @FechaCreación: 01/08/2023
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class MovimientoTransaccionRespuestaDTO implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 9038254006732304358L;
    /** The codigo respuesta. */
    private String codigoRespuesta;

    /** The descripcion respuesta. */
    private String mensajeRespuesta;

    /** The punto de venta. */
    private MovimientoTransaccionDTO lista;
}
