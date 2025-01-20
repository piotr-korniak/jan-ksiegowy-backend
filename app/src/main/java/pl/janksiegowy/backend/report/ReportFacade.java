package pl.janksiegowy.backend.report;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.report.dto.ReportSchemaDto;
import pl.janksiegowy.backend.report.dto.ReportElement;
import pl.janksiegowy.backend.report.dto.ReportSchemaMap;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Log4j2

@AllArgsConstructor
public class ReportFacade {

    private final MigrationService migrationService;
    private final ReportFactory reportFactory;
    private final ReportRepository reportRepository;
    private final ReportQueryRepository reportQueryRepository;
    private final DecreeLineQueryRepository decreeLineQueryRepository;


    public Interpreter calculate( ReportType type, String reportCode, LocalDate startDate, LocalDate endDate) {
        return calculate( type, reportCode, new Interpreter(), startDate, endDate);
    }

    public Interpreter calculate( ReportType type, String reportCode, Interpreter interpreter,
                                  LocalDate startDate, LocalDate endDate) {
        return reportRepository.findByTypeAndCode( type, reportCode)
                .map(report-> calculate( interpreter, report, startDate, endDate))
                .orElseThrow();
    }

    private Interpreter calculate( Interpreter interpreter, ReportSchema report, LocalDate begin, LocalDate end) {
        System.err.println( "Calculate " + report.getCode()+ " with parameters " + report.getParameters());
        report.getItems()
                .forEach( item-> calculate( interpreter, item, begin, end));
        var value= execute( report, interpreter, begin, end);
        interpreter.setVariable( report.getCode(), value!= null? value: BigDecimal.ZERO);
        System.err.println( "Set var " + report.getCode()+ "= " + interpreter.getVariable( report.getCode()));
        return interpreter;
    }

    public ReportElement prepare( ReportType type, String reportCode, LocalDate startDate, LocalDate endDate) {
        return reportRepository.findByTypeAndCode( type, reportCode)
                .map( report-> ReportElement.create()
                        .title( report.getName())
                        .startDate( startDate)
                        .endDate( endDate)
                        .elements( Optional
                                .ofNullable( prepare( new Interpreter(), report, startDate, endDate))
                                .map( ReportElement::getElements)
                                .orElseGet(()-> null)))
                .orElseThrow();
    }

    private ReportElement prepare(Interpreter interpreter, ReportSchema report, LocalDate begin, LocalDate end) {
        var elements= new TreeMap<Integer, ReportElement>();

        System.err.println( "Prepare " + report.getCode()+ " with parameters " + report.getParameters());
        report.getItems().forEach( reportConfig->
            Optional.ofNullable( prepare( interpreter, reportConfig, begin, end))
                    .ifPresent( reportElement-> elements.put( reportConfig.getNo(), reportElement))
        );
        var value= execute( report, interpreter, begin, end);
        interpreter.setVariable( report.getCode(), value!= null? value: BigDecimal.ZERO);
        System.err.println( "Set Var " + report.getCode()+ "= " + interpreter.getVariable( report.getCode()));

        return report.isHidden()? null: ReportElement.create()
                .value( interpreter.getVariable( report.getCode()))
                .name( report.getName())
                .elements( new ArrayList<>( elements.values()));
    }

    private BigDecimal execute(ReportSchema report, Interpreter interpreter, LocalDate begin, LocalDate end) {
        return report.getFunction().accept( new ReportFunction.LineFunctionVisitor<BigDecimal>() {
            @Override public BigDecimal visitLabel() {
                return BigDecimal.ZERO;
            }
            @Override public BigDecimal visitExpression() {
                return interpreter.interpret( report.getParameters());
            }
            @Override public BigDecimal visitTurnoverCt() {
                return decreeLineQueryRepository.turnoverCt( report.getParameters(), begin, end);
            }
            @Override public BigDecimal visitTurnoverDt() {
                return decreeLineQueryRepository.turnoverDt( report.getParameters(), begin, end);
            }
            @Override public BigDecimal visitDevelopedBalanceDt() {
                return decreeLineQueryRepository.developedBalanceDt( report.getParameters(), begin, end);
            }
            @Override public BigDecimal visitDevelopedBalanceCt() {
                return decreeLineQueryRepository.developedBalanceCt( report.getParameters(), begin, end);
            }
            @Override public BigDecimal visitParentBalanceDt() {
                return decreeLineQueryRepository.parentBalanceDt( report.getParameters(), begin, end);
            }
            @Override public BigDecimal visitCalculate() {
                return calculate( ReportType.valueOf( report.getParameters()), report.getName(), begin, end)
                        .getVariable( report.getCode(), BigDecimal.ZERO);
            }
            @Override public BigDecimal visitBalanceCtLike() {
                return decreeLineQueryRepository.balanceCtLike( report.getParameters(), begin, end);
            }
            @Override public BigDecimal visitBalanceDtLike() {
                return decreeLineQueryRepository.balanceDtLike( report.getParameters(), begin, end);
            }
        });
    }

    public String migrate() {
        migrationService.loadReportSchemas()
            .forEach( source-> {
                if( !reportRepository.existsByTypeAndCode( source.getType(), source.getCode())) {
                    Optional.ofNullable( source.getParentCode()).ifPresentOrElse( parentCode->
                        reportQueryRepository.findByTypeAndCode( ReportSchemaDto.class, source.getType(), parentCode)
                                .map( ReportSchemaMap::new)
                                .ifPresent( reportQuery-> {
                                    reportRepository.save( reportFactory.from( reportQuery.add( source)));
                                }),
                        ()-> reportRepository.save( reportFactory.from( source))
                    );
                }

            });
        log.warn( "Reports migration complete!");
        return "Reports migration complete!";
    }
}
