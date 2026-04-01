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
public class PreguntaUsuarioDTO implements Serializable {

    private static final long serialVersionUID = -8132540365489696534L;
    private Long idUsuario;
    private Long idPregunta;
    private String pregunta;
    private String respuesta;
    private String usuario;
    private String estado;
    private String confirmacionRespuesta;

}
