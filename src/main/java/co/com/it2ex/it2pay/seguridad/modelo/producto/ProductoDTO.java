package co.com.it2ex.it2pay.seguridad.modelo.producto;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 25/7/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
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
public class ProductoDTO extends BaseDTO implements Serializable {

    /**
     *The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -5013174623145178651L;

    private Long idProducto;
    private String nommbreProducto;
    private String descripcionProducto;
    private String codigoPropioProducto;
    private Long idAliado;
    private Long idLineaNegocioProducto;
    private String estadoProducto;
    private String idEstadoProducto;

    private String fechaCreacionProducto;
    private String usuarioCreacionProducto;
    private String fechaModificacionProducto;
    private String usuarioModificacionProducto;
    private Long idTipoProducto;
    private String nombreTipoProducto;
    private Long idCategoriaProducto;

    private String nombreLineaNegocioProducto;
    private String nombreAliado;
    private String nombreConvenioProducto;
    private String nombreCategoriaProducto;
    /**
     * parametros productos
     **/
    private String mensaje;
    private String lecturaCodigoBarras;
    private String aceptaFacturasVencidas;
    private String aceptaPagosParciales;
    private String aceptaPagosDobles;
    private String montoMinimo;
    private String montoMaximo;
    private String ventanaOperacionInicio;
    private String ventanaOperacionFinal;
    private String permiteAnulacion;
    private String referenciaCaptura;
    private Long idConvenioProducto;
    private String multiplosPagos;
    private String montoEditable;
    private String imagen;
    private String fijo;
    private String variable;


}