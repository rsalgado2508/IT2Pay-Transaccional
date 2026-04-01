package co.com.it2ex.it2pay.util.otros.constantes.config;

public interface ConstantesCodigosError {

    /** The Constant MENSAJE_NO_ESPECIFICADO. */
    public static final String MENSAJE_NO_ESPECIFICADO = "Mensaje no especificado. ";

    /** The Constant CODIGO_EXITO. */
    public static final String CODIGO_EXITO = "OK";

    /** The Constant MENSAJE_CODIGO_EXITO. */
    public static final String MENSAJE_CODIGO_EXITO = "Operación realizada exitosamente. ";

    /** The Constant CODIGO_ERROR_NO_CONTROLADO. */
    public static final String CODIGO_ERROR_NO_CONTROLADO = "ERR_NO_CTRL";

    /** The Constant MENSAJE_CODIGO_ERROR_NO_CONTROLADO. */
    public static final String MENSAJE_CODIGO_ERROR_NO_CONTROLADO = "(MSJ999) Error no controlado. ";

    /** The Constant CODIGO_ERROR_NO_JSON. */
    public static final String CODIGO_ERROR_NO_JSON = "ERR_NO_JSON";

    /** The Constant MENSAJE_CODIGO_ERROR_NO_JSON. */
    public static final String MENSAJE_CODIGO_ERROR_NO_JSON = "No se encuentra JSON. ";

    /** The Constant CODIGO_ERROR_USU_PWD. */
    public static final String CODIGO_ERROR_USU_PWD = "ERR_USU_PWD";

    /** The Constant MENSAJE_ERROR_USU_PWD. */
    public static final String MENSAJE_ERROR_USU_PWD = "Falta configurar usuario y/o password para autenticar los servicios. ";

    /** The Constant CODIGO_ERROR_USU_PWD_SVC. */
    public static final String CODIGO_ERROR_USU_PWD_SVC = "ERR_USU_PWD_SVC";

    /** The Constant MENSAJE_ERROR_USU_PWD_SVC. */
    public static final String MENSAJE_ERROR_USU_PWD_SVC = "Campos obligatorios. ";

    /** The Constant CODIGO_ERROR_USU_PWD_NOVAL. */
    public static final String CODIGO_ERROR_USU_PWD_NOVAL = "ERR_USU_PWD_NOVAL";

    /** The Constant MENSAJE_ERROR_USU_PWD_NOVAL. */
    public static final String MENSAJE_ERROR_USU_PWD_NOVAL = "Usuario y/o password no coinciden. ";

    /** The Constant MENSAJE_ERROR_TOKEN_INVALIDO_VENCIDO. */
    public static final String MENSAJE_ERROR_TOKEN_INVALIDO_VENCIDO = "JWT Token inválido o vencido. ";


    public static final String MENSAJE_INVOCACION_SERVICIO_METODO = "Servicio / Método invocado ";
    public static final String CODIGO_ERROR_OTP_01 = "ERR_OTP_01";
    public static final String CODIGO_ERROR_OTP_02 = "ERR_OTP_02";
    public static final String CODIGO_ERROR_OTP_03 = "ERR_OTP_03";
    public static final String CODIGO_ERROR_OTP_04 = "ERR_OTP_04";
    public static final String CODIGO_ERROR_OTP_05 = "ERR_OTP_05";

    //COLOCADORES
    public static final String CODIGO_ERROR_COLOCADOR_01 = "ERR_COLOCADOR_01";
    public static final String CODIGO_ERROR_COLOCADOR_02 = "ERR_COLOCADOR_02";
    public static final String MENSAJE_VALIDAR_DOCUMENTO = "Numero de documneto ya registrado";
    //Aliado
    public static final String CODIGO_ERROR_ALIADO_01 = "ERR_ALIADO_01";
    public static final String CODIGO_ERROR_ALIADO_02 = "ERR_ALIADO_02";
    public static final String MENSAJE_VALIDAR_DOCUMENTO_ALIADO = "Numero de documneto ya registrado";
    //Producto
    public static final String CODIGO_ERROR_PRODUCTO_01 = "ERR_PRODUCTO_01";
    public static final String MENSAJE_VALIDAR_EXISTENCIA_PRODUCTO = "El producto ya se encuentra registrada";



