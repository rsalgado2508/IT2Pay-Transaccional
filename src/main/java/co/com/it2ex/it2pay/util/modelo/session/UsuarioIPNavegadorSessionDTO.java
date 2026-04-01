package co.com.it2ex.it2pay.util.modelo.session;

import org.springframework.stereotype.Repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class UsuarioIPNavegadorSessionDTO implements Serializable {

    private static final long serialVersionUID = 34387603007759653L;
    String login;
    String tipoUsuario;
    Date fechaUltimoIngreso;

}
