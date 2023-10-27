package pl.janksiegowy.backend.company;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyConfiguration {
    @Bean
    CompanyFacade companyFacade( final CompanyService company,
                                 final CompanyQueryRepository companies) {
        return new CompanyFacade( company, companies);
    }
}
