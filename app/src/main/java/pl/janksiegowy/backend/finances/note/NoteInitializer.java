package pl.janksiegowy.backend.finances.note;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.note.dto.NoteDto;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;

@AllArgsConstructor
public class NoteInitializer {

    private final SettlementQueryRepository settlements;
    private final EntityQueryRepository entities;
    private final NoteFacade facade;
    private final DataLoader loader;

    private void save( NoteDto source) {
        facade.save( source);
    }

    public String init() {
        var total= 0;
        var added= 0;

        for( String[] fields : loader.readData( "notices.txt")) {
            if( fields[0].startsWith( "---" ))
                continue;
            total++;

            var entity= Util.parseTaxNumber( fields[1], "PL");

            if( settlements.existsByNumberAndEntityTaxNumber( fields[0], entity.getTaxNumber()))
                continue;
            added++;

            var amount= new BigDecimal( fields[2]);
            save( NoteDto.create()
                    .type( amount.signum()> 0? NoteType.I: NoteType.N)
                    .number( fields[0])
                    .amount( amount.abs())
                    .date( Util.toLocalDate( fields[3]))
                    .due( Util.toLocalDate( fields[4]))
                    .entity( entities.findByCountryAndTypeAndTaxNumber(
                                    entity.getCountry(), EntityType.C, entity.getTaxNumber())
                            .orElseGet(()-> entities.findByCountryAndTypeAndTaxNumber(
                                    entity.getCountry(), EntityType.B, entity.getTaxNumber())
                            .orElseGet(()-> entities.findByCountryAndTypeAndTaxNumber(
                                    entity.getCountry(), EntityType.R, entity.getTaxNumber())
                            .orElseThrow()))));

        }

        return String.format( "%-40s %16s\n",
                "Notices migration complete, added: ", added+ "/"+ total);
    }


}














