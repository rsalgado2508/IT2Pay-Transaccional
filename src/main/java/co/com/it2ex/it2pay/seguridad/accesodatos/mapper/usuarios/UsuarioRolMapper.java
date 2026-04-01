package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios;

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;

@Mapper
public interface UsuarioRolMapper {


    @Insert(" INSERT INTO \"SEG_USUARIO_ROL\" (id_usuario, id_rol, estado, fecha_creacion, usuario_creacion) " +
            " VALUES( #{idUsuario}, #{idRol}, 'A', CURRENT_TIMESTAMP, #{usuarioCreacion}) ")
    public void crearRolUsuario(UsuarioDTO usuario);

    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update(" UPDATE \"SEG_USUARIO_ROL\" SET estado = 'A', id_rol = #{idRol}, " +
            " fecha_modificacion = CURRENT_TIMESTAMP, usuario_modificacion = #{usuarioModificacion} " +
            " WHERE id_usuario = #{idUsuario}  ")
    public void modificarRolUsuario(UsuarioDTO usuario);

}
