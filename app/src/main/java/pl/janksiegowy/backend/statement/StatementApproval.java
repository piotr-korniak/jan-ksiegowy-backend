package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;

import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;

import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class StatementApproval {

    private final PeriodRepository periods;
    private final StatementFacade facade;

    private final MetricRepository metrics;
    private final StatementRepository statements;
    private final EntityQueryRepository entities;
    private final InvoiceLineQueryRepository invoiceLines;


    private void save( StatementDto source) {
        facade.approve( facade.save( source));
    }


    public void approval( String periodId) {
        var version= PatternId.JPK_V7K_2_v1_0e;

        periods.findMonthById( periodId)
                .map( period-> {
                    var metric= metrics.findByDate( period.getBegin()).orElseThrow();

                    var source= XmlConverter.marshal(
                            Factory_JPK_V7.create( period, invoiceLines).prepare( metric));
                    System.err.println( source);

/*
                    var statementDto= statements.findByPatternIdAndPeriod( version, period)
                            .map( statement-> StatementDto.create()
                                    .statementId( statement.getStatementId())
                                    .date( statement.getDate())
                                    .created( statement.getCreated()))
                            .orElse( StatementDto.create()
                                    .date( LocalDate.now())
                                    .created( LocalDateTime.now()))
                        .patternId( version)
                        .type( StatementType.V)
                        .periodId( periodId);


                    var source= version.accept( new PatternId.PatternJpkVisitor<Statement_JPK_V7>() {
                        @Override public Statement_JPK_V7 visit_JPK_V7K_2_v1_0e() {
                            return new Factory_JPK_V7K_2_v1_0e( invoiceLines, metrics).create( period);
                        }
                    } );
                    statementDto.xml( XmlConverter.marshal( source));
                    statementDto.value1( source.korektaNaleznego());
                    statementDto.value2( source.korektaNaliczonego());

                    Optional.ofNullable( source.getP51())
                            .ifPresent( bigInteger-> entities
                                    .findByTypeAndTaxNumber( EntityType.R, metric.getRcCode())
                                    .ifPresent( revenue-> { statementDto
                                            .revenue( revenue)
                                            .liability( new BigDecimal( bigInteger))
                                            .number( "VAT-7K "+ period.getEnd().getYear()+ "K"+
                                                    (( period.getEnd().getMonth().getValue()- 1) / 3 + 1))
                                            .due( period.getEnd().plusMonths( 1).withDayOfMonth( 25));
                            }));

                    save( statementDto);*/
                    return null;



                });
    }


}
