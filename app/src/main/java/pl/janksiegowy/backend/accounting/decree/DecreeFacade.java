package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.declaration.PayableDeclaration;

@AllArgsConstructor
public class DecreeFacade {

    private final DecreeFactory decree;
    private final DecreeRepository decrees;

    public Decree book( Document document) {
        return save( decree.to( document));
    }

    public void book( PayableDeclaration statement) {
        save( decree.to( statement));
    }

    public Decree save( DecreeDto source) {
        return decrees.save( decree.from( source));
    }

    public Decree book( MonthPeriod month) {
        return save( decree.to( month));
    }
}
