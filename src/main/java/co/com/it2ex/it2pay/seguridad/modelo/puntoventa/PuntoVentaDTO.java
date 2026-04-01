package co.com.it2ex.it2pay.seguridad.modelo.puntoventa;

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo backend
 * @Copyright IT2Ex
 *
 * @Autor: jlalfonso
 * @FechaCreación: 27/4/2023
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class PuntoVentaDTO extends AuditoriaDTO implements Serializable {

    private static final long serialVersionUID = 5105222152921865886L;

    /** Constantes  punto de venta**/
    private Long idPuntoVenta;
    private Long codigo;
    private String nombrePuntoVenta;
    private Long idEmpresa;
    private Long idContacto;
    private String estado;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;

    /** Constantes de Persona**/
    private Long idPersona;
    private String idTipoDocumento;
    private String numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String razonSocial;
    private String naturaleza;
    private String idTipoPersona;
    private String direccion;
    private String idDepartamento;
    private Long idRol;
    private Long idMunicipio;
    private String idRepresentante;
    private String usuarioCreacionContacto;

    /** Constantes Detalles**/
    private String cadena;
    private String colocador;
    private String empresa;
    private String departamento;
    private String municipio;
    private String nombreContacto;
    private String documento;
    private String correoContacto;
    private String celularContacto;
    private String telefonoContacto;
    private String direccionContacto;
    private String nombreTipoDocumento;

    /** Constantes Ubicacion**/
    private String barrio;

    /** Constantes Cupo**/
    private List<CupoPuntoVentaDTO> listaCupos;

    /** Constantes RazonSocialPuntoVenta**/
    private String usuNit;
    private String usuRazonSocialPuntoV;
}
