package pl.janksiegowy.backend.finances.charge;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.finances.charge.dto.ChargeDto;

@AllArgsConstructor
public class ChargeFacade {

    private final ChargeFactory factory;
    private final ChargeRepository repository;
    private final DecreeFacade decrees;

    public void approval( Charge charge) {
        decrees.book( charge);
    }

    public Charge save( ChargeDto source ) {
        return repository.save( factory.from( source));
    }
}
