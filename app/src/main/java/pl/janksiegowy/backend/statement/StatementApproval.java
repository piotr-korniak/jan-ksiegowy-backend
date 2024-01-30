package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;

import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.pattern.PatternCode;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class StatementApproval {

    private final PeriodRepository periods;
    private final StatementFacade statement;

    private final MetricRepository metrics;
    private final StatementRepository statements;
    private final EntityQueryRepository entities;
    private final InvoiceLineQueryRepository invoiceLines;

    public void approval( String periodId) {
        var version= JpkVat.JPK_V7K_2_v1_0e;

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
                        .periodId( periodId);

                    // Pobranie wzorca
                    var wzorzec= version.accept( new JpkVat.Jpk_Vat7kVisitor<Object>() {
                        @Override public Object visit_JPK_V7K_2_v1_0e() {
                            return new Factory_JPK_V7K_2_v1_0e( invoiceLines, metrics).create( period);
                        }
                    });

                    try {   // przetworzenie wzorca
                        var result= new StringWriter();
                        var marshaller= JAXBContext
                                .newInstance( wzorzec.getClass()).createMarshaller();

                        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                        marshaller.marshal( wzorzec, result);
                        statementDto.xml( result.toString());

                        System.err.println( result.toString());
                    } catch (JAXBException e) {
                        throw new RuntimeException( e );
                    }

                    // Pobranie wyniku
                    statement.save( version.accept( new JpkVat.Jpk_Vat7kVisitor<StatementDto>() {
                        @Override public StatementDto visit_JPK_V7K_2_v1_0e() {
                            Optional.ofNullable( ((Ewidencja_JPK_V7K_2_v1_0e)wzorzec).deklaracja())
                                .ifPresent( deklaracja-> entities
                                    .findByTypeAndTaxNumber( EntityType.R, metric.getRcCode())
                                        .ifPresent( revenue-> { statementDto
                                            .revenue( revenue)
                                            .liability( new BigDecimal( deklaracja.pozycjeSzczegolowe().getP51()))
                                            .number( "VAT-7K "+ period.getEnd().getYear()+ "K"+
                                                (( period.getEnd().getMonth().getValue()- 1) / 3 + 1))
                                            .due( period.getEnd().plusMonths( 1).withDayOfMonth( 25));
                                }));
                            return statementDto.patternCode( PatternCode.JPK_V7K);
                        }
                    }));

                    return null;
                });
    }

}
