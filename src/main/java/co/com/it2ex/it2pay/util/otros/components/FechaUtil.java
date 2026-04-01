package co.com.it2ex.it2pay.util.otros.components;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 20/6/2023
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.enums.ComponenteFechaEnum;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase que realiza oepraciones relacionadas con
 * fechas es estandar.
 * @author itc
 *
 */
public class FechaUtil {

	@Autowired
	LoggerAuditoriasComponent loggerAuditoriasComponent;


	private static FechaUtil formatoFecha;

	private FechaUtil() {
	}

	public static FechaUtil getInstance() {
		if (formatoFecha == null) {
			formatoFecha = new FechaUtil();
		}
		return formatoFecha;
	}

	public String getFechaFormato(Date fecha_date, String fecha_string ){
		Locale locale = new Locale("es");
		SimpleDateFormat format_fecha = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss",locale);
		String fecha = null;	
		try {
			fecha_string = fecha_string.split(" ")[0];//en caso que la fecha tenga consigo una hora especifica esta se obviara.

			if(fecha_date!=null){
				fecha = format_fecha.format(fecha_date);			
				fecha = fecha.split("/")[0] + "/" + 
						(fecha.split("/")[1].substring(0,1)).toUpperCase() + fecha.split("/")[1].substring(1, 3)  + "/" +
						fecha.split("/")[2];
			}else if( StringUtils.isNotEmpty(fecha_string) ){

				int dia=0;
				int mes=0;
				int anio=0;
				if(fecha_string.indexOf("-")>0){ // Formato fecha yyyy-mm-dd
					dia = Integer.parseInt(fecha_string.split("-")[2]);
					mes = Integer.parseInt(fecha_string.split("-")[1]);
					anio = Integer.parseInt(fecha_string.split("-")[0]);
				}else if(fecha_string.indexOf("/")>0){ // Formato fecha dd/mm/yyyy
					dia = Integer.parseInt(fecha_string.split("/")[0]);
					mes = Integer.parseInt(fecha_string.split("/")[1]);
					anio = Integer.parseInt(fecha_string.split("/")[2]);
				}

				SimpleDateFormat format_fechaL;
				if(String.valueOf(anio).length()==2){
					format_fechaL = new SimpleDateFormat("dd/MMM/yy",locale);
				}else{
					format_fechaL = new SimpleDateFormat("dd/MMM/yyyy",locale);
				}

				fecha = null;
				try {
					Calendar calendar_fechaMov = Calendar.getInstance();
					calendar_fechaMov.set(Calendar.YEAR, anio);
					/*
					 * Las constantes de los meses en Calendar comienzan en 0 = Enero
					 */
					calendar_fechaMov.set(Calendar.MONTH, mes-1);
					calendar_fechaMov.set(Calendar.DAY_OF_MONTH, dia);

					Date fechaMv = calendar_fechaMov.getTime();
					fecha = format_fechaL.format(fechaMv).toLowerCase();
					fecha = fecha.split("/")[0] + "/" + 
							(fecha.split("/")[1].substring(0,1)).toUpperCase() + fecha.split("/")[1].substring(1, 3)  + "/" +
							fecha.split("/")[2];

				} catch ( Exception e) {
					fecha = fecha_string;
					loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage("Error format fecha: " + fecha_string), this.getClass(), e);
				}
			}
			else {
							}
		} catch ( Exception e) {
			if(fecha_date!=null){
				fecha = format_fecha.format(fecha_date);
				loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage("Error format fecha: " + fecha_date), this.getClass(), e);
			}else if(fecha_string!=null){
				fecha = format_fecha.format(fecha_string);
				loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage("Error format fecha: " + fecha_string), this.getClass(), e);
			}
		}
		return fecha;
	}


	/**
	 * Calcula el tiempo que ha pasado entre una fecha inicial y el sysdate 
	 * @param fechaInicio  Recibe la fecha inicial con la que se calculara el tiempo de diferencia (dd-MM-yyyy HH:mm:ss)
	 * @param tipoRetorno  Recibe el tipo de dato que quiere que se retorne dias, hora, min, seg.   
	 * @return diferencia en minutos
	 */
	public long obtenerMinutosTranscurridos (String fechaInicio, String tipoRetorno, String formatoFecha, String fechaFinal){

		Date myDate = new Date();
		String vfinal="";
		Date dinicio = null;
		Date dfinal = null;
		long milis1;
		long milis2;
		long diff;

		SimpleDateFormat sdf= new SimpleDateFormat(formatoFecha);

		try {
			// PARSEO STRING A DATE
			
			if (fechaFinal != null){
				vfinal = fechaFinal;
			}else{
				vfinal  = new SimpleDateFormat(formatoFecha).format(myDate);
			}
			
			
			dinicio = sdf.parse(fechaInicio);    
			
			dfinal = sdf.parse(vfinal);  
			

		} catch (Exception e) {
			loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage("Se ha producido un error en el parseo para calcular la diferencia entre tiempos para desbloqueo automatico"), this.getClass(), e);
		}
		
		

		//INSTANCIA DEL CALENDARIO GREGORIANO
		Calendar cinicio = Calendar.getInstance();
		Calendar cfinal = Calendar.getInstance();

		//ESTABLECEMOS LA FECHA DEL CALENDARIO CON EL DATE GENERADO ANTERIORMENTE
		cinicio.setTime(dinicio);
		cfinal.setTime(dfinal);
       
		
		milis1 = cinicio.getTimeInMillis();

		milis2 = cfinal.getTimeInMillis();
		diff = milis2-milis1;
		
		long diferencia= -1;

		if(tipoRetorno.equalsIgnoreCase(ComponenteFechaEnum.SEGUNDOS.getCodigo())){
			// calcular la diferencia en segundos
			diferencia =  Math.abs (diff / 1000);

		}
		else if(tipoRetorno.equalsIgnoreCase(ComponenteFechaEnum.MINUTOS.getCodigo())){
			// calcular la diferencia en minutos
			diferencia =  Math.abs (diff / (60 * 1000));


		}
		else if(tipoRetorno.equalsIgnoreCase(ComponenteFechaEnum.HORAS.getCodigo())){
			// calcular la diferencia en horas
			diferencia =   (diff / (60 * 60 * 1000));


		}
		else if(tipoRetorno.equalsIgnoreCase(ComponenteFechaEnum.DIAS.getCodigo())){
			// calcular la diferencia en dias
			diferencia = Math.abs ( diff / (24 * 60 * 60 * 1000) );

		}

		
		return diferencia;
	}
	
	public Date convertirStringADate(String strfecha){
		 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MMM-yyyy");

		Date fecha = null;
		try {

		fecha = formatoDelTexto.parse(strfecha);

		} catch (ParseException ex) {

		

		}
		return fecha;
	}
	
	
	public boolean validarFechaActual(String fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
	        Date date1 = sdf.parse(fecha);
	        Date date2 = sdf.parse(sdf.format(new Date()));

	        

	        /*if (date1.compareTo(date2) > 0) {
	            
	        } else if (date1.compareTo(date2) < 0) {
	            
	        } else if (date1.compareTo(date2) == 0) {
	            
	        } else {
	            
	        }*/

			return (date1.compareTo(date2) == 0);

		} catch (Exception e) {
			
		}
		return false;
	}
	
	public String formatearFecha(String fecha){
		   SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
	       SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
	       Date fechas = null;
		  try {
		        fechas = formato.parse(fecha);
		        
		} catch (Exception e) {
			  loggerAuditoriasComponent.registrarLoggerError(CaracteresUtil.neutralizeMessage("error formateando fecha retiro "+e.getMessage()), this.getClass(), e);
			
			return "";
		}
      return formatoFinal.format(fechas);
	}
	
	public String formatearFechaCsv(String fecha){
		   SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
	       SimpleDateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
	       Date fechas = null;
		  try {
		        fechas = formato.parse(fecha);
		        
		} catch (Exception e) {
			
		}
   return formatoFinal.format(fechas);
	}
	
	public boolean validarFechaMenorOActual(String fecha){
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date fechaDate = formato.parse(fecha);
			return (fechaDate.before(new Date()) || validarFechaActual(fecha));
		} catch (Exception e) {
			
		}
		return false;

	}
	
	public Long obtenerDiasTranscurridosASysdate(String fechaPosterior){
		Long dias=null;
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		try {
			   final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al d�a 
			   Date hoy = new Date(); //Fecha de hoy
			   Date fecha = formato.parse(fechaPosterior);   
			   Calendar cal = Calendar.getInstance();
			   cal.setTime(fecha);
			   cal.set(Calendar.HOUR, 0);
			   cal.set(Calendar.MINUTE, 0);
			   cal.set(Calendar.SECOND,0);
			   
			   dias = (cal.getTime().getTime()-hoy.getTime()  )/MILLSECS_PER_DAY; 
			   return dias;
		} catch (Exception e) {
			
		}
		return dias;
		
	}
	

}
