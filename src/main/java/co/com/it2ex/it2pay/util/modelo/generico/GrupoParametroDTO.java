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
public class GrupoParametroDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = -1904898272936364261L;
    private Long idGrupo;
    private String nombre;
    private String descripcion;
    private  Integer orden;
    private Long modulo;

}
