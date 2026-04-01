package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.otp;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 26/4/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.otp.OTPCodigosAutenticacionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OTPCodigosAutenticacionMapper {

	/** The insertar codigo otp. */
	public final String INSERTAR_CODIGO_OTP = " INSERT INTO \"SEG_OTP\" (fecha_creacion, id_usuario, "
			+ " usuario_creacion, codigo_otp, estado) "
			+ " VALUES (current_timestamp, #{idUsuario}, "
			+ " #{usuarioCreacion}, #{codigoOtp}, #{estadoCodigoOtp}) ";
	
	/** The actualizar estado codigo otp con usuario. */
	public final String ACTUALIZAR_ESTADO_CODIGO_OTP_CON_USUARIO = " UPDATE \"SEG_OTP\" SET usuario_modificacion = #{usuarioModificacion}, fecha_modificacion = current_timestamp, estado = #{estadoCodigoOtp} "
			+ " WHERE id_usuario = #{idUsuario} ";
	
	/** The actualizar codigo otp con usuario. */
	public final String ACTUALIZAR_CODIGO_OTP_CON_USUARIO = " UPDATE \"SEG_OTP\" SET usuario_modificacion = #{usuarioModificacion}, fecha_modificacion = current_timestamp, "
			+ " estado = #{estadoCodigoOtp}, codigo_otp = #{codigoOtp} WHERE  "
			+ " id_usuario = #{idUsuario} ";
	
	/** The consultar codigo otp con usuario. */
	public final String CONSULTAR_CODIGO_OTP_CON_USUARIO = " SELECT o.id_otp AS idRegistroOtp, o.fecha_creacion AS fechaCreacion, o.fecha_modificacion AS fechaModificacion, "
			+ " o.id_usuario AS idUsuario, o.codigo_otp AS codigoOtp, "
			+ " o.estado AS estadoCodigoOtp FROM \"SEG_OTP\" o WHERE "
			+ " o.id_usuario = #{idUsuario} ";

	/**
	 * Insertar nuevo codigo otp.
	 *
	 * @param otpCodigosAutenticacionDTO the otp codigos autenticacion DTO
	 */
	@Insert(INSERTAR_CODIGO_OTP)
	public void insertarNuevoCodigoOtp(OTPCodigosAutenticacionDTO otpCodigosAutenticacionDTO);
	
	/**
	 * Actualizar estado codigo otp.
	 *
	 * @param otpCodigosAutenticacionDTO the otp codigos autenticacion DTO
	 */
	@Update(value = ACTUALIZAR_ESTADO_CODIGO_OTP_CON_USUARIO)
	public void actualizarEstadoCodigoOtp(OTPCodigosAutenticacionDTO otpCodigosAutenticacionDTO);
	
	/**
	 * Actualizar codigo otp.
	 *
	 * @param otpCodigosAutenticacionDTO the otp codigos autenticacion DTO
	 */
	@Update(value = ACTUALIZAR_CODIGO_OTP_CON_USUARIO)
	public void actualizarCodigoOtp(OTPCodigosAutenticacionDTO otpCodigosAutenticacionDTO);
	
	/**
	 * Consultar codigo otp por usuario.
	 *
	 * @param otpCodigosAutenticacionDTO the otp codigos autenticacion DTO
	 * @return the OTP codigos autenticacion DTO
	 */
	@Select(value = CONSULTAR_CODIGO_OTP_CON_USUARIO)
	public OTPCodigosAutenticacionDTO consultarCodigoOtpPorUsuario(OTPCodigosAutenticacionDTO otpCodigosAutenticacionDTO);
	

}