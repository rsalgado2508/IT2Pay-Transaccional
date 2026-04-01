package co.com.it2ex.it2pay.util.modelo.session;

import java.util.Date;
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
public class InfoIPNavegadorSessionDTO implements Serializable {

    private static final long serialVersionUID = 1829171263551227639L;
    private String login;
    private String tipoUsuario;
    private String direccionIP;
    private String navegador;
    private String sessionId;
    private Date fechaRegistro;

}