package co.com.it2ex.it2pay.util.otros.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Validador se encarga de realizar la implementación de los métodos necesarios 
 * para las validaciones de los campos 
 * 
 * @author nromero@itc.com.co
 * @authorUltimaModificacion nromero@itc.com.co
 * @fechaCreacion 12/07/2016
 * @fechaModificacion 12/07/2016
 * @version 1.0.0
 * 
 * ************************************ Versiones ************************************ 
 * 
 * 1.0.0: 12/07/2016 - Construcción de la clase Validador
 */
public class Validador {

	/**
	 * Constructor privado de la clase Validador
	 */
	private Validador(){
	}

	/**
	 * Método que se encarga de realizar la validación de un campo obligatorio.
	 *
	 * @param obj Objeto que contiene la información del campo
	 * @return el valor de true se obtiene cuando cumple con la validación en caso contrario retorna el valor de false
	 */
	public static boolean validarCampoObligatorio(Object obj){
//		if(logger.isDebugEnabled() || logger.isTraceEnabled()){
//			logger.debug(CaracteresUtil.neutralizeMessage("Validar Campo Obligatorio");
//		}
		if(obj == null){
			return false;
		}
		if(obj instanceof String){
			String str = (String)obj;
			if(str.trim().isEmpty()){
				return false;
			}
		}
		return true;
	}

	/**
	 * Método que se encarga de realizar la validación de un campo por el tamaño máximo.
	 *
	 * @param obj Objeto que contiene la información del campo
	 * @param tamanno es el tamaño máximo del campo
	 * @return el valor de true se obtiene cuando cumple con la validación en caso contrario retorna el valor de false
	 */
	public static boolean validarCampoTamannoMaximo(Object obj, Long tamanno){
//		if(logger.isDebugEnabled() || logger.isTraceEnabled()){
//			logger.debug(CaracteresUtil.neutralizeMessage("Validar longitud " + tamanno);
//		}
		if(obj == null){
			return false;
		}
		if(obj instanceof String){
			String str = (String)obj;
			str = str.trim();
			if(str.length() > tamanno){
				return false;
			}
		} else {
			String str = obj.toString();
			if(str.length() > tamanno){
				return false;
			}
		}
		return true;
	}

	/**
	 * Método que se encarga de realizar la validación de un campo por el tamaño mímino.
	 *
	 * @param obj Objeto que contiene la información del campo
	 * @param tamanno es el tamaño mínimo del campo
	 * @return el valor de true se obtiene cuando cumple con la validación en caso contrario retorna el valor de false
	 */
	public static boolean validarCampoTamannoMimino(Object obj, Long tamanno){
//		if(logger.isDebugEnabled() || logger.isTraceEnabled()){
//			logger.debug(CaracteresUtil.neutralizeMessage("Validar longitud " + tamanno);
//		}
		if(obj == null){
			return false;
		}
		if(obj instanceof String){
			String str = (String)obj;
			str = str.trim();
			if(str.length() < tamanno){
				return false;
			}
		} else {
			String str = obj.toString();
			if(str.length() < tamanno){
				return false;
			}
		}
		return true;
	}

	/**
	 * Método que se encarga de realizar la validación de un campo por la expresion regular enviada.
	 *
	 * @param obj Objeto que contiene la información del campo
	 * @param expresion es la expresión regular para realizar la validación
	 * @return el valor de true se obtiene cuando cumple con la validación en caso contrario retorna el valor de false
	 */
	public static boolean validarCampoExpresionRegular(Object obj, String expresion){

		/*
		 * ini CamiloBlanco - PCRF-128
		 * */
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		try {
			expresion = expresion.replaceAll("&quot;", "\"");
			str = str.replaceAll("&quot;", "\"");
		

		
			Pattern pat = Pattern.compile(expresion); 
			Matcher mat = pat.matcher(str); 
			if(!mat.matches()) { 
				return false; 
			}
			return true;
		}catch(Exception e) {
			return false;
		}
		 
		 /*
		  * fin CamiloBlanco - PCRF-128
		* */
	}
	
	/**
	 * Metodo que se encarga de validar caracteres repetidos
	 * 
	 * @param palabra
	 * @return el valor de true se obtiene cuando cumple con la validación en caso contrario retorna el valor de false
	 */
	public static boolean validarCamposCaracteresRepetidos(String palabra){
		for (int i = 0; i < palabra.length(); i++) {
			int ind1 = palabra.indexOf(palabra.charAt(i));
			int ind2 = palabra.lastIndexOf(palabra.charAt(i));
			if (ind1 != ind2) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validarFechaActual(String fecha){
	//fecha = "08/08/2016 01:01:11";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 00);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		
	
		try {
			Date nuevaFecha = format.parse(fecha);
			if(nuevaFecha.before(calendar.getTime())){
				return false;
			}
			
		} catch (Exception e) {

		}
		return true;
	}	
	
	public static boolean validarLetrasYNumeros(String cadena){
		boolean algunDigito = false;
		boolean algunaLetra = false;
		if (cadena.contains(" "))
			return false;
		for (int i = 0; i < cadena.length(); i++) {
			if (Character.isDigit(cadena.charAt(i))) {
				// es un digito
				algunDigito = true;
			} else {
				algunaLetra = true;
				// no es un digito
			}
		}
		if (algunDigito && algunaLetra) {
			return true;
		}
		return false;
	}
	
	public static boolean validarSiTieneNumeros(String cadena){
		boolean algunDigito = false;
		if (cadena.contains(" "))
			return false;
		for (int i = 0; i < cadena.length(); i++) {
			if (Character.isDigit(cadena.charAt(i))) {
				// es un digito
				algunDigito = true;
			} 
		}
		if (algunDigito) {
			return true;
		}
		return false;
	}
	
//	public static void main (String [] args){
//		if(Validador.validarLetrasYNumeros("sadsad213")){
//			
//		}
//	}
}
