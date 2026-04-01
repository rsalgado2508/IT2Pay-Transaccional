package co.com.it2ex.it2pay.seguridad.modelo.usuarios;

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
public class RolDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -469376007424917867L;
    private Long idRol;
    private String nombreRol;
    private String estado;

}
