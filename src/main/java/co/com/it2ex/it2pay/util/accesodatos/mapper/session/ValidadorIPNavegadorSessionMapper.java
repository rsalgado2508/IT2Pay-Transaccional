package co.com.it2ex.it2pay.util.accesodatos.mapper.session;

import co.com.it2ex.it2pay.util.modelo.session.InfoIPNavegadorSessionDTO;
import co.com.it2ex.it2pay.util.modelo.session.UsuarioIPNavegadorSessionDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ValidadorIPNavegadorSessionMapper {

    /**
     * ========================================
     * AccesosCorrectosPorUsuario
     * ========================================
     */

    @Insert(" INSERT INTO SES_LGOK "
            +"("
            +"  USUARIOLOGIN         ,  "
            +"	TIPOPERSONA          ,  "
            +"	DIRECCIONIP          ,  "
            +"	NAVEGADOR               "
            +")                         "
            +"VALUES                    "
            +"("
            +"  #{login}             ,  "
            +"  #{tipoUsuario}       ,  "
            +"  #{direccionIP}       ,  "
            +"  #{navegador}            "
            +")                         ")
    public void insertAccesoCorrectoPorUsuario(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);

    @Select(" SELECT "
            +"USUARIOLOGIN login		,    "
            +"TIPOPERSONA  tipoUsuario	,    "
            +"DIRECCIONIP  direccionIP	,    "
            +"NAVEGADOR    navegador	    "
            +"FROM SES_LGOK     			 "
            +"where USUARIOLOGIN =  #{login} "
            +"and  TIPOPERSONA=#{tipoUsuario}")
    public InfoIPNavegadorSessionDTO getAccesoCorrectoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);


    @Delete(" DELETE from SES_LGOK "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public void deleteAccesoCorrectoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);

    @Delete(" DELETE from SES_LGOK "
            + " where DIRECCIONIP =#{direccionIP} ")
    public void deleteAccesoCorrectoPorIpNavegador(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);

    @Delete(" DELETE from SES_AIPOK "
            + " where DIRECCIONIP =#{direccionIP} ")
    public void deleteAccesoCorrectoPorIp(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);

    @Update(" UPDATE SES_LGOK "
            + "SET DIRECCIONIP = #{direccionIP} "
            + ", NAVEGADOR = #{navegador} "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public void actualizarAccesoCorrectoPorUsuario(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);

    @Select(" SELECT COUNT(*) from SES_LGOK "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public long getAccesoCorrectoPorUsuarioCount(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);


    /**
     * ========================================
     * AccesoFallidoPorUsuario
     * ========================================
     */

    @Insert("INSERT INTO SES_LGNOK "
            +"("
            +"  USUARIOLOGIN         ,  "
            +"	TIPOPERSONA          ,  "
            +"	DIRECCIONIP          ,  "
            +"	NAVEGADOR               "
            +")                         "
            +"VALUES                    "
            +"("
            +"  #{login}             ,  "
            +"  #{tipoUsuario}       ,  "
            +"  #{direccionIP}       ,  "
            +"  #{navegador}            "
            +")                         ")
    public void insertAccesoFallidoPorUsuario(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);


    @Select(" SELECT "
            +"USUARIOLOGIN login		,    "
            +"TIPOPERSONA  tipoUsuario	,    "
            +"DIRECCIONIP  direccionIP	,    "
            +"NAVEGADOR    navegador	     "
            +"FROM SES_LGNOK     			 "
            +"where USUARIOLOGIN =  #{login} "
            +"and  TIPOPERSONA=#{tipoUsuario}")
    public InfoIPNavegadorSessionDTO getAccesoFallidoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);


    @Delete(" DELETE from SES_LGNOK "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public void deleteAccesoFallidoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);


    /**
     * ========================================
     * AccesoCorrectoPorIPNavegador
     * ========================================
     */
    @Insert("INSERT INTO SES_AIPOK "
            +"("
            +"  USUARIOLOGIN         ,  "
            +"	TIPOPERSONA          ,  "
            +"	DIRECCIONIP          ,  "
            +"	NAVEGADOR               "
            +")                         "
            +"VALUES                    "
            +"("
            +"  #{login}             ,  "
            +"  #{tipoUsuario}       ,  "
            +"  #{direccionIP}       ,  "
            +"  #{navegador}            "
            +")                         ")
    public void insertAccesoCorrectoPorIPNavegador(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);


    @Select(" SELECT "
            +"USUARIOLOGIN login		,    "
            +"TIPOPERSONA  tipoUsuario	,    "
            +"DIRECCIONIP  direccionIP	,    "
            +"NAVEGADOR    navegador	     "
            +"FROM SES_AIPOK     			 "
            +"where DIRECCIONIP =  #{direccionIP} "
            +"and  NAVEGADOR=#{navegador}")
    public InfoIPNavegadorSessionDTO getAccesoCorrectoPorIPNavegador(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);

    @Delete(" DELETE from SES_AIPOK "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public void deleteAccesoCorrectoIPNavegadorPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);

    @Update(" UPDATE SES_AIPOK "
            + "SET DIRECCIONIP = #{direccionIP} "
            + ", NAVEGADOR = #{navegador} "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public void actualizarAccesoCorrectoIPNavegadorPorUsuario(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);

    @Select(" SELECT COUNT(*) from SES_AIPOK "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public long getAccesoCorrectoIPNavegadorPorUsuario(InfoIPNavegadorSessionDTO infoIPNavegadorSessionDTO);


    /**
     * ========================================
     * FechaUltimoAccesoValidoPorUsuario
     * ========================================
     */
    @Insert("INSERT INTO SES_UAOK "
            +"("
            +"  USUARIOLOGIN         ,  "
            +"	TIPOPERSONA          ,  "
            +"	FECHAULTIMOINGRESO      "
            +")                         "
            +"VALUES                    "
            +"(                         "
            +"  #{login}             ,  "
            +"  #{tipoUsuario}       ,  "
            +"  #{fechaUltimoIngreso}   "
            +")                         ")
    public void insertUltimoAccesoValidoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);

    @Select(" SELECT "
            +"USUARIOLOGIN login					,"
            +"TIPOPERSONA  tipoUsuario				,"
            +"FECHAULTIMOINGRESO  fechaUltimoIngreso "
            +"FROM SES_UAOK     			 		 "
            +"where USUARIOLOGIN 	=  #{login} 	 "
            +"and  TIPOPERSONA		=#{tipoUsuario}	 ")
    public UsuarioIPNavegadorSessionDTO getUltimoAccesoValidoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);


    @Delete(" DELETE from SES_UAOK "
            + "where USUARIOLOGIN =  #{login} "
            + "and  TIPOPERSONA=#{tipoUsuario}")
    public void deleteUltimoAccesoValidoPorUsuario(UsuarioIPNavegadorSessionDTO usuarioIPNavegadorSessionDTO);




}