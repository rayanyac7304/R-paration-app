package reparation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class reparationAppareilsApplication {

	public static void main(String[] args) {
	
		ApplicationContext springApp = SpringApplication.run(reparationAppareilsApplication.class, args);
	}

}
