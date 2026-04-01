package co.com.it2ex.it2pay.util.modelo.session;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class RespuestaValidacionIPNavegadorDTO implements Serializable {

    private static final long serialVersionUID = -8548687065776476980L;
    public static final String ACCION_REQUERIDA_ELIMINAR_SOLO_INGRESO_EXITOSO_USUARIO ="eliminarSoloIngresoExitosoUsuario";
    public static final String ACCION_REQUERIDA_ELIMINAR_SOLO_INGRESO_FALLIDO_USUARIO ="eliminarSoloIngresoFallidoUsuario";
    public static final String ACCION_REQUERIDA_ELIMINAR_TODA_INFO_USUARIO ="eliminarTodaInfoUsuario";
    public static final String ACCION_REQUERIDA_ELIMINAR_TODA_INFO_USUARIO_SIN_POPUP ="eliminarTodaInfoUsuarioSinPopup";
    public static final String ACCION_REQUERIDA_INVALIDAR_SESION_USUARIO ="invalidarSesionUsuario";
    public static final String ACCION_REQUERIDA_NINGUNA ="ninguna";
    private String accionRequerida;
    private boolean valido;
    private List<InfoIPNavegadorSessionDTO> listaInfoIPNavegadorPorProcesar;
}
