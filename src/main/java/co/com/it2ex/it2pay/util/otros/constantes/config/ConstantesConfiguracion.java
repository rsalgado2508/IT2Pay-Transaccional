package co.com.it2ex.it2pay.util.otros.constantes.config;

import java.text.SimpleDateFormat;

public class ConstantesConfiguracion {

    /**
     * Validación y generación de OTP: ATH - INT
     * */
    public final static String TIPO_GENERACION_VALIDACION_OTP = "TIPO_GENERACION_VALIDACION_OTP";

    /**
     * Activar envio de SMS: S - N
     */
    public final static String ACTIVAR_ENVIO_SMS = "ACTIVAR_ENVIO_SMS";

    /**
     * Tipo de obtención de datos funcionario: LDAP - INT
     */
    public static final String TIPO_DATOS_FUNCIONARIO = "TIPO_DATOS_FUNCIONARIO";

    /**
     * Path para almacenar archivos adjuntos notas del proyecto
     */
    public static final String PATH_ADJUNTOS_NOTAS_PROYECTO = "PATH_ADJUNTOS_NOTAS_PROYECTO";

    /**
     * Versión de la base de datos
     */
    public static final String VERSION_BD = "VERSION_BD";

    /**
     * Constante para formato de hora
     */
    public static final SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");
}