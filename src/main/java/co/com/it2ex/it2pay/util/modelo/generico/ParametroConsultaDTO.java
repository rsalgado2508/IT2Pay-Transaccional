package co.com.it2ex.it2pay.util.modelo.generico;

import java.io.Serializable;
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
public class ParametroConsultaDTO implements Serializable{

    private static final long serialVersionUID = 4225481565785198142L;
    private Long id;
    private String nombre;
    private String valor;
    private String expresion;
    private String valores;
    private String codigoRespuesta;
    private String mensajeRespuesta;
}
