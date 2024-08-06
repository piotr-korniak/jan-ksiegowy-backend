package pl.janksiegowy.backend.salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollCalculationService {

    private final List<SalaryStrategy> strategies;

    @Autowired
    public PayrollCalculationService( List<SalaryStrategy> strategies) {
        this.strategies= strategies;
    }


}
