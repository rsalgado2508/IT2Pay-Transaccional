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
public class DatosBasicosUsuarioDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -3757433848799918367L;
    private Long idUsuario;
    private String login;
    private String nombres;
    private String apellidos;
    private Long idRol;
    private String userName;
    private String empresa;
    private String ultimaVisita;
    private String nombreRol;
    private String ip;
    private String numeroIntentos;
    private Long idPersona;
    private String tipo;
    private String fechaActual;
    private boolean contrasenaVigente;
    private String delegado;
    private String correo;
    private String celular;
    private String aceptaTerminos;
    private boolean iniciarSession;
    private boolean mostrarCaptcha;
    private String correoSinOfuscar;
    private String celularSinOfuscar;
    private String nroId;
    private String tpId;
    private boolean cambioContrasenaMasivo;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String estado;
}
