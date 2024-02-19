package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;

import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;

import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.pattern.PatternCode;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlGenerator;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class StatementApproval extends XmlGenerator{

    private final PeriodRepository periods;
    private final StatementFacade statement;

    private final MetricRepository metrics;
    private final StatementRepository statements;
    private final EntityQueryRepository entities;
    private final InvoiceLineQueryRepository invoiceLines;

    public void approval( String periodId) {
        var version= PatternId.JPK_V7K_2_v1_0e;

        periods.findMonthById( periodId)
                .map( period-> {
                    var metric= metrics.findByDate( period.getBegin()).orElseThrow();

                    var statementDto= statements.findByPatternIdAndPeriod( PatternCode.JPK_V7K, period)
                            .map( statement-> StatementDto.create()
                                    .statementId( statement.getStatementId())
                                    .date( statement.getDate())
                                    .created( statement.getCreated()))
                            .orElse( StatementDto.create()
                                    .date( LocalDate.now())
                                    .created( LocalDateTime.now()))
                        .patternCode( PatternCode.JPK_V7K)
                        .periodId( periodId);

                    Optional.ofNullable( version.accept( new XmlGenerator<BigInteger>(){

                        @Override public BigInteger visit_JPK_V7K_2_v1_0e() {
                            var source= new Factory_JPK_V7K_2_v1_0e( invoiceLines, metrics).create( period);
                            statementDto.xml( marshal( source));

                            // Pobranie wyniku
                            return Optional.ofNullable( source.deklaracja())
                                    .map( deklaracja-> deklaracja.pozycjeSzczegolowe().getP51())
                                    .orElse( null);
                        }})
                    ).ifPresent( bigInteger-> entities
                            .findByTypeAndTaxNumber( EntityType.R, metric.getRcCode())
                            .ifPresent( revenue-> { statementDto
                                    .revenue( revenue)
                                    .liability( new BigDecimal( bigInteger))
                                    .number( "VAT-7K "+ period.getEnd().getYear()+ "K"+
                                            (( period.getEnd().getMonth().getValue()- 1) / 3 + 1))
                                    .due( period.getEnd().plusMonths( 1).withDayOfMonth( 25));
                            }));

                    statement.save( statementDto);
                    return null;
                });
    }


}
