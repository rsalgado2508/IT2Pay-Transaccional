package co.com.it2ex.it2pay.util.negocio.servicios.log;

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

import co.com.it2ex.it2pay.util.accesodatos.mapper.auditoria.AuditoriaLogMapper;
import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiciosAuditoriaLogImpl implements ServiciosAuditoriaLog {

    @Autowired
    private AuditoriaLogMapper auditoriaLogMapper;

    @Async
    public void insertarAuditoria(AuditoriaDTO dto) throws Exception {


        try {

            auditoriaLogMapper.insertarAuditoria( dto );

        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(this.getClass());
            LOGGER.error(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO, e);
        }
    }


}