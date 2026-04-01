package co.com.it2ex.it2pay.ini;

import javax.sql.DataSource;

import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

@EnableAsync
@Configuration
@MapperScan({"co.com.it2ex.it2pay.seguridad.accesodatos.mapper", "co.com.it2ex.it2pay.util.accesodatos.mapper"})
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    private String from;
    private static final String PARAMETRO_MODULO = "SERVWCORR";

    private static final String PARAMETRO_STARTTLS = "STARTTLS";

    private static final String DEFAULT_PARAMETRO_PROTOCOL = "smtp";

    private static final String PARAMETRO_PROTOCOL = "PROTOCOL";

    private static final int    PARAMETRO_DEFAULT_PORT = 587;

    private static final String PARAMETRO_PORT = "PORT";

    private static final String DEFAULT_PARAMETRO_PASSWORD = "rjcwzhfpktjrgsxr";

    private static final String DEFAULT_PARAMETRO_HOST = "smtp.gmail.com";

    private static final String PARAMETRO_PASSWORD = "PASSWORD";

    private static final String PARAMETRO_HOST = "HOST";

    private static final String DEFAULT_PARAMETRO_FROM = "nromero@it2ex.com";

    private static final String PARAM_DEBUG = "DEBUG";

    private static final String DEFAULT_PARAMETRO_AUTH = "true";

    private static final String DEFAULT_PARAMETRO_DEBUG = "true";

    private static final String DEFAULT_PARAMETRO_STARTTLS = "true";

    private static final String PARAMETRO_AUTH = "AUTH";

    private static final String PARAM_FROM = "FROM";

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


        mailSender.setHost(DEFAULT_PARAMETRO_HOST);
        mailSender.setPort(PARAMETRO_DEFAULT_PORT);
        mailSender.setUsername(DEFAULT_PARAMETRO_FROM);
        mailSender.setPassword(DEFAULT_PARAMETRO_PASSWORD);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", DEFAULT_PARAMETRO_PROTOCOL);
        props.put("mail.smtp.auth", DEFAULT_PARAMETRO_AUTH);
        props.put("mail.smtp.starttls.enable",DEFAULT_PARAMETRO_STARTTLS);
        props.put("mail.debug", DEFAULT_PARAMETRO_DEBUG);

        from = DEFAULT_PARAMETRO_FROM;



        try {


            String paraAuth = DEFAULT_PARAMETRO_AUTH;
            String paraDebug = DEFAULT_PARAMETRO_DEBUG;
            String paraFrom = DEFAULT_PARAMETRO_FROM;
            String paraHost = DEFAULT_PARAMETRO_HOST;
            String paraPassword = DEFAULT_PARAMETRO_PASSWORD;
            String paraPort = PARAMETRO_DEFAULT_PORT + "";
            String paraProtocol = DEFAULT_PARAMETRO_PROTOCOL;
            String paraStarttls = DEFAULT_PARAMETRO_STARTTLS;


            if (StringUtils.isNoneEmpty(paraAuth)) {
                props.put("mail.smtp.auth",StringUtils.lowerCase(paraAuth));

            }
            if (StringUtils.isNoneEmpty(paraDebug)) {
                props.put("mail.debug", StringUtils.lowerCase(paraDebug));

            }
            if (StringUtils.isNoneEmpty(paraFrom)) {
                mailSender.setUsername(StringUtils.trim(paraFrom));
                from=StringUtils.trim(paraFrom);

            }
            if (StringUtils.isNoneEmpty(paraHost)) {
                mailSender.setHost(StringUtils.lowerCase(paraHost));

            }
            if (StringUtils.isNoneEmpty(paraPassword)) {
                mailSender.setPassword(StringUtils.trim(paraPassword));

            }
            if (StringUtils.isNoneEmpty(paraPort)) {
                mailSender.setPort(Integer.parseInt(paraPort));


            }
            if (StringUtils.isNoneEmpty(paraProtocol)) {

                props.put("mail.transport.protocol", StringUtils.lowerCase(paraProtocol));

            }
            if (StringUtils.isNoneEmpty(paraStarttls)) {

                props.put("mail.smtp.starttls.enable",StringUtils.lowerCase(paraStarttls));

            }



        } catch (Exception e) {
            Logger LOGGER = LoggerFactory.getLogger(this.getClass());
            LOGGER.error(ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO, e);
        }


        return mailSender;
    }

}