    // GENERAL
    public static final String CODIGO_SERVER_NO_DISP = "INF_NO_AT_SERV";
    public static final String CODIGO_ERROR_GENERAR_REPOT = "INF_NO_AT_GREP";
    public static final String CODIGO_ERROR_CONSULTAR_REPOT = "INF_NO_AT_CREP";
    public static final String CODIGO_RECHAZO_EXITOSO_DI = "INF_RECH_EXGEN";
    public static final String CODIGO_ERROR_DELIMITANTE_EXCEL = "ERR_DELI_EXCEL";
    public static final String CODIGO_ERROR_VALIDACION_PERMISO = "ERROR_VALIDACION_PERMISO";
    public static final String CODIGO_ELIMINACION_EXITOSA = "INF_ELIM_EXIT";
    public static final String CODIGO_ELIMINACION_EXITOSA_DI = "INF_PEND_ELIM";
    public static final String CODIGO_ERROR_FALLO_CONX = "ERR_ENV_OTP";
    public static final String CODIGO_MODIFICACION_EXITOSA = "INF_OPE_EXIT";
    public static final String CODIGO_MODIFICACION_EXITOSA_DI = "INF_PEND_MODF";
    public static final String CODIGO_EXITO_PENDIENTE = "OK_PENDIENTE";
    public static final String CODIGO_DATOS_NO_ENCONTRADOS = "INF_DAT_NO_ENC";
    public static final String CODIGO_PENDIENTE_APROBAR = "INF_PEND_APR";
    public static final String CODIGO_RECHAZO_EXITOSO = "INF_RECH_EXTRS";
    public static final String CODIGO_CANCELA_OPERACION = "INF_CANC_OP";

    public static final String CODIGO_ERROR_USUARIO_NO_ENCONTRADO = "ERR_USU_NO_ENC";

    public static final String CODIGO_ERROR_USUARIO_NO_ENCONTRADO_RECUPERAR = "ERR_USU_NO_ENCR";

    public static final String CODIGO_ERROR_USUARIO_BLOQUEADO = "ERR_USU_BLOQ";

    public static final String CODIGO_ERROR_USUARIO_INACTIVO = "ERR_USU_INAC";
    public static final String CODIGO_ERROR_USU_DOCU_EXIST = "ERR_USU_DOC_EXI";
    public static final String CODIGO_ERROR_USU_CORREO_EXIST = "ERR_USU_COR_EXI";
    public static final String CODIGO_CONTRASENA_NO_VAL = "ERR_CONT_NOVAL";
    public static final String CODIGO_EXISTE_CONTRASENA = "ERR_CONT_EXI";
    public static final String CODIGO_CARACTERES_REPETIDOS = "ERR_CARAC_REPE";
    public static final String CODIGO_CARACTERES_MAYUSCULAS = "ERR_MAYUS";
    public static final String CODIGO_CARACTERES_MINUSCULAS = "ERR_MINUS";
    public static final String CODIGO_CARACTERES_MINUS_MAYUS = "ERR_MAYUS_MINUS";
    public static final String CODIGO_ALFANUMERICO = "ERR_ALFANUM";
    public static final String CODIGO_COMBINACION_NUMERICO = "ERR_COMB_NUM";
    public static final String CODIGO_ERROR_CLAVE_NO_SECUENCIAS="ERR_CLAVE_SECU";
    public static final String CODIGO_ERROR_VALIDA_CARAC_ESPECIALES = "ERR_COMB_CARESP";

    public static final String CODIGO_ERROR_BLOQUEO="ERR_BLOQ_USUA";
    public static final String CODIGO_ERROR_INTENTO_BLOQUEO = "ERR_SIU_INT_BLO";
    public static final String CODIGO_MENSAJE_CONTRASENA_BLOQ = "ERR_NO_CONTR";
    public static final String CODIGO_ERROR_ULTIMO_INTENTO = "ERR_SVC_ULT_INT";

    public static final String CODIGO_ERROR_ACTUALIZA_DESBLOQUEO = "ERR_DESB_ACT";

    public static final String CODIGO_ERROR_AUDOTORIA_01 = "ERR_FECH_LIMIT";
    public static final String CODIGO_ERROR_AUDOTORIA_02 = "ERR_FECHAS_MAX";

    public static final String CODIGO_DATOS_NO_ENCONTRADOS_FECHA_ACTUAL = "No se encontraron transacciones para la fecha actual";


}