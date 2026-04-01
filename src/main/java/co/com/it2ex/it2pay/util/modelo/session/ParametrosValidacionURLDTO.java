package co.com.it2ex.it2pay.util.modelo.session;

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
public class ParametrosValidacionURLDTO implements Serializable{

    private static final long serialVersionUID = 5444807969387783518L;
    private String login;
    private String urlFuncionalidad;
    private String usuario;
    private String idRol;
    private String loginActual;
}
