package co.com.it2ex.it2pay.util.otros.constantes.path;

public class ConstantesSeguridadPathRest {

    static final String serviciosPublicos = "publicTran";
    static final String serviciosPrivados = "apiTran";

    //CONFIGURACION-----------------------------------------------------------------------------------------------------

    public static final String PATH_HEALTH = "/" + serviciosPublicos + "/health";
    public static final String PATH_TOKEN = "/" + serviciosPublicos + "/autenticacion";

    public static final String PATH_RE_TOKEN = "/" + serviciosPrivados + "/reautenticacion";

    public static final String PATH_LOGOUT = "/" + serviciosPrivados + "/salir";

    public static final String PATH_CONSULTA_SYSINFO_APP = "/" + serviciosPrivados + "/info/app";

    public static final String PATH_ULTIMA_CONEXION =  "/" + serviciosPrivados + "/ultima_conexion";

    public static final String PATH_MENU = "/" + serviciosPrivados + "/menu";

    public static final String PATH_LISTADETALLE_POR_ID = "/" + serviciosPrivados + "/listadetalle-por-id";

    public static final String PATH_PAISES_POR_IDPAIS = "/" + serviciosPrivados + "/paises-por-idpais";

    public static final String PATH_PAISES = "/" + serviciosPrivados + "/paises";

    public static final String PATH_DEPARTAMENTOS_POR_IDPAIS = "/" + serviciosPrivados + "/departamentos-por-idpais";

    public static final String PATH_MUNICIPIOS_POR_IDDEPARTAMENTO = "/" + serviciosPrivados + "/municipios-por-iddepartamento";

    public static final String PATH_CONSULTAR_TERMINOS = "/" + serviciosPublicos + "/consultar-terminos";

    public static final String PATH_ACTUALIZAR_ACEPTAR_TERMINOS = "/" + serviciosPublicos + "/aceptar-terminos";

    /** The Constant PATH_SWGENERAROTP. */
    public static final String PATH_SWGENERAROTP = "/" + serviciosPrivados + "/sw_generarOTP";

    /** The Constant PATH_SWVALIDAROTP. */
    public static final String PATH_SWVALIDAROTP = "/" + serviciosPrivados + "/sw_validarOTP";

    public static final String PATH_SWVALIDAROTP_PUBLIC = "/" + serviciosPublicos + "/sw_validarOTP_public";

    /** The Constant PATH_Colocadores. **/

    public static final String PATH_ALL_COLOCADORES = "/" + serviciosPrivados + "/consultar_colocadores";
    public static final String PATH_DETALLES_COLOCADOR = "/" + serviciosPrivados + "/detalles_colocador";
    public static final String PATH_INSERTAR_COLOCADOR = "/" + serviciosPrivados + "/crear_colocador";
    public static final String PATH_CAMBIAR_ESTADO_COLOCADOR = "/" + serviciosPrivados + "/cambiar_estado";
    public static final String PATH_DEBLOQUEAR_COLOCADOR = "/" + serviciosPrivados + "/desbloquear_colocador";

    public static final String PATH_MODIFICAR_COLOCADOR = "/" + serviciosPrivados + "/modificar_colocador";
    public static final String PATH_ALL_PUNTOS_DE_VENTA= "/" + serviciosPrivados + "/consultar_puntos_venta";
    public static final String PATH_ALL_RAZON_SOCIAL= "/" + serviciosPrivados + "/consultar_razon_social";
    /** The Constant PATH_Aliados. **/
    public static final String PATH_ALL_ALIADOS = "/" + serviciosPrivados + "/consultar_aliados";
    public static final String PATH_DETALLE_ALIADO = "/" + serviciosPrivados + "/detalle_aliado";

    public static final String PATH_INSERTAR_ALIADO = "/" + serviciosPrivados + "/crear_aliado";
    public static final String PATH_CAMBIAR_ESTADO_ALIADO = "/" + serviciosPrivados + "/cambiar_aliado";
    public static final String PATH_MODIFICAR_ALIADO = "/" + serviciosPrivados + "/modificar_aliado";
    /** The Constant PATH_LINEA_NEGOCIO. **/
    public static final String PATH_ALL_LINEA_NEGOCIO = "/" + serviciosPrivados + "/consultar_lineas_negocio";
    public static final String PATH_DETALLE_LINEA_NEGOCIO = "/" + serviciosPrivados + "/detalles_liena_negocio";
    public static final String PATH_CAMBIAR_ESTADO_LINEA_NEGOCIO = "/" + serviciosPrivados + "/cambiar_estado_liena_negocio";

