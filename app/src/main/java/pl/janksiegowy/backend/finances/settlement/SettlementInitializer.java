package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind.SettlementKindVisitor;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;

@AllArgsConstructor
public class SettlementInitializer {

    private final SettlementQueryRepository settlements;
    private final EntityQueryRepository entities;
    private final SettlementFacade settlement;
    private final DecreeFacade decree;
    private final DataLoader loader;

    private void save( SettlementDto source) {
        var settlement= this.settlement.save( source);

        switch ( settlement.getType()) {  // book only Charge, Fee, Note
            case C, F, N -> decree.book( (FinancialSettlement) settlement);
        }
    }

    public String init() {
        var total= 0;
        var added= 0;

        for( String[] fields : loader.readData( "settlement.txt" ) ) {
            if( fields[0].startsWith( "---" ) )
                continue;
            total++;

            var taxNumber= fields[3].replaceAll( "[^a-zA-Z0-9]", "");

            if( settlements.existsByNumberAndEntityTaxNumber( fields[2], taxNumber))
                continue;
            added++;

            save( SettlementKind.valueOf( fields[1])
                    .accept( new SettlementKindVisitor<SettlementDto.Proxy>() {
                @Override public SettlementDto.Proxy visitDebit() {
                    return SettlementDto.create()
                            .kind( SettlementKind.D)
                            .dt( new BigDecimal( fields[4]));
                }
                @Override public SettlementDto.Proxy visitCredit() {
                    return SettlementDto.create()
                            .kind( SettlementKind.C )
                            .ct( new BigDecimal( fields[4]));
                }
            }).type( SettlementType.valueOf( fields[0]))
                .number( fields[2])
                .date( Util.toLocalDate( fields[5]))
                .due( Util.toLocalDate( fields[6]))
                .entity( entities.findByTypeAndTaxNumber( EntityType.B, taxNumber)
                    .orElseGet(()-> entities.findByTypeAndTaxNumber( EntityType.C, taxNumber)
                            .orElseThrow())));

        }

        return String.format( "%-40s %16s\n", "Settlement migration complete, added: ",
                added+ "/"+ total);

    }
}
