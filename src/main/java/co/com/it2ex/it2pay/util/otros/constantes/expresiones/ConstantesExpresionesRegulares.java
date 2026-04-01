package co.com.it2ex.it2pay.util.otros.constantes.expresiones;

public class ConstantesExpresionesRegulares {

	public static final String SECUENCIA_CARACTERES = ".*(12|23|34|45|56|67|78|89|01|AB|BC|CD|DE|EF|FG|GH|HI|IJ|JK|KL|LM|MN|NO|OP|PQ|QR|RS|ST|TU|UV|VW|WX|XY|YZ|ab|bc|cd|de|ef|fg|gh|hi|jk|kl|lm|mn|no|op|pq|qr|rs|st|tu|uw|wx|xy|yz).*";
	public static final String CARACTERES_ESPECIALES = ".*[!#$%&/()=?�*:;,.{}�,;+@^�'_-].*";
	public static final String MAYUSCULAS = ".*[A-Z]+.*";
	public static final String MINUSCULAS = ".*[a-z]+.*";
	public static final String MAYUSCULAS_MINUSCULAS = ".*[A-Za-z].*";
	public static final String ALFANUMERICO = ".*[A-Za-z]+[0-9]+.*";
	public static final String COMBINACION_NUMERICO = ".*[0-9]+.*";

}
