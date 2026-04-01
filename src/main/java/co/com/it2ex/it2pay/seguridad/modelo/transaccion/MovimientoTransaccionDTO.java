package co.com.it2ex.it2pay.seguridad.modelo.transaccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo backend
 * @Copyright IT2Ex
 *
 * @Autor: jlalfonso
 * @FechaCreación: 27/07/2023
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class MovimientoTransaccionDTO implements Serializable {
    private static final long serialVersionUID = 4183405007340667597L;
    private Long idMovimientoTransaccion;
    private Long idTransaccion;
    private Long idReporte;
    private Long idLineaNegocio;
    private String nombreLineaNegocio;
    private Long idAliado;
    private String nombreAliado;
    private String cliente;
    private String codigoProducto;
    private String nombreProducto;
    private String moneda;
    private Long monto;
    private String referencia;
    private String factura;
    private Date fechaInicio;
    private String fechaFin;
    private String fechaFormat;
    private String estado;
    private Long idPuntoVenta;
    private String nombrePuntoVenta;
    private Long idColocador;
    private String nombreColocador;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;
    private Long numeroDias;
    private String correoEnvioComprobante;
    private String ubicacionPuntoVenta;
    private String codigoPuntoVenta;
    private String colocador;
    private String codigoConvenio;
    private String nombreConvenio;
    private String mensaje;
    private String error;
    private String mensajeError;
}
