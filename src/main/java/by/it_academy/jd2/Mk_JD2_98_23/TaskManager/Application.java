package by.it_academy.jd2.Mk_JD2_98_23.TaskManager;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config.property.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
@EnableConfigurationProperties({JWTProperty.class})

public class  Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
