package pl.janksiegowy.backend.finances.share;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.charge.dto.ChargeDto;
import pl.janksiegowy.backend.finances.share.dto.ShareDto;

@AllArgsConstructor
public class ShareFacade {

    private final ShareFactory factory;
    private final ShareRepository repository;
    private final DecreeFacade decrees;

    public void approval( Share share) {
        decrees.book( share);
    }

    public Share save( ShareDto source ) {
        return repository.save( factory.from( source));
    }
}
