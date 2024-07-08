package pl.janksiegowy.backend.report;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;

@TenantController
@RequestMapping( "/v2/balance-sheet/{startDate}/{endDate}")
public class BalanceSheetController {

    private final BalanceSheet balanceSheet;

    public BalanceSheetController( DecreeLineQueryRepository decreeLines){
        this.balanceSheet= new BalanceSheet( decreeLines);
    }

    @GetMapping
    public ResponseEntity getReport(
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok( balanceSheet.prepare( startDate, endDate).toString());
    }
}
