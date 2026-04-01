package co.com.it2ex.it2pay.seguridad.modelo.puntoventa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional backend
 * @Copyright IT2Ex
 *
 * @Autor: Santiago Orjuela
 * @FechaCreación: 18/7/2023
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class CupoPuntoVentaDTO {
    private Long idCupoPuntoVentaDTO;
    private Long idPuntoVenta;
    private Long idLineaNegocio;
    private String lineaNegocio;
    private Long cupoGlobal;
    private Long cupoDisponible;
}
