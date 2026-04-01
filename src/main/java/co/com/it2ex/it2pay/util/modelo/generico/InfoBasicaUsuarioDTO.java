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
public class InfoBasicaUsuarioDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 3349822957401514950L;
    private String nroId;
    private String tipoDocumentoEmpresa;
    private String numeroDocumentoEmpresa;
    private String tipoDocumento;
    private String numeroDocumento;
    private String numeroProducto;
    private String numeroIdentificacionEmpresa;
    private String usuario;
    private String naturaleza;
    private String correo;
    private String celular;
    private String correoSinOfuscar;
    private String celularSinOfuscar;
    private Long id;
    private String tipoUsuario;
    private String numNotificacion;
    private String captchaResponse;
    private boolean migradoEnrutamientoApagado = false;
    private String loginActual;
    private String url;

}
