package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.terminos;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 2/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import co.com.it2ex.it2pay.seguridad.modelo.terminos.TerminosDTO;
import org.apache.ibatis.annotations.*;


@Mapper
public interface TerminosMapper {

	public final String ACTUALIZAR_ACEPTAR_TERMINOS = " UPDATE \"SEG_USUARIO\"  SET usuario_modificacion = #{usuarioModificacion}, fecha_modificacion = current_timestamp, " +
			" acepta_terminos = 'S', fecha_acepta_terminos = current_timestamp, id_terminos_condiciones = #{idTerminos} WHERE  " +
			" id_usuario = #{idUsuario} ";
	
	public final String CONSULTAR_TERMINOS_ACTIVO = " select id_terminos_condiciones idTerminos, terminos from \"SEG_TERMINOS_CONDICIONES\" stc where estado = 'A' ";

	@Update(value = ACTUALIZAR_ACEPTAR_TERMINOS)
	public void actualizarAceptarTerminosYCondiciones(TerminosDTO dto);

	@Select(value = CONSULTAR_TERMINOS_ACTIVO)
	public TerminosDTO consultarTerminosYCondicionesActivo();

}