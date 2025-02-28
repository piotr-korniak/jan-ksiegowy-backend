package pl.janksiegowy.backend.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class ItemConfiguration {
    @Bean
    ItemFacade itemFacade(final ItemRepository items,
                          final MigrationService migrationService) {
        return new ItemFacade( new ItemFactory(), items, migrationService);
    }
}
