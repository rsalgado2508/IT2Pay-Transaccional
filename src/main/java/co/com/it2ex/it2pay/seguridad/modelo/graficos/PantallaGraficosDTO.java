package co.com.it2ex.it2pay.seguridad.modelo.graficos;

import co.com.it2ex.it2pay.util.modelo.generico.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class PantallaGraficosDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = -8787174135564287449L;

    private String idGraficoPantalla;

    private Map<String, Object> mapaGraficos;

}
