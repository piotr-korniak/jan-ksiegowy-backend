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
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class StatementFacade {

    private final StatementFactory factory;
    private final StatementRepository statements;
    private final StatementService statementService;

    private final DecreeFacade decrees;

    public Statement save( MonthPeriod period, StatementDto source) {
        return statements.save( factory.from( source, period));
    }

    public void approve( Statement statement) {
        if( statement instanceof PayableStatement)
            decrees.book( (PayableStatement) statement);
    }

    public void process( MonthPeriod period, TaxType taxType) {
        System.err.println( "Begin process Tax Facade: "+ taxType.name());

        var calculation=statementService.calculateStrategy( period.getEnd(), taxType)
                .calculate( period);
        if( calculation==null || calculation.isEmpty())
            return;

        var formatted=statementService.formatterStrategy( period.getEnd(), taxType)
                .format( period, calculation);

        var created= statementService.factoryStrategy( period.getEnd(), taxType)
                .create( period, calculation, formatted);

        save( period, created);

        System.err.println( "Format: "+ formatted);
        System.err.println( "End process Tax Facade!");
    }
}
