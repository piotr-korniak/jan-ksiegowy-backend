package pl.janksiegowy.backend.finances.notice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.shared.MigrationService;

@Configuration
public class NoteConfiguration {

    @Bean
    NoticeFacade noteFacade(final EntityRepository entityRepository,
                            final PeriodFacade period,
                            final NoteRepository notice,
                            final DecreeFacade decree,
                            final MigrationService migrationService,
                            final EntityQueryRepository entities,
                            final SettlementQueryRepository settlements) {
        return new NoticeFacade( new NoticeFactory( entityRepository, period),
                notice, decree, migrationService, entities, settlements);
    }
}
