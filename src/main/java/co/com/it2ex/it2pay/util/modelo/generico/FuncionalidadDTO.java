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
public class FuncionalidadDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = -3720528480200539363L;
    private Long idFuncionalidad;
    private String nombre;
    private String activo;
    private Long idModulo;
    private String parametrizable;
    private String url;
    private String esPrincipal;
    private String permiteDobleIntervencion;
    private String solicitaDobleIntervencion;
    private String usuarioRegistro;
    private String estadoRegistro;
    private String operacionAprobar;
    private String loginAutoriza;
    private Long idRoles;
    private String tipo;
    private Long idPersona;
    private boolean validacionToken;
    private boolean mostrarAlertaAutenticacion;
    private String tipoAutenticacionFuerte;
    private String tipoUsuario;
    private String posicionAutenticacion;
    private Long idUsuario;
    private String nombreFuncionalidad;
    private boolean usuarioAsignado;
    private Long idFuncionalidadRegistro;
    private String requiereOtp;
}
