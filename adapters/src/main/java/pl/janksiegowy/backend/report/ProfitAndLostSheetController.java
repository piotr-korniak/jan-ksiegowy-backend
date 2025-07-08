package pl.janksiegowy.backend.report;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.report.dto.ReportElement;
import pl.janksiegowy.backend.subdomain.DomainController;

import java.time.LocalDate;

@DomainController
@RequestMapping( "/v2/profit-and-lost-sheet")
public class ProfitAndLostSheetController {

    private final PeriodRepository periods;
    private final ReportFacade reportFacade;

    public ProfitAndLostSheetController( final PeriodRepository periods,
                                         final ReportFacade reportFacade ) {
        this.periods= periods;
        this.reportFacade= reportFacade;
    }

    @RequestMapping("/extended/{date}")
    public ResponseEntity<ReportElement> getExtendedReport(
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate date) {

        return periods.findAnnualByDate( date)
                .map( annualPeriod->
                        ResponseEntity.ok(
                                //profitAndLoss.prepare( annualPeriod.getBegin(), date)))
                                reportFacade.prepare( ReportType.P, "RZiS", annualPeriod.getBegin(), date)))
                .orElseGet(()-> ResponseEntity.ok().build());
    }
}
