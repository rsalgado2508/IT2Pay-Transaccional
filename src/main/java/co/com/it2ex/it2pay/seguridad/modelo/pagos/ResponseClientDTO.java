package co.com.it2ex.it2pay.seguridad.modelo.pagos;/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 27/10/2023
 */

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
public class ResponseClientDTO implements Serializable {

    private static final long serialVersionUID = 7608812268981648635L;
    private String body;
    private Long statusCode;
}
