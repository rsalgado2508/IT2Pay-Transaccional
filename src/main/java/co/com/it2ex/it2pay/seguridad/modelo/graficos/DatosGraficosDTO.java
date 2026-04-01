package co.com.it2ex.it2pay.seguridad.modelo.graficos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Repository
public class DatosGraficosDTO implements Serializable {

    private static final long serialVersionUID = 6914175541212986697L;

    private String nombreRol;
    private Long idRol;
    private Date fecha;
    private Long contador;

}