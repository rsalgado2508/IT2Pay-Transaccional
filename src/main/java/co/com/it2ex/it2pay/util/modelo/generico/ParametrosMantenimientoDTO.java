package co.com.it2ex.it2pay.util.modelo.generico;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class ParametrosMantenimientoDTO implements Serializable {

    private static final long serialVersionUID = 8157945805350669574L;
    private String idParametro;
    private String descripcion;
    private String valor;
    private Long idGrupoParametro;
    private String valores;
    private String valorAnterior;
    private Long tipo;
    private String expresion;
    private String cambio;
    private List<String> valorLista = new ArrayList();

}
