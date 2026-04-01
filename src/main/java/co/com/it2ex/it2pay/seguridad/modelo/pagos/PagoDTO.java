package co.com.it2ex.it2pay.seguridad.modelo.pagos;/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 13/9/2023
 */

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
public class PagoDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -7324659887912743703L;
    private String login;
    private Long idProducto;
    private Long idAliado;
    private String valor;
    private String labelReferencia1;
    private String labelReferencia2;
    private String labelReferencia3;
    private String labelReferencia4;
    private String labelReferencia5;
    private String nombreReferencia1;
    private String nombreReferencia2;
    private String nombreReferencia3;
    private String nombreReferencia4;
    private String nombreReferencia5;
    private String referencia1;
    private String referencia2;
    private String referencia3;
    private String referencia4;
    private String referencia5;

}