    public static final String PATH_MODIFICAR_LINEA_NEGOCIO = "/" + serviciosPrivados + "/modificar_liena_negocio";
    /** The Constant PATH_PRODUCTOS. **/
    public static final String PATH_ALL_PRODUCTOS = "/" + serviciosPrivados + "/consultar_productos";
    public static final String PATH_PRODUCTOS_POR_ALIADO = "/" + serviciosPrivados + "/consultar_productos_aliado";
    public static final String PATH_DETALLE_PRODUCTO_POR_ALIADO = "/" + serviciosPrivados + "/consultar_detalle_productos_aliado";
    public static final String PATH_INSERTAR_PRDOUCTO = "/" + serviciosPrivados + "/crear_producto";
    public static final String PATH_DETALLE_PRODUCTO = "/" + serviciosPrivados + "/consultar_detalle_producto";
    public static final String PATH_CAMBIAR_ESTADO_PRODUCTO = "/" + serviciosPrivados + "/cambiar_estado_producto";
    public static final String PATH_MODIFICAR_PRODUCTO = "/" + serviciosPrivados + "/modificar_producto";
    public static final String PATH_CLONAR_PRODUCTO = "/" + serviciosPrivados + "/clonar_producto";
    /** The Constant PATH_AUTORIA. */
    public static final String PATH_CONSULTAR_AUDITORIA = "/" + serviciosPrivados + "/consultar_auditoria";
    public static final String PATH_CONSULTAR_DETALLE_AUDITORIA = "/" + serviciosPrivados + "/consultar_detalle_auditoria";


    /** The Constant PATH_PERSONA. */
    public static final String PATH_CREAR_PERSONA = "/" + serviciosPrivados + "/crear_persona";

    /** The Constant PATH_LOGIN. */
    public static final String PATH_LOGIN ="/" + serviciosPublicos +  "/login";

    public static final String PATH_CAMBIAR_CLAVE_LOGIN ="/" + serviciosPublicos +  "/cambiarClave";

    public static final String PATH_RECORDAR_CONTRASENA ="/" + serviciosPublicos +  "/validar-recordar-contrasena";

    public static final String PATH_CAMBIO_RECORDAR_CONTRASENA = "/" + serviciosPublicos + "/cambio-recordar-contrasena";

    //------------------------------------------------------------------------------------------------------------------

    public static final String PATH_TIPOS_DE_DOCUMENTO_PUBLICO = "/" + serviciosPublicos + "/tipos_documento";
    public static final String PATH_TIPOS_DE_DOCUMENTO = "/" + serviciosPrivados + "/tipos_documento";
    public static final String PATH_TIPOS_DE_PERSONA = "/" + serviciosPrivados + "/tipos_persona";
    public static final String PATH_CONSULTAR_LISTA_USUARIOS = "/" + serviciosPrivados + "/consultarusuarios";
    public static final String PATH_CONSULTAR_USUARIO_POR_ID = "/" + serviciosPrivados + "/consultarusuarioporid";
    public static final String PATH_CREAR_USUARIO = "/" + serviciosPrivados + "/crearusuario";
    public static final String PATH_EDITAR_USUARIO = "/" + serviciosPrivados + "/modificarusuario";
    public static final String PATH_CAMBIAR_ESTADO_USUARIO = "/" + serviciosPrivados + "/cambiarestadousuario";
    public static final String PATH_DESBLOQUEAR_USUARIO = "/" + serviciosPrivados + "/desbloquearusuario";
    public static final String PATH_CONSULTAR_LISTA_ROLES = "/" + serviciosPrivados + "/consultarroles";

