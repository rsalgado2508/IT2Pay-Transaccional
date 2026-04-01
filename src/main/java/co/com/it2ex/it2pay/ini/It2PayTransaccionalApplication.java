package co.com.it2ex.it2pay.ini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("co.com.it2ex.it2pay")
@ServletComponentScan
@EnableAsync
public class It2PayTransaccionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(It2PayTransaccionalApplication.class, args);
	}

}
