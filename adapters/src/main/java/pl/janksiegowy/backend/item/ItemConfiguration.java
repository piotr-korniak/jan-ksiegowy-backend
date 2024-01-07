package pl.janksiegowy.backend.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemConfiguration {
    @Bean
    ItemFacade itemFacade( final ItemRepository items) {
        return new ItemFacade( new ItemFactory(), items);
    }
}
