package co.com.it2ex.it2pay.util.modelo.auditoria;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 8/8/2023
 */

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AuditoriaDTO implements Serializable {

    private static final long serialVersionUID = -2569491080948034262L;

    private Long idAuditoria;
    private Long idFuncionalidadAuditoria;

    private String nombreFuncionalidad;
    private String nombreRol;


    private Long idRol;
    private String descripcion;
    private String ip;
    private String parametros;
    private String estadoAuditoria;
    private String clase;
    private String fecha;
    private String hora;

    private Date fechaCreacion;
    private Date fechaModificacion;

    @JsonIgnore
    private String usuarioCreacion;

    @JsonIgnore
    private String usuarioModificacion;

}
