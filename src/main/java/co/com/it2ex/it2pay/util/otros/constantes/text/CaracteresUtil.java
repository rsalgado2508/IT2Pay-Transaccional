package co.com.it2ex.it2pay.util.otros.constantes.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;
import org.owasp.esapi.ESAPI;

public class CaracteresUtil {

    /** Constante logger. */
    final static Logger logger = LoggerFactory.getLogger(CaracteresUtil.class);

    private CaracteresUtil(){

    }

    public static String eliminarTildes(String texto){

        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";

        String output = texto;

        if(texto!=null){

            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.
                output = output.replace(original.charAt(i), ascii.charAt(i));
            }//for i
        }


        return output;


    }

    /*
     * aavila ini  kiuwan vulnerabilidy
     * se personaliza metodo saneador
     */
    public static String neutralizeMessage(String dato) {
        Pattern[] patterns = new Pattern[] {

                Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };
        if (dato != null) {

            dato = dato.replaceAll("\0", "");

            for (Pattern scriptPattern : patterns) {
                dato = scriptPattern.matcher(dato).replaceAll("");
            }
        }
        return dato;
    }

    /*
     * aavila fin  kiuwan vulnerabilidy
     * se personaliza metodo saneador
     */
    public static String cleanMessage(String message) {
        if(message != null){
            // ensure no CRLF injection into logs for forging records
            String clean = (message.replace( '\n', '_' )).replace( '\r', '_' );
            clean = (clean.replace('\'', '_')).replace( '"', '_' );
            if ( ESAPI.securityConfiguration().getBooleanProp("Logger.LogEncodingRequired") ) {
                clean = ESAPI.encoder().encodeForHTML(clean);
                if (!message.equals(clean)) {
                    clean += " (Encoded)";
                }
            }
            return clean;
        } else {
            return null;
        }
    }


}
