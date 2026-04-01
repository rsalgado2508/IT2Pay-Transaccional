package co.com.it2ex.it2pay.util.accesodatos.mapper.intento;

import co.com.it2ex.it2pay.util.modelo.intento.BloqueoDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;


/**
 * Mapper relacionado con a tabla seg_blouqeo
 * la cual es utilizada para controlar los bloqueos
 * de los usuarios o personas en fiduinversion 
 * @author itc
 *
 */
@Mapper
public interface BloqueoMapper {


	@Insert("INSERT INTO \"SEG_BLOQUEO\" "
			+ " (ID_TIPO_BLOQUEO, ID_USUARIO, ESTADO, FECHA, USUARIO_CREACION, FECHA_CREACION) "
			+ " VALUES "
			+ "( #{idTipoBloqueo}, #{idUsuarioBloqueo}, #{estadoBloqueo}, CURRENT_TIMESTAMP , #{loginUsuarioBloqueo}, CURRENT_TIMESTAMP )")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void registrarBloqueoUsuario(BloqueoDTO bloqueo);

	@Insert("INSERT INTO \"SEG_BLOQUEO\" "
			+ " (ID_TIPO_BLOQUEO, ID_PERSONA, ESTADO, FECHA, USUARIO_CREACION, FECHA_CREACION) "
			+ " VALUES "
			+ " ( #{idTipoBloqueo}, #{idPersonaBloqueo}, #{estadoBloqueo}, CURRENT_TIMESTAMP , #{idPersonaBloqueo}, CURRENT_TIMESTAMP )")
	@Options(useGeneratedKeys=true, keyProperty="id")
	public void registrarBloqueoPersona(BloqueoDTO bloqueo);

	@Cacheable("consultarTipoBloqueo")
	@Select(" select id_funcionalidad idFuncionalidad, "
			+ " tipo_intento tipoIntento, "
			+ " bloqueo_destino bloqueoDestino, "
			+ " id_parametro idParametro, "
			+ " id_tipo_bloqueo idTipoBloqueo "
			+ " from \"SEG_TIPO_BLOQUEO\" "
			+ " where  id_funcionalidad =  #{idFuncionalidad} "
			+ " and tipo_intento =  #{tipoIntento}")
	public BloqueoDTO consultarTipoBloqueo(BloqueoDTO bloqueo);
	

	@Select(" select count (1) "
			+ " from \"SEG_BLOQUEO\" bl , \"SEG_TIPO_BLOQUEO\" t "
			+ " where  t.id_funcionalidad =  #{idFuncionalidad} "
			+ " and t.id_tipo_bloqueo = bl.id_tipo_bloqueo "
			+ " and t.tipo_intento =  #{tipoIntento}"
			+ " and bl.id_persona = #{idPersonaBloqueo}"
			+ " and bl.estado  <> 'I'")
	public long consultarTipoBloqueoPersona(BloqueoDTO bloqueo);
	
	@Select(" select count (1) "
			+ " from \"SEG_BLOQUEO\" bl , \"SEG_TIPO_BLOQUEO\" t "
			+ " where  t.id_funcionalidad =  #{idFuncionalidad} "
			+ " and t.id_tipo_bloqueo = bl.id_tipo_bloqueo "
			+ " and t.tipo_intento =  #{tipoIntento}"
			+ " and bl.ID_USUARIO = #{idUsuarioBloqueo}"
			+ " and bl.estado  <> 'I'")
	public long consultarTipoBloqueoUsuario(BloqueoDTO bloqueo);
	
	
	
	@Select(" select count (1) "
			+ " from \"SEG_BLOQUEO\" bl , \"SEG_TIPO_BLOQUEO\" t "
			+ " where  t.id_funcionalidad =  #{idFuncionalidad} "
			+ " and t.id_tipo_bloqueo = bl.id_tipo_bloqueo "
			+ " and bl.ID_USUARIO = #{idUsuarioBloqueo}"
			+ " and t.tipo_intento = #{tipoIntento} "
			+ " and bl.estado <> 'I'")
	public long consultarCantBloqueoUsuarioXFuncionalidad(BloqueoDTO bloqueo);



}

