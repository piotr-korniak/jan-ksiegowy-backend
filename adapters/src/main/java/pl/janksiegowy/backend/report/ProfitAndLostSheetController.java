package pl.janksiegowy.backend.report;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.subdomain.TenantController;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.time.LocalDate;

@TenantController
@RequestMapping( "/v2/profit-and-lost-sheet")
public class ProfitAndLostSheetController {

    private final ProfitAndLostSheet profitAndLoss;
    private final PeriodRepository periods;

    public ProfitAndLostSheetController( final PeriodRepository periods,
                                         final ProfitAndLossItems profitAndLossItems) {
        this.periods= periods;
        this.profitAndLoss= new ProfitAndLostSheet( profitAndLossItems);
    }

    @RequestMapping("/extended/{date}")
    public ResponseEntity<String> getExtendedReport(
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate date) {

        return periods.findAnnualByDate( date)
                .map( annualPeriod->
                        ResponseEntity.ok( profitAndLoss.prepare( annualPeriod.getBegin(), date).toString()))
                .orElseGet( ()-> ResponseEntity.ok().build());
    }
}
