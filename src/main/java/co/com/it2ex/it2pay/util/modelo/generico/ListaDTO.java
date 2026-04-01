package co.com.it2ex.it2pay.util.modelo.generico;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 26/4/2023
 */

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class ListaDTO implements Serializable{

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = -1389651088866793664L;
    /** Constante codigo. */
    private String codigoRespuesta;
    /** Constante mensaje. */
    private String mensajeRespuesta;
    /** total paginas**/
    private int totalRegistros;
    /** total paginas**/
    private int totalPaginas;
    /** Constante lista. */
    private List<?> lista;

}
