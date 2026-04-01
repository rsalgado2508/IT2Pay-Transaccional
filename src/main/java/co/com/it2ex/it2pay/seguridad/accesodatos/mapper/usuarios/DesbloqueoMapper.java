package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios;

import java.util.List;

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.DesbloqueoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface DesbloqueoMapper {

	@Select("select tip.tipo_intento from \"SEG_TIPO_BLOQUEO\" tip, \"SEG_BLOQUEO\" bl "
			+ " where tip.id_tipo_bloqueo = bl.id_tipo_bloqueo "
			+ " and bl.id_bloqueo=#{idBloqueo}")
	public String consultarTipoBloqueoXId(Long idBloqueo);

	/**
	 * Consulta un registro de bloqueo especifico
	 * @param idBloqueo
	 */
	@Select(" select bloq.estado estadoBloqueo, "
			+ " tipoB.id_parametro idParametro, "
			+ " bloq.fecha_modificacion fechaPreparador, "
			+ " bloq.usuario_modificacion usuarioModificacion, "
			+ " bloq.fecha_liberacion fechaLiberacion, "
			+ "  TO_CHAR(bloq.fecha, 'dd-MM-yyyy HH24:MI:SS') fechaBloqueo, "
			+ " bloq.login_usua_liberador_bloqueo usuarioLiberacion, "
			+ " bloq.id_usuario idUsuario ,"
			+ " bloq.id_persona idPersona,"
			+ " tipoB.bloqueo_Destino bloqueoDestino, "
			+ " bloq.id_bloqueo idBloqueo "
			+ " from \"SEG_BLOQUEO\" bloq, \"SEG_TIPO_BLOQUEO\" tipoB"
			+ " where bloq.id_bloqueo = #{idBloqueo}"
			+ " and bloq.id_tipo_bloqueo = tipoB.id_tipo_bloqueo") 
	public DesbloqueoDTO consultarBloqueoXId(Long idBloqueo);

	/**
	 * Utilizado para obtener informacion de un bloqueo 
	 * especificio de un usuario ya registrando
	 * segun un id de funcionalidad y de usuario
	 * @param funcionalidad
	 * @param idUsuario
	 * @return
	 */
	@Select ("select  tbl.id_parametro idParametro, "
			+ " tbl.id_tipo_bloqueo idTipoBloqueo, "
			+ " tbl.bloqueo_destino bloqueoDestino,"
			+ " bl.id_usuario idUsuario, "
			+ " bl.id_persona idPersona,"
			+ " bl.id_bloqueo idBloqueo "
			+ " from "
			+ " \"SEG_TIPO_BLOQUEO\" tbl, \"SEG_BLOQUEO\" bl "
			+ " where "
			+ " bl.id_tipo_bloqueo = tbl.id_tipo_bloqueo "
			+ " and bl.id_usuario = #{idUsuario} "
			+ " and bl.estado  <> 'I' "
			+ " and tbl.id_funcionalidad = #{funcionalidad}" )
	public List<DesbloqueoDTO> consultarTipoBloqueoXFuncionalidadUsuario(@Param("funcionalidad") Long funcionalidad, @Param("idUsuario")Long idUsuario);

	/**
	 *   Utilizado para obtener informacion de un bloqueo de una persona
	 *   que se esta registrando segun un id de funcionalidad y de persona
	 * @param funcionalidad
	 * @param persona
	 * @return
	 */
	@Select ("select  id_parametro idParametro, "
			+ " tbl.id_tipo_bloqueo idTipoBloqueo, "
			+ " tbl.bloqueo_destino bloqueoDestino,"
			+ " bl.id_usuario idUsuario, "
			+ " bl.id_persona idPersona, "
			+ " bl.id_bloqueo idBloqueo "
			+ " from "
			+ " \"SEG_TIPO_BLOQUEO\" tbl, \"SEG_BLOQUEO\" bl "
			+ " where "
			+ " bl.id_tipo_bloqueo = tbl.id_tipo_bloqueo "
			+ " and bl.id_persona = #{persona} "
			+ " and bl.estado  <> 'I' "
			+ " and tbl.id_funcionalidad = #{funcionalidad}" )
	public List<DesbloqueoDTO> consultarTipoBloqueoXFuncionalidadPersona(@Param("funcionalidad")Long funcionalidad, @Param("persona")Long persona);

	/**
	 * Utilizado para actualizar sin doble intervención
	 * el desbloqueo de usuario
	 * @param desbloqueo
	 */
	@Update(" UPDATE \"SEG_BLOQUEO\" SET ESTADO = #{estadoBloqueo}, "
			+ " USUARIO_MODIFICACION = #{usuarioModificacion}, "
			+ " FECHA_MODIFICACION = current_timestamp, "
			+ " LOGIN_USUA_LIBERADOR_BLOQUEO = #{usuarioModificacion}, "
			+ " FECHA_LIBERACION = current_timestamp "
			+ "WHERE ID_BLOQUEO = #{idBloqueo}")
	public void actualizarDesbloqueoSinDI(DesbloqueoDTO desbloqueo);
}

