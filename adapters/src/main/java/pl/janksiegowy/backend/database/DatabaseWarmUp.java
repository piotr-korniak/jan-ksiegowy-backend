package pl.janksiegowy.backend.database;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Order( 0)
public class DatabaseWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    public DatabaseWarmUp(){
    }

    @Override
    public void onApplicationEvent( @NonNull ContextRefreshedEvent event) {
    }
}
