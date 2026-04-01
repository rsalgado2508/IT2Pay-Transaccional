package co.com.it2ex.it2pay.util.otros.components;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 19/5/2023
 */

import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.EstadoDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroConsultaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.parametros.ServicioParametros;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.enums.TipoValorEnum;
import co.com.it2ex.it2pay.util.otros.constantes.expresiones.ConstantesExpresionesRegulares;
import co.com.it2ex.it2pay.util.otros.constantes.parametros.ConstantesParametros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContrasenaUtil {
	
	@Autowired
	ServicioParametros serviciosParametros;
	
	@Autowired
	private ServiciosComun serviciosComun;

	public EstadoDTO validarContrasenaParametros(UsuarioDTO dto) throws Exception{
		
		EstadoDTO validarUsuario = new EstadoDTO();
		
		String expRegular = "";
		ParametroConsultaDTO parametroAlfanumerico = serviciosParametros.obtenerValorParametro(ConstantesParametros.COMBINACION_ALFANUMERICO) != null
				? serviciosParametros.obtenerValorParametro(ConstantesParametros.COMBINACION_ALFANUMERICO) : new ParametroConsultaDTO();

				ParametroConsultaDTO parametroMayusculas = serviciosParametros.obtenerValorParametro(ConstantesParametros.CARACTERES_MAYUSCULAS) != null
						? serviciosParametros.obtenerValorParametro(ConstantesParametros.CARACTERES_MAYUSCULAS) : new ParametroConsultaDTO();

						ParametroConsultaDTO parametroMinusculas = serviciosParametros.obtenerValorParametro(ConstantesParametros.CARACTERES_MINUSCULAS) != null
								? serviciosParametros.obtenerValorParametro(ConstantesParametros.CARACTERES_MINUSCULAS) : new ParametroConsultaDTO();

								ParametroConsultaDTO parametroMayusMinus = serviciosParametros.obtenerValorParametro(ConstantesParametros.COMBINACION_MAYUSCULAS_MINUSCULAS) != null
										? serviciosParametros.obtenerValorParametro(ConstantesParametros.COMBINACION_MAYUSCULAS_MINUSCULAS) : new ParametroConsultaDTO();

										ParametroConsultaDTO parametroNumeros = serviciosParametros.obtenerValorParametro(ConstantesParametros.COMBINACION_NUMEROS) != null
												? serviciosParametros.obtenerValorParametro(ConstantesParametros.COMBINACION_NUMEROS) : new ParametroConsultaDTO();

												ParametroConsultaDTO caracteresRepetidos = serviciosParametros.obtenerValorParametro(ConstantesParametros.PERMITE_CARACTERES_REPETIDOS) != null
														? serviciosParametros.obtenerValorParametro(ConstantesParametros.PERMITE_CARACTERES_REPETIDOS) : new ParametroConsultaDTO();
														
														ParametroConsultaDTO secuenciaCaracteres = serviciosParametros.obtenerValorParametro(ConstantesParametros.PERMITE_SECUENCIA_CARACTERES) != null
																? serviciosParametros.obtenerValorParametro(ConstantesParametros.PERMITE_SECUENCIA_CARACTERES) : new ParametroConsultaDTO();
																
																ParametroConsultaDTO caracteresEspeciales = serviciosParametros.obtenerValorParametro(ConstantesParametros.PERMITE_CARACTERES_ESPECIALES) != null
																		? serviciosParametros.obtenerValorParametro(ConstantesParametros.PERMITE_CARACTERES_ESPECIALES) : new ParametroConsultaDTO();

														if (caracteresRepetidos.getValor().equals(TipoValorEnum.NO.name())){
	
																if(!Validador.validarCamposCaracteresRepetidos(dto.getClave())){
																	validarUsuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_CARACTERES_REPETIDOS);
																	validarUsuario.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(validarUsuario.getCodigoRespuesta()).getMensaje());
																	validarUsuario.setEstado(false);
																	return validarUsuario;
																}
														}
													
														 
														if(secuenciaCaracteres.getValor().equals(TipoValorEnum.NO.name())){
															expRegular = ConstantesExpresionesRegulares.SECUENCIA_CARACTERES;
															if(expRegular!=null){
																if(Validador.validarCampoExpresionRegular(dto.getClave(),expRegular )){
																	validarUsuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_CLAVE_NO_SECUENCIAS);
																	validarUsuario.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(validarUsuario.getCodigoRespuesta()).getMensaje());
																	validarUsuario.setEstado(false);
																	return validarUsuario;
																}
															}
														}
														if(caracteresEspeciales.getValor().equals(TipoValorEnum.SI.name())){
															expRegular = ConstantesExpresionesRegulares.CARACTERES_ESPECIALES;
															if(expRegular!=null){
																if(!Validador.validarCampoExpresionRegular(dto.getClave(),expRegular )){
																	validarUsuario.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_VALIDA_CARAC_ESPECIALES);
																	validarUsuario.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(validarUsuario.getCodigoRespuesta()).getMensaje());
																	validarUsuario.setEstado(false);
																	return validarUsuario;
																}
															}
														}
   													
														
														if(parametroAlfanumerico.getValor() != null){
															if (parametroAlfanumerico.getValor().equals(TipoValorEnum.SI.name())) {
																expRegular = ConstantesExpresionesRegulares.ALFANUMERICO;
																if(expRegular == null){
																	expRegular = ".*[A-Za-z]+[0-9]+.*";
																}
																validarUsuario = validarExpresion(validarUsuario, expRegular, dto, ConstantesCodigosError.CODIGO_ALFANUMERICO);
																if(!validarUsuario.isEstado())return validarUsuario;
															}
														}

														if(parametroMayusMinus.getValor() != null){
															if (parametroMayusculas.getValor().equals(TipoValorEnum.SI.name()))
															{
																expRegular = ConstantesExpresionesRegulares.MAYUSCULAS_MINUSCULAS;
																if(expRegular == null){
																	expRegular = ".*[A-Za-z].*";
																}
																validarUsuario = validarExpresion(validarUsuario, expRegular, dto, ConstantesCodigosError.CODIGO_CARACTERES_MINUS_MAYUS);
																if(!validarUsuario.isEstado())return validarUsuario;

															}
														}
														if(parametroMayusculas.getValor() != null){
															if (parametroMayusculas.getValor().equals(TipoValorEnum.SI.name())) {
																expRegular = ConstantesExpresionesRegulares.MAYUSCULAS;
																if(expRegular == null){
																	expRegular = "/[A-Z]+/";
																}
																validarUsuario = validarExpresion(validarUsuario, expRegular, dto, ConstantesCodigosError.CODIGO_CARACTERES_MAYUSCULAS);
																if(!validarUsuario.isEstado())return validarUsuario;
															} 
														}
														if(parametroMinusculas.getValor() != null){
															if (parametroMinusculas.getValor().equals(TipoValorEnum.SI.name())) {
																expRegular = ConstantesExpresionesRegulares.MINUSCULAS;
																if(expRegular == null){
																	expRegular = "/[a-z]+/";
																}
																validarUsuario = validarExpresion(validarUsuario, expRegular, dto, ConstantesCodigosError.CODIGO_CARACTERES_MINUSCULAS);
																if(!validarUsuario.isEstado())return validarUsuario;
															}
														}
														if(parametroNumeros.getValor() != null){
															if (parametroNumeros.getValor().equals(TipoValorEnum.SI.name())) {
																expRegular = ConstantesExpresionesRegulares.COMBINACION_NUMERICO;
																if(expRegular == null){
																	expRegular = ".*[0-9]+.*";
																}
																validarUsuario = validarExpresion(validarUsuario, expRegular, dto, ConstantesCodigosError.CODIGO_COMBINACION_NUMERICO);
																if(!validarUsuario.isEstado())return validarUsuario;
															}	
														}
														validarUsuario.setEstado(true);
														return  validarUsuario;
	}

	public EstadoDTO validarExpresion(EstadoDTO validarUsuarioRpta, String expRegular, UsuarioDTO dto, String codigoError) throws Exception{
		if (!Validador.validarCampoExpresionRegular(dto.getClave(), expRegular)) {
			validarUsuarioRpta.setCodigoRespuesta(codigoError);
			validarUsuarioRpta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(validarUsuarioRpta.getCodigoRespuesta()).getMensaje());
			validarUsuarioRpta.setEstado(false);
		}else{
			validarUsuarioRpta.setEstado(true);
		}
		return validarUsuarioRpta;
	}
	
	
}
