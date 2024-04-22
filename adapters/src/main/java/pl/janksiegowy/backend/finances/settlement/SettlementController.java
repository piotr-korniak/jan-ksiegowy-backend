package pl.janksiegowy.backend.finances.settlement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementListDto;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.janksiegowy.backend.statement.CitApproval.DWA_MIEJSCA;

@TenantController
@RequestMapping( value= {"/v2/settlement", "/v2/settlement/{account_number}"})
public class SettlementController {

    private final SettlementQueryRepository settlements;
    private final String rowFormat= "%1s %-30s %10s %10s %8s %12s %12s %12s\n";
    private final String dashes=  String.format( rowFormat,
            String.join("", Collections.nCopies(1, "-")),
            String.join("", Collections.nCopies(30, "-")),
            String.join("", Collections.nCopies(10, "-")),
            String.join("", Collections.nCopies(10, "-")),
            String.join("", Collections.nCopies(8, "-")),
            String.join("", Collections.nCopies(12, "-")),
            String.join("", Collections.nCopies(12, "-")),
            String.join("", Collections.nCopies(12, "-")));

    public SettlementController( final SettlementQueryRepository settlements) {
        this.settlements = settlements;
    }

    @GetMapping
    public ResponseEntity settlement( @PathVariable Optional<String> account_number) {

        var bufor= new StringBuilder();
        account_number.map( settlements::findByEntityAccountNumberOrderByDateDesc)
                .orElseGet( settlements::findByDtNotEqualCtOrderByDateDesc)
        .stream().collect( Collectors.groupingBy(
                SettlementListDto::getEntityAccountNumber, LinkedHashMap::new, Collectors.toList()))
        .values().forEach( lines-> {
                lines.stream()
                    .findFirst()
                    .ifPresent( entity-> {
                        bufor.append( entity.getEntityAccountNumber()+ " > "+ entity.getEntityName()+ "\n");
                        bufor.append( String.format( rowFormat,
                                "T", "Document", "Date   ", "Due    ", "Variance", "Dt   ", "Ct   ", "Saldo   "));
                        bufor.append( dashes);
                        lines.forEach( settlement->
                                bufor.append( settlement!=null? toPrint( settlement): "<empty>\n"));
                        bufor.append( dashes+ "\n");
                    });
        });
        return ResponseEntity.ok(  bufor.toString());
    }

    private String toPrint( SettlementListDto settlement) {

        var saldo= settlement.getDt().subtract( settlement.getCt());
        var variance= saldo.signum()== 0? "-": ChronoUnit.DAYS.between( LocalDate.now(), settlement.getDue());
        return String.format( rowFormat,
                settlement.getType().name(),
                settlement.getNumber(),
                Util.toString( settlement.getDate()),
                Util.toString( settlement.getDue()),
                variance,
                Util.toString( settlement.getDt()),
                Util.toString( settlement.getCt()),
                Util.toString( saldo)
        );
    }
}
