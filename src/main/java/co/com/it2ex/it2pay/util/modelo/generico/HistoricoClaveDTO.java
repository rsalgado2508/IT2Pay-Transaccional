package co.com.it2ex.it2pay.util.modelo.generico;

import java.io.Serializable;
import java.sql.Date;

import co.com.it2ex.it2pay.util.modelo.auditoria.AuditoriaDTO;
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
public class HistoricoClaveDTO extends AuditoriaDTO implements Serializable {

    private static final long serialVersionUID = -5580309979764181132L;
    private Long id;
    private String clave;
    private Long usuario;

}
