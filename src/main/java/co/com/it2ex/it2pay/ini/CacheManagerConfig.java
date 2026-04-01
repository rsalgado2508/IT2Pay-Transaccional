package co.com.it2ex.it2pay.ini;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheManagerConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    @Primary
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("consultarValorConfiguracion"),
                new ConcurrentMapCache("consultarMensajePorIdioma"),
                new ConcurrentMapCache("consultarValorParamConfig"),
                new ConcurrentMapCache("consultarMenuPorLoginYRol"),
                new ConcurrentMapCache("obtenerNombreRol"),
                new ConcurrentMapCache("obtenerFuncionalidadRequiereOTPPorUrl"),
                new ConcurrentMapCache("consultaDatosBasicosUsuarioPorUsuarioClave"),
                new ConcurrentMapCache("consultarTipoBloqueo"),
                new ConcurrentMapCache("obtenerTiposDeDocumento"),
                new ConcurrentMapCache( "consultarFuncionalidadPorURL" )
        ));
        return cacheManager;
    }
}
