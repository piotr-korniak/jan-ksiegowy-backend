package pl.janksiegowy.backend.tax;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TaxService {

    private final List<SpecificItems<Interpreter>> strategies;

    public Interpreter calculate( Period period, TaxType taxType){
        return strategies.stream()
                .filter( s-> s.isApplicable( taxType)&&
                        !s.getDateApplicable().isAfter( period.getEnd()))
                .max( Comparator.comparing( SpecificItems::getDateApplicable))
                .map( specificItems -> specificItems.calculate( period))
                .orElseGet( Interpreter::new);
    }
}
