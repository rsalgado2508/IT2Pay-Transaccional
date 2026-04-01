package co.com.it2ex.it2pay.seguridad.accesodatos.mapper.configuracion;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;

@Mapper
public interface ConfiguracionMapper {

    @Cacheable("consultarValorConfiguracion")
    @Select("select valor from gen_configuracion where nombre = #{nombre}")
    public String consultarValorConfiguracion(String nombre);

    @Select("select TO_CHAR(sysdate, 'dd-MM-yyyy HH24:MI:SS') from dual")
    public String consultarFechaSysdate();

    @Select("select sysdate from dual")
    public Date consultarFechaServidor();

}