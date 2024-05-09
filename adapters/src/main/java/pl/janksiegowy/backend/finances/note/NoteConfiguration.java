package pl.janksiegowy.backend.finances.note;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodFacade;

@Configuration
public class NoteConfiguration {

    @Bean
    NoteFacade noteFacade( final EntityRepository entities,
                           final PeriodFacade period,
                           final NoteRepository notice,
                           final DecreeFacade decree ) {
        return new NoteFacade( new NoteFactory( entities, period), notice, decree);
    }
}
