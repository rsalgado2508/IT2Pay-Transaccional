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
public class BaseConsultaDTO implements Serializable{

    private static final long serialVersionUID = -4111914743681622602L;
    private String codigo;
    private String mensaje;
}
