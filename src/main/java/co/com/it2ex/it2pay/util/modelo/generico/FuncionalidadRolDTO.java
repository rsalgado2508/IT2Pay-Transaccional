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
public class FuncionalidadRolDTO implements Serializable{


    private static final long serialVersionUID = 7963687786018827477L;
    private Long idFuncionalidades;
    private Long idRoles;
    private String nombre;
    private String dobeIntervencion;
    private boolean seleccionEliminar;

}
