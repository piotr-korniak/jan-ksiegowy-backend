package pl.janksiegowy.backend.finances.share;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.finances.share.dto.ShareDto;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;

@AllArgsConstructor
public class ShareInitializer {

    private final SettlementQueryRepository settlements;
    private final EntityQueryRepository entities;
    private final ShareFacade facade;
    private final DataLoader loader;

    private void save( ShareDto source) {
        facade.approval( facade.save( source));
    }

    public String init() {
        var total= 0;
        var added= 0;

        for( String[] fields : loader.readData( "share.txt")) {
            if( fields[0].startsWith( "---" ))
                continue;
            total++;

            var entity= Util.parseTaxNumber( fields[1], "PL");

            if( settlements.existsByNumberAndEntityTaxNumber( fields[2], entity.getTaxNumber()))
                continue;
            added++;

            save( ShareDto.create()
                    .type( ShareType.valueOf( fields[0]))
                    .number( fields[2])
                    .equity( new BigDecimal( fields[3]))
                    .date( Util.toLocalDate( fields[4]))
                    .entity( entities.findByCountryAndTypeAndTaxNumber(
                                    entity.getCountry(), EntityType.H, entity.getTaxNumber())
                                            .orElseThrow()));
        }

        return String.format( "%-40s %16s\n",
                "Shares migration complete, added: ", added+ "/"+ total);
    }
}
