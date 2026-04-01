package co.com.it2ex.it2pay.util.accesodatos.mapper.intento;

import co.com.it2ex.it2pay.util.modelo.generico.InfoBasicaUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.intento.IntentoUsuarioDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IntentoUsuarioMapper {

	@Select(" select NRO_INTENTOS from \"SEG_USUARIO_INTENTO\" where ID_USUARIO = "
			+ " (select ID_USUARIO from \"SEG_USUARIO\" where LOGIN = #{usuario} ) " + " AND TIPO_INTENTO = #{tipoIntento} ")
	public Integer consultarIntentoUsuario(IntentoUsuarioDTO intentoUsuarioDTO);

	@Insert("insert into \"SEG_USUARIO_INTENTO\" (ID_USUARIO,TIPO_INTENTO,NRO_INTENTOS,FECHA_CREACION,USUARIO_CREACION) values ( "
			+ "(select ID_USUARIO from \"SEG_USUARIO\" where LOGIN = #{usuario} ),#{tipoIntento},1,CURRENT_TIMESTAMP,#{usuario}) ")
	public void insertarIntentoUsuario(IntentoUsuarioDTO intentoUsuarioDTO);
	
	@Update(" update \"SEG_USUARIO_INTENTO\" set NRO_INTENTOS = "
			+ "    (select NRO_INTENTOS+1 from \"SEG_USUARIO_INTENTO\" where TIPO_INTENTO = #{tipoIntento} "
			+ " AND ID_USUARIO = (select ID_USUARIO from \"SEG_USUARIO\" where LOGIN = #{usuario} )), "
			+ " USUARIO_MODIFICACION = #{usuario}, FECHA_MODIFICACION = CURRENT_TIMESTAMP "
			+ " where ID_USUARIO = (select ID_USUARIO from \"SEG_USUARIO\" where LOGIN = #{usuario} ) "
			+ " and TIPO_INTENTO = #{tipoIntento} ")
	public void modificarIntentoUsuario(IntentoUsuarioDTO intentoUsuarioDTO);

	@Delete(" delete from \"SEG_USUARIO_INTENTO\" where ID_USUARIO =(select ID_USUARIO from \"SEG_USUARIO\" where LOGIN = #{usuario}  )"
			+ " and TIPO_INTENTO = #{tipoIntento}")
	public void eliminarIntento(IntentoUsuarioDTO intentoUsuarioDTO);

	@Delete(" delete from \"SEG_USUARIO_INTENTO\" where ID_USUARIO = #{idUsuario}"
			+ " and TIPO_INTENTO = #{tipoIntento}")
	public void eliminarIntentoXID(IntentoUsuarioDTO intentoUsuarioDTO);
	
	@Select(" select NRO_INTENTOS from \"SEG_USUARIO_INTENTO\" where ID_USUARIO = "
			+ " (select U.ID_USUARIO from \"SEG_USUARIO\" U, \"SEG_PERSONA\" P where P.ID = U.ID_PERSONA AND P.TIPO_DOCUMENTO = #{tipoDocumento} AND P.NUMERO_DOCUMENTO = #{numeroDocumento} and u.login = #{login} ) "
			+ " AND TIPO_INTENTO = #{intento.tipoIntento} ")
	public Integer consultarIntentoUsuarioporDocumento(@Param("tipoDocumento") String tipoDocumento, @Param("numeroDocumento") String numeroDocumento, @Param("intento") IntentoUsuarioDTO intento, @Param("login") String login);

	@Insert("insert into \"SEG_USUARIO_INTENTO\" (ID_USUARIO,TIPO_INTENTO,NRO_INTENTOS,FECHA_CREACION,USUARIO_CREACION) values ( "
			+ "(select U.ID_USUARIO from \"SEG_USUARIO\" U, \"SEG_PERSONA\" P where P.ID = U.ID_PERSONA AND P.TIPO_DOCUMENTO = #{usuario.tipoDocumento} AND P.NUMERO_DOCUMENTO = #{usuario.numeroDocumento}),#{intento.tipoIntento},1,CURRENT_TIMESTAMP,#{intento.usuario}) ")
	public void insertarIntentoUsuarioporDocumento(@Param("usuario") InfoBasicaUsuarioDTO usuario, @Param("intento") IntentoUsuarioDTO intento);

	@Update(" update \"SEG_USUARIO_INTENTO\" set NRO_INTENTOS = (select NRO_INTENTOS+1 from SEG_USUARIO_INTENTO where TIPO_INTENTO = #{intento.tipoIntento} AND ID_USUARIO = (select U.ID_USUARIO from SEG_USUARIO U, SEG_PERSONA P where P.ID = U.ID_PERSONA AND P.TIPO_DOCUMENTO = #{usuario.tipoDocumento} AND P.NUMERO_DOCUMENTO = #{usuario.numeroDocumento})), "
			+ " USUARIO_MODIFICACION = #{intento.usuario}, FECHA_MODIFICACION = CURRENT_TIMESTAMP "
			+ " where ID_USUARIO = (select U.ID_USUARIO from \"SEG_USUARIO\" U, \"SEG_PERSONA\" P where P.ID = U.ID_PERSONA AND P.TIPO_DOCUMENTO = #{usuario.tipoDocumento} AND P.NUMERO_DOCUMENTO = #{usuario.numeroDocumento}) "
			+ " and TIPO_INTENTO = #{intento.tipoIntento} ")
	public void modificarIntentoUsuarioporDocumento(@Param("usuario") InfoBasicaUsuarioDTO usuario, @Param("intento") IntentoUsuarioDTO intento);

  @Delete(" delete \"SEG_USUARIO_INTENTO\" where ID_USUARIO =(select U.ID_USUARIO from \"SEG_USUARIO\" U, \"SEG_PERSONA\" P where P.ID = U.ID_PERSONA AND  P.TIPO_DOCUMENTO = #{usuario.tipoDocumento} AND P.NUMERO_DOCUMENTO = #{usuario.numeroDocumento} and U.LOGIN = #{usuario.usuario} )"
			+ " and TIPO_INTENTO = #{intento.tipoIntento}")
	public void eliminarIntentoValidacionOTP(@Param("usuario") InfoBasicaUsuarioDTO usuario, @Param("intento") IntentoUsuarioDTO intento);
  
}
