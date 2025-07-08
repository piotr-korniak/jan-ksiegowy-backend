package pl.janksiegowy.backend.report;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.report.dto.ReportElement;
import pl.janksiegowy.backend.subdomain.DomainController;

import java.time.LocalDate;

@DomainController
@RequestMapping( "/v2/balance-sheet/{date}")
public class BalanceSheetController {

    private final PeriodRepository periods;
    private final ReportFacade reportFacade;

    public BalanceSheetController( final PeriodRepository periods,
                                   final ReportFacade reportFacade){
        this.periods= periods;
        this.reportFacade= reportFacade;
    }

    @GetMapping
    public ResponseEntity<ReportElement> getReport(
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate date) {

        return periods.findAnnualByDate( date)
                .map( annualPeriod->
                        ResponseEntity.ok(
                                //balanceSheet.prepare( annualPeriod.getBegin(), date).toString()))
                                reportFacade.prepare( ReportType.B, "Bilans", annualPeriod.getBegin(), date)))
                .orElseGet( ()-> ResponseEntity.ok().build());
    }
}
