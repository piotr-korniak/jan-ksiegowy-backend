package pl.janksiegowy.backend.declaration.factory;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.tax.TaxType;

import java.time.LocalDate;

public interface FactoryStrategy <T, C, F>{

    T create( MonthPeriod period, C calculation, F formatted);

    boolean isApplicable( TaxType taxType);
    LocalDate getDateApplicable();

}
