package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios;

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;

@Mapper
public interface PersonaMapper {

    @Insert("INSERT INTO \"SEG_PERSONA\" (id_tipo_documento, numero_documento, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, naturaleza, id_tipo_persona, correo, telefono, direccion, fecha_creacion, usuario_creacion, estado, id_municipio) " +
            " VALUES(#{idTipoDocumento}, #{numeroDocumento}, #{primerNombre}, #{segundoNombre}, #{primerApellido}, #{segundoApellido}, 'N', 'USU', #{correo}, #{telefono}, #{direccion}, CURRENT_TIMESTAMP, #{usuarioCreacion}, 'A', #{idMunicipio}) ")
    @Options(useGeneratedKeys=true, keyProperty="idPersona")
    public void crearPersona(UsuarioDTO usuario);

    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update(" UPDATE \"SEG_PERSONA\" SET id_tipo_documento=#{idTipoDocumento}, numero_documento=#{numeroDocumento}, primer_nombre=#{primerNombre}, segundo_nombre=#{segundoNombre}, primer_apellido=#{primerApellido}, segundo_apellido=#{segundoApellido}, " +
            " naturaleza='N', correo=#{correo}, telefono=#{telefono}, direccion=#{direccion}, fecha_modificacion=CURRENT_TIMESTAMP, usuario_modificacion=#{usuarioModificacion}, id_municipio=#{idMunicipio} " +
            " WHERE id_persona= #{idPersona} ")
    public void modificarPersona(UsuarioDTO usuario);

    @Select("SELECT COUNT(*) FROM \"SEG_PERSONA\" P WHERE P.ID_TIPO_DOCUMENTO = #{idTipoDocumento} AND P.NUMERO_DOCUMENTO = #{numeroDocumento} ")
    public Long validarExisteTipoNumeroDocumento(UsuarioDTO usuario);

    @Select("SELECT COUNT(*)  FROM \"SEG_PERSONA\" P WHERE P.CORREO = #{correo} ")
    public Long validarExisteCorreo(UsuarioDTO usuario);

}
