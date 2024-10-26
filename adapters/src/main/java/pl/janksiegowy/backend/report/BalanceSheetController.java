package pl.janksiegowy.backend.report;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.subdomain.TenantController;
import pl.janksiegowy.backend.tax.vat.BalanceItems;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.time.LocalDate;

@TenantController
@RequestMapping( "/v2/balance-sheet/{date}")
public class BalanceSheetController {

    private final BalanceSheet balanceSheet;
    private final PeriodRepository periods;

    public BalanceSheetController( final PeriodRepository periods,
                                   final BalanceItems balanceItems){
        this.periods = periods;
        this.balanceSheet= new BalanceSheet( balanceItems);
    }

    @GetMapping
    public ResponseEntity<String> getReport(
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate date) {

        return periods.findAnnualByDate( date)
                .map( annualPeriod->
                        ResponseEntity.ok( balanceSheet.prepare( annualPeriod.getBegin(), date).toString()))
                .orElseGet( ()-> ResponseEntity.ok().build());
    }
}
