package co.com.it2ex.it2pay.util.otros.constantes.enums;

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

/**
 * Enum utilizado para parametros estandar de la aplicacion
 * ej: encoding
 * @author itc
 *
 */
public enum ComponenteFechaEnum {
	MINUTOS("m"),SEGUNDOS("s"), HORAS("h"), DIAS("d");

	private String codigo;	

	ComponenteFechaEnum(String componenteFecha){
		this.codigo=componenteFecha;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


}
