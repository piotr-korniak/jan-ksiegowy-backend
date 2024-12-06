package pl.janksiegowy.backend.report;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeBalanceDto;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TrialBalanceSheet {

    private final DecreeLineQueryRepository lines;

    public List<DecreeBalanceDto> printTrialBalanceSheet(LocalDate startDate, LocalDate endDate) {
        return lines.sum( startDate, endDate);
    }
}
