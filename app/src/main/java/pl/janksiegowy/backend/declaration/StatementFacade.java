package pl.janksiegowy.backend.declaration;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.declaration.dto.StatementDto;
import pl.janksiegowy.backend.tax.TaxType;

@AllArgsConstructor
public class StatementFacade {

    private final StatementFactory factory;
    private final StatementRepository statements;
    private final StatementService statementService;

    private final DecreeFacade decrees;

    public Declaration save(MonthPeriod period, StatementDto source) {
        return statements.save( factory.from( source, period));
    }

    public void approve( Declaration statement) {
        if( statement instanceof PayableDeclaration)
            decrees.book( (PayableDeclaration) statement);
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

        approve( save( period, created));

        System.err.println( "Format: "+ formatted);
        System.err.println( "End process Tax Facade!");
    }
}
