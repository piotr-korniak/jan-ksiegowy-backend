package pl.janksiegowy.backend;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import pl.janksiegowy.backend.shared.indicator.IndicatorsProperties;

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
