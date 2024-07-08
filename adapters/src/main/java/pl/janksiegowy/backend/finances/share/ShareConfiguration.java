package pl.janksiegowy.backend.finances.share;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.charge.ChargeRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodFacade;

@Configuration
public class ShareConfiguration {

    @Bean
    public ShareFacade shareFacade(final EntityRepository entities,
                                   final PeriodFacade period,
                                   final MetricRepository metrics,
                                    final ShareRepository share,
                                   final DecreeFacade decree) {
        return new ShareFacade( new ShareFactory( entities, period, metrics), share, decree);
    }
}
