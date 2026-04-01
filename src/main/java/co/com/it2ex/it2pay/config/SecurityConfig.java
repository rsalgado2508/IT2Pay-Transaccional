package co.com.it2ex.it2pay.config;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Transaccional
 * @Modulo: Portal Transaccional Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 9/5/2023
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AuthenticationProvider provider;

    //@Autowired
    //private LoggerAuditoriasComponent loggerAuditoriasComponent;

    private static final String STRING_URL_PROTECTED = "/apiTran/**";

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher(STRING_URL_PROTECTED)
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

        http
                .authorizeRequests()
                .antMatchers(STRING_URL_PROTECTED).authenticated()
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                //.addFilterAfter(new FiltroUsuarioInvalido(), SwitchUserFilter.class)
                .authorizeRequests()
                .requestMatchers(PROTECTED_URLS)
                .authenticated()
                .and().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {

                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException authException) throws IOException, ServletException {
                        if (authException != null) {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ".");
                        }
                    }
                })
                .and()
                .requiresChannel()
                .antMatchers("/").requiresSecure()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

    }

    public SecurityConfig(final AuthenticationProvider authenticationProvider) {
        super();
        this.provider = authenticationProvider;
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }
}