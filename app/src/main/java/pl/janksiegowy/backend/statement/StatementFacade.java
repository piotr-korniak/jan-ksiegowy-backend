package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.dto.StatementDto;
import pl.janksiegowy.backend.statement.dto.StatementMap;
import pl.janksiegowy.backend.tax.TaxCalculatorManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class StatementFacade {

    private final StatementFactory factory;
    private final StatementRepository statements;

    private final DecreeFacade decrees;

    private final List<Factory_JPK_V7> factoriesJpkV7;

    private final TaxCalculatorManager taxCalculatorManager;

    public Statement save( MonthPeriod period, StatementDto source) {
        return statements.save( factory.from( source, period));
    }

    public void approveJpkV7( StatementDto source) {
        factoriesJpkV7.forEach( factoryJpkV7 -> {
            System.out.println("Approving JPK V7: " + factoryJpkV7.getClass().getSimpleName());

            factoryJpkV7.prepare( StatementMap.create( source));
/*
            //save( period,
            statements.findFirstByPatternIdAndPeriodOrderByNoDesc( source.getPatternId(), source.getPeriod())
                    .filter( statement-> StatementStatus.S != statement.getStatus())
                    .map( statement-> StatementDto.create()
                            .statementId( statement.getStatementId())
                            .no( jpk.setReason( statement.getNo())))
                    .orElseGet(()-> StatementDto.create()
                            .no( jpk.setReason( Integer.valueOf(  numerator.
                                    increment( NumeratorCode.ST, "JPK", period.getEnd())))))
                    .xml( XmlConverter.marshal( jpk.prepare( metric)))
                    .patternId( version)
                    .kind( jpk.isSettlement()? StatementKind.S: StatementKind.R )
                    .type( jpk.isSettlement()? StatementType.V: StatementType.R)
                    .date( Util.min( LocalDate.now(), period.getEnd().plusDays( 25)))
                    .due( period.getEnd().plusDays( 25 ))
                    .revenue( entities.findByTypeAndTaxNumber( EntityType.R, metric.getRcCode())
                            .orElseThrow())
                    .number( jpk.getNumber())
                    .created( LocalDateTime.now())
                    .liability( jpk.getLiability())
                    .value1( jpk.getOutputCorrection())
                    .value2( jpk.getInputCorrection())
                    .period( jpk.getPeriod()));*/

        });
    }

    public void approve( Statement statement) {
        if( statement instanceof PayableStatement)
            decrees.book( (PayableStatement) statement);
    }
}
