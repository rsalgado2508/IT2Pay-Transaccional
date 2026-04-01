package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios;

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.HistoricoClaveDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface UsuarioMapper {

    @Select("select sp.id_tipo_documento idTipoDocumento, std.nombre tipoDocumento, sp.numero_documento numeroDocumento, sp.primer_nombre primerNombre, " +
            "sp.segundo_nombre segundoNombre, sp.primer_apellido primerApellido, sp.segundo_apellido segundoApellido, sr.id_rol idRol, " +
            "sr.nombre nombreRol, su.estado, su.id_usuario idUsuario ,sp.id_persona idPersona, sp.naturaleza, sp.correo, sp.telefono, sp.direccion, su.login, " +
            "su.acepta_terminos aceptaTerminos, gm.id_municipio idMunicipio, gm.nombre nombreMunicipio, gd.id_departamento idDepartamento " +
            "from \"SEG_PERSONA\" sp " +
            "inner join \"SEG_USUARIO\" su on sp.id_persona = su.id_persona " +
            "inner join \"SEG_USUARIO_ROL\" sur on sur.id_usuario = su.id_usuario " +
            "inner join \"SEG_ROL\" sr on sr.id_rol = sur.id_rol " +
            "inner join \"SEG_TIPO_DOCUMENTO\" std on sp.id_tipo_documento = std.id_tipo_documento " +
            "inner join \"GEN_MUNICIPIO\" gm on sp.id_municipio = gm.id_municipio " +
            "inner join \"GEN_DEPARTAMENTO\" gd on gm.id_departamento = gd.id_departamento " +
            "where sp.estado = 'A' and sp.naturaleza = 'N' and sp.id_tipo_persona = 'USU'")
    public List<UsuarioDTO> listarUsuarios();


    @Select("select sp.id_tipo_documento idTipoDocumento, std.nombre tipoDocumento, sp.numero_documento numeroDocumento, sp.primer_nombre primerNombre, " +
            "sp.segundo_nombre segundoNombre, sp.primer_apellido primerApellido, sp.segundo_apellido segundoApellido, sr.id_rol idRol, " +
            "sr.nombre nombreRol, su.estado, su.id_usuario idUsuario, sp.id_persona idPersona, sp.naturaleza, sp.correo, sp.telefono, sp.direccion, su.login, " +
            "su.acepta_terminos aceptaTerminos, gm.id_municipio idMunicipio, gm.nombre nombreMunicipio, gd.id_departamento idDepartamento " +
            "from \"SEG_PERSONA\" sp " +
            "inner join \"SEG_USUARIO\" su on sp.id_persona = su.id_persona " +
            "inner join \"SEG_USUARIO_ROL\" sur on sur.id_usuario = su.id_usuario " +
            "inner join \"SEG_ROL\" sr on sr.id_rol = sur.id_rol " +
            "inner join \"SEG_TIPO_DOCUMENTO\" std on sp.id_tipo_documento = std.id_tipo_documento " +
            "inner join \"GEN_MUNICIPIO\" gm on sp.id_municipio = gm.id_municipio " +
            "inner join \"GEN_DEPARTAMENTO\" gd on gm.id_departamento = gd.id_departamento " +
            "where sp.estado = 'A' and sp.naturaleza = 'N' and sp.id_tipo_persona = 'USU' " +
            "and sp.id_tipo_documento = #{idTipoDocumento} and sp.numero_documento = #{numeroDocumento}")
    public UsuarioDTO consultarUsuarioPorId(UsuarioDTO usuarioDTO);

    @Insert("INSERT INTO \"SEG_USUARIO\" (login, clave, acepta_terminos, id_persona, estado, fecha_creacion, usuario_creacion, id_terminos_condiciones) " +
            "VALUES(#{login}, #{clave}, 'N', #{idPersona}, 'R', CURRENT_TIMESTAMP, #{usuarioCreacion}, 1) ")
    @Options(useGeneratedKeys=true, keyProperty="idUsuario")
    public void crearUsuario(UsuarioDTO usuario);

    @Cacheable("consultaDatosBasicosUsuarioPorUsuarioClave")
    @Select("select su.id_usuario idUsuario, sp.id_tipo_documento tpId, sp.numero_documento nroId, " +
            "sp.primer_nombre primerNombre, sp.segundo_nombre segundoNombre, " +
            "sp.primer_apellido primerApellido, sp.segundo_apellido segundoApellido," +
            "sur.id_rol idRol, sp.id_persona idPersona, su.estado estado " +
            "from \"SEG_USUARIO\" su " +
            "inner join \"SEG_PERSONA\" sp on su.id_persona = sp.id_persona " +
            "inner join \"SEG_USUARIO_ROL\" sur ON su.id_usuario = sur.id_usuario " +
            "where su.LOGIN=#{login} AND su.CLAVE = #{clave} ")
    public DatosBasicosUsuarioDTO consultaDatosBasicosUsuarioPorUsuarioClave(String login, String clave);

    @Update("update \"SEG_USUARIO\" set fecha_ultimo_login = CURRENT_TIMESTAMP, fecha_anterior_login = ( select  u.fecha_ultimo_login FROM \"SEG_USUARIO\" U WHERE U.login = #{idUsuario} )  where login = #{idUsuario}")
    public void insertarUltimaConexion(String idUsuario);

    @Select("select  u.fecha_anterior_login AT TIME ZONE 'GMT+5' AS fecha_anterior_login FROM \"SEG_USUARIO\" U WHERE "
            + " U.id_usuario = #{idUsuario}")
    public Timestamp consultaUltimaConexion(Long idUsuario);

    @Cacheable("obtenerNombreRol")
    @Select("select  nombre FROM \"SEG_ROL\" U WHERE "
            + " id_rol = #{id}")
    public String obtenerNombreRol(Long id);

    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update(" UPDATE \"SEG_USUARIO\" SET estado=#{estado}, USUARIO_MODIFICACION=#{login}, FECHA_MODIFICACION = CURRENT_TIMESTAMP WHERE id_usuario = #{idUsuario} ")
    public void cambiarEstadoUsuario(UsuarioDTO usuarioDTO);

    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update(" UPDATE \"SEG_USUARIO\" SET estado='A', USUARIO_MODIFICACION=#{login}, FECHA_MODIFICACION = CURRENT_TIMESTAMP WHERE id_usuario = #{idUsuario} ")
    public void desbloquearUsuario(UsuarioDTO usuarioDTO);

    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update(" UPDATE \"SEG_USUARIO\" SET estado='B', USUARIO_MODIFICACION=#{login}, FECHA_MODIFICACION = CURRENT_TIMESTAMP WHERE id_usuario = #{idUsuario} ")
    public void bloquearUsuario(UsuarioDTO usuarioDTO);

    @Select("SELECT COUNT(*) FROM  \"SEG_USUARIO\" us join \"SEG_HISTORICO_CLAVE\" hc on hc.ID_USUARIO = us.ID_USUARIO "
            + "WHERE us.LOGIN = #{login} "
            + "AND hc.CLAVE = #{claveRenovar} "
            + "group by hc.FECHA_CREACION "
            + "ORDER BY hc.FECHA_CREACION DESC "
            + "limit #{numeroHistorico} offset 0  ")
    public Integer existeClaveHistorialUsuario(UsuarioDTO usuarioDTO);

    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update("UPDATE  \"SEG_USUARIO\" SET CLAVE = #{claveRenovar}, "
            + "USUARIO_MODIFICACION=#{login}, FECHA_MODIFICACION = CURRENT_TIMESTAMP, ULT_ACT_CLAVE = CURRENT_TIMESTAMP "
            + "WHERE LOGIN = #{login} ")
    public void actualizarUsuario(UsuarioDTO usuarioDTO);

    @Insert("INSERT INTO  \"SEG_HISTORICO_CLAVE\"	"
            + "(CLAVE, ID_USUARIO, FECHA_CREACION, USUARIO_CREACION) "
            + "VALUES (#{clave}, #{usuario},CURRENT_TIMESTAMP, #{usuarioCreacion})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    public void insertarHistorialUsuario(HistoricoClaveDTO historicoClaveDTO);

    @Select("select  login FROM \"SEG_USUARIO\" U WHERE "
            + " login = #{login} and clave = #{pass}")
    public String validarUsuarioYPass(String login, String pass);

    @Select("select  id_usuario FROM \"SEG_USUARIO\" U WHERE "
            + " login = #{login}")
    public Long consultarIdUsuarioPorLogin(String login);

    @Select("select sp.correo correo " +
            "from \"SEG_USUARIO\" su " +
            "inner join \"SEG_PERSONA\" sp on su.id_persona = sp.id_persona " +
            "where su.id_usuario=#{idUsuario} ")
    public String consultaCorreoUsuarioPorIdUsuario(DatosBasicosUsuarioDTO usuario);

    @Select("SELECT COUNT(*)  FROM \"SEG_USUARIO\" U WHERE U.LOGIN = #{login} ")
    public Long validarExisteLogin(UsuarioDTO usuario);
    
    @CacheEvict(cacheNames = "consultaDatosBasicosUsuarioPorUsuarioClave", allEntries = true)
    @Update("UPDATE  \"SEG_USUARIO\" SET ESTADO = #{estado}, "
            + "USUARIO_MODIFICACION=#{login}, FECHA_MODIFICACION = CURRENT_TIMESTAMP, ULT_ACT_CLAVE = CURRENT_TIMESTAMP "
            + "WHERE ID_USUARIO = #{idUsuario}")
    public void actualizarUsuarioEstado(UsuarioDTO usuarioDTO);
}