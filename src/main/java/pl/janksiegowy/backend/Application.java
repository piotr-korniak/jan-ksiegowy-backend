package pl.janksiegowy.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.util.Arrays;

@SpringBootApplication( exclude={
		DataSourceAutoConfiguration.class,
		LiquibaseAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run( Application.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner( Environment environment) {
		return args -> {

		};
	}

}
