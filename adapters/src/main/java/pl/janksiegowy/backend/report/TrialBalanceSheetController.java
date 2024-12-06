package pl.janksiegowy.backend.report;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@TenantController
@RequestMapping( "/v2/trial-balance-sheet/{beginDate}/{endDate}")
@AllArgsConstructor
public class TrialBalanceSheetController {

    private final TrialBalanceSheet trialBalanceSheet;

    @GetMapping
    public ResponseEntity getReport(
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @PathVariable @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) LocalDate endDate) {

        System.err.println( "beginDate: " + beginDate);
        System.err.println( "endDate: " + endDate);

        var header = Arrays.asList( "Konto", "Nazwa",
                "Początkowe Dt", "Początkowe Ct", "Obroty Dt", "Obroty Ct",
                "Narastające Ct", "Narastające Dt", "Końcowe Dt", "Końcowe Ct");

        var data= trialBalanceSheet.printTrialBalanceSheet( beginDate, endDate).stream()
                .map(dto -> Arrays.asList(
                        dto.getNumber(),
                        dto.getName(),
                        dto.getOpeningDebitBalance(),
                        dto.getOpeningCreditBalance(),
                        dto.getDebitTurnover(),
                        dto.getCreditTurnover(),
                        dto.getCumulativeDebitTurnover(),
                        dto.getCumulativeCreditTurnover(),
                        dto.getClosingDebitBalance(),
                        dto.getClosingCreditBalance()
                )).toList();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("header", header);  // Nagłówki jako pierwszy element
        result.put("data", data);      // Dane jako drugi element

        return  ResponseEntity.ok( result);
    }
}
