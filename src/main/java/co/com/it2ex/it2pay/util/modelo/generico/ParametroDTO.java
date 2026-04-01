package co.com.it2ex.it2pay.util.modelo.generico;

import java.util.List;

import co.com.it2ex.it2pay.util.modelo.listadetalle.DetalleListaDTO;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class ParametroDTO implements Serializable{

    private static final long serialVersionUID = 4030207770649921960L;
    private Long idParametro;
    private String nombreParametro;
    private String descripcionParametro;
    private String expresion;
    private String valor;
    private String valores;
    private String estado;
    private Long ordenParametro;
    private Long idGrupoParametro;
    private String nombreGrupoParametro;
    private String descripcionGrupoParametro;
    private Long ordenGrupoParametro;
    private String usuarioModificacion;

}
