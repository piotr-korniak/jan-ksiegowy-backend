package pl.janksiegowy.backend.finances.charge;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.charge.dto.ChargeDto;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;

@AllArgsConstructor
public class ChargeInitializer {

    private final SettlementQueryRepository settlements;
    private final EntityQueryRepository entities;
    private final ChargeFacade facade;
    private final DataLoader loader;

    private void save( ChargeDto source) {
        facade.approval( facade.save( source));
    }

    public String init() {
        var total= 0;
        var added= 0;

        for( String[] fields : loader.readData( "charge.txt")) {
            if( fields[0].startsWith( "---" ))
                continue;
            total++;

            var entity= Util.parseTaxNumber( fields[2], "PL");

            if( settlements.existsByNumberAndEntityTaxNumber( fields[1], entity.getTaxNumber()))
                continue;
            added++;

            save( ChargeDto.create()
                    .type( ChargeType.valueOf( fields[0]))
                    .number( fields[1])
                    .amount( new BigDecimal( fields[3]))
                    .date( Util.toLocalDate( fields[4]))
                    .due( Util.toLocalDate( fields[5]))
                    .entity( entities.findByCountryAndTypeAndTaxNumber(
                                    entity.getCountry(), EntityType.C, entity.getTaxNumber())
                            .orElseGet(()-> entities.findByCountryAndTypeAndTaxNumber(
                                            entity.getCountry(), EntityType.B, entity.getTaxNumber())
                                    .orElseGet(()-> entities.findByCountryAndTypeAndTaxNumber(
                                                    entity.getCountry(), EntityType.H, entity.getTaxNumber())
                                            .orElseGet( ()-> entities.findByCountryAndTypeAndTaxNumber(
                                                            entity.getCountry(), EntityType.O, entity.getTaxNumber())
                                                    .orElseThrow())))));
        }

        return String.format( "%-40s %16s\n",
                "Charges migration complete, added: ", added+ "/"+ total);
    }
}
