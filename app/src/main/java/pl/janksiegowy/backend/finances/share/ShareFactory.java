package pl.janksiegowy.backend.finances.share;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.share.dto.ShareDto;

import pl.janksiegowy.backend.finances.share.ShareType.ShareTypeVisitor;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.PeriodFacade;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ShareFactory implements ShareTypeVisitor<Share> {
    private final EntityRepository entities;
    private final PeriodFacade periods;
    private final MetricRepository metrics;

    public Share from( ShareDto source) {
        return metrics.findByDate( source.getDate())
                .map( metric-> (Share) source.getType().accept( this)
                        .setEquity( source.getEquity())
                        .setAmount( metric.getCapital()
                                .multiply( source.getEquity()).divide( new BigDecimal(100)))
                        .setNumber( source.getNumber())
                        .setIssueDate( source.getDate())
                        .setDueDate( source.getDate().plusDays( 7L))
                        .setDocumentId( Optional.ofNullable( source.getChargeId())
                                .orElseGet( UUID::randomUUID))
                        .setPeriod( periods.findMonthPeriodOrAdd( source.getDate()))
                        .setEntity( entities.findByEntityIdAndDate(
                                source.getEntity().getEntityId(), source.getDate()).orElseThrow()))
                .orElseThrow();
    }

    @Override public Share visitAcquireShare() {
        return new AcquiredShare();
    }

    @Override public Share visitDisposedShare() {
        return new DisposedShare();
    }
}
