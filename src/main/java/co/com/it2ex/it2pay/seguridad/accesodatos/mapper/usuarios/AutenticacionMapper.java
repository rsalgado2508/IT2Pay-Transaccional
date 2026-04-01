package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios;

import co.com.it2ex.it2pay.seguridad.modelo.token.DatosAutenticacionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AutenticacionMapper {

    @Select(value= "SELECT LOGIN usuario, CLAVE usuarioContr "
            + "FROM \"SEG_USUARIO\" "
            + "WHERE "
            + "LOGIN = #{usuario} ")
    public DatosAutenticacionDTO autenticacion(String usuario);

}
