package co.com.it2ex.it2pay.util.modelo.generico;

import java.io.Serializable;

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
public class BaseDTO extends AuditoriaDTO implements Serializable{

    private static final long serialVersionUID = 7193282752117148660L;
    private String codigoRespuesta;
    private String mensajeRespuesta;
    private String mensajeInterno;
    private boolean usuarioConsulta = false;
    private boolean usuarioEdito = false;
    private String estado;
    private String jsonEntrada;
    private String jsonSalida;

}
