package co.com.it2ex.it2pay.util.modelo.tiposdocumentos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 4/5/2023
 */

import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class TipoDocumentoDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -1354402776781503902L;
    private String idTipoDocumento;
    private String nombre;
    private String naturaleza;

}