    /** The Constant PATH_PUNTO_VENTA. **/
    public static final String PATH_EMPRESAS = "/" + serviciosPrivados + "/empresas";
    public static final String PATH_GUARDAR_PUNTO_VENTA = "/" + serviciosPrivados + "/guardar-punto-venta";
    public static final String PATH_MODIFICAR_PUNTO_VENTA = "/" + serviciosPrivados + "/modificar-punto-venta";
    public static final String PATH_LISTAR_PUNTO_VENTA = "/" + serviciosPrivados + "/consultar-punto-venta";
    public static final String PATH_CAMBIAR_ESTADO_PUNTO_VENTA = "/" + serviciosPrivados + "/cambiar-estado-punto-venta";
    public static final String PATH_CONSULTA_DETALLE_PUNTO_VENTA = "/" + serviciosPrivados + "/consulta-detalle-punto-venta";
    public static final String PATH_CONSULTA_PRODUCTO_PUNTO_VENTA = "/" + serviciosPrivados + "/consulta-producto-punto-venta";
    public static final String PATH_CONSULTAR_PERFIL = "/" + serviciosPrivados + "/consultar-perfil";
    public static final String PATH_CONSULTA_CUPOS_PUNTO_VENTA = "/" + serviciosPrivados + "/consulta-cupos";


    /** The Constant PATH_RAZON_SOCIAL. **/
    public static final String PATH_GUARDAR_RAZON_SOCIAL = "/" + serviciosPrivados + "/guardar-razon-social";
    public static final String PATH_LISTAR_RAZON_SOCIAL = "/" + serviciosPrivados + "/listar-razon-social";
    public static final String PATH_CAMBIAR_ESTADO_RAZON_SOCIAL = "/" + serviciosPrivados + "/cambiar-estado-razon-social";

    /** The Constant PATH_CATEGORIAS. **/
    public static final String PATH_LISTAR_CATEGORIAS = "/" + serviciosPrivados + "/consultar-categoria";
    public static final String PATH_GUARDAR_CATEGORIA = "/" + serviciosPrivados + "/guardar-categoria";
    public static final String PATH_CAMBIAR_ESTADO_CATEGORIA = "/" + serviciosPrivados + "/cambiar-estado-categoria";
    public static final String PATH_MODIFICAR_CATEGORIA = "/" + serviciosPrivados + "/modificar_categoria";
    public static final String PATH_CONSULTA_DETALLE_CATEGORIA = "/" + serviciosPrivados + "/consulta-detalle-categoria";
    public static final String PATH_LISTAR_LINEA_NEGOCIO = "/" + serviciosPrivados + "/consultar-lineas-negocio";

    /** The Constant PATH_PARAMETROS **/
    public static final String PATH_CONSULTAR_PARAMETROS = "/" + serviciosPrivados + "/consultarparametros";

    public static final String PATH_CONSULTAR_GRUPOS_PARAMETROS = "/" + serviciosPrivados + "/consultargruposparametros";

    public static final String PATH_MODIFICAR_PARAMETROS = "/" + serviciosPrivados + "/modificarparametros";

//---------------------------------------------------------------------------------------------------------

    public static final String PATH_PARAMETROS_PUBLICO_POR_NOMBRE = "/" + serviciosPublicos + "/consultar_parametro";

    public static final String PATH_PARAMETROS_VALOR_POR_NOMBRE = "/" + serviciosPrivados + "/consultar_parametro";

    public static final String PATH_CAMBIO_CLAVE_USUARIO = "/" + serviciosPrivados + "/cambio_clave";

    /** The Constant PATH_PARAMETROS **/

    public static final String PATH_REALIZAR_PAGO_ALIADO_PRODUCTO = "/" + serviciosPrivados + "/realizar_pago_aliado_producto";
    public static final String PATH_VALIDAR_PAGO_ALIADO_PRODUCTO = "/" + serviciosPrivados + "/validar_pago_aliado_producto";
    public static final String PATH_CONSULTAR_PARAM_PAGO = "/" + serviciosPrivados + "/consultar_param_pago";
    public static final String PATH_REVERSAR_PAGO = "/" + serviciosPrivados + "/reversar_pago";
    public static final String PATH_ENVIAR_COMPROBANTE_PAGO = "/" + serviciosPrivados + "/enviar_comprobante_pago";

    public static final String PATH_VENTAS_LINEA_NEGOCIO_FECHA = "/" + serviciosPrivados + "/consultar-ventas_linea_negocio";

    public static final String PATH_LISTAR_TRANSACCIONES = "/" + serviciosPrivados + "/consultar-transacciones-actual";
    public static final String PATH_TRANSACCIONES_FECHA = "/" + serviciosPrivados + "/consultar-transacciones";
    public static final String PATH_REVERSAR_TRANSACCION = "/" + serviciosPrivados + "/reversar-transaccion";
    public static final String PATH_ENVIAR_COMPROBANTE = "/" + serviciosPrivados + "/enviar-comprobante";

}
