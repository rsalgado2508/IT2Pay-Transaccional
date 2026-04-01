package co.com.it2ex.it2pay.seguridad.modelo.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: jgutierrez
 * @FechaCreación: 28/4/2023
 */

import java.io.Serializable;
import java.sql.Date;
import co.com.it2ex.it2pay.util.modelo.generico.InfoBasicaUsuarioDTO;
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
public class UsuarioDTO extends InfoBasicaUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1377685673078346261L;

    private Long idPersona;
    private String idTipoDocumento;
    private String tipoDocumento;
    private String numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String razonSocial;
    private String naturaleza;
    private String idTipoPersona;
    private String correo;
    private String telefono;
    private String direccion;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;
    private String estado;
    private Long idMunicipio;
    private String nombreMunicipio;
    private Long idDepartamento;
    private Long idRepresentante;
    private Long idUsuario;
    private String login;
    private String clave;
    private String claveRenovar;
    private String claveRenovar2;
    private String aceptaTerminos;
    private Date fechaAceptaTerminos;
    private Date fechaUltimoLogin;
    private Long idPuntoVenta;
    private Long idTerminosCondiciones;
    private Long idRol;
    private String nombreRol;
    private Long numeroHistorico;
    private Long idAliado;
    private Long idProducto;

}
