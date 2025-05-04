package pl.janksiegowy.backend.salary.strategy;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.contract.ContractType;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;
import pl.janksiegowy.backend.shared.indicator.IndicatorsProperties;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Component
public class ServiceStrategy extends EmploymentStrategy
        implements SalaryStrategy<Contract, Interpreter, PayslipDto> {

    private final PayslipQueryRepository payslips;

    public ServiceStrategy( PayslipQueryRepository payslips, IndicatorsProperties indicators) {
        super( payslips, indicators);
        this.payslips = payslips;
    }

    @Override
    public boolean isApplicable( ContractType contractType) {
        return ContractType.W== contractType;
    }


    @Override
    public PayslipDto factory( Contract contract, Period period, Interpreter calculation) {
        return payslips.findByContractIdAndPeriod( contract.getContractId(), period)
                .map( payslipDto-> PayslipDto.create()
                        .documentId( payslipDto.getDocumentId())
                        .number( payslipDto.getNumber()))
                .orElseGet( PayslipDto::create)
                .date( period.getBegin())
                .dueDate( period.getEnd().plusDays( 10))
                .contractType( ContractType.W)
                .amount( calculation.getVariable("netto"))
                .entityId( contract.getEntity().getEntityId())
                .contractId( contract.getContractId())
                .elements( Map.ofEntries(
                        Map.entry( WageIndicatorCode.KW_BRT, calculation.getVariable("brutto")),
                        Map.entry( WageIndicatorCode.KW_NET, calculation.getVariable("netto")),
                        Map.entry( WageIndicatorCode.KW_ZAL, calculation.getVariable("podatek"))
                ));
    }

    @Override public Interpreter calculate( Contract contract, Period period) {
        return config()
                .setVariable( "brutto", contract.getSalary())
                .interpret( "koszty", "[brutto]* [koszty]")
                .interpret( "dochod", "[brutto]- [koszty]")
                .interpret( "podatek", "[dochod]* [podatek]")
                .interpret( "netto", "[brutto]- [podatek]");
    }

    @Override
    public LocalDate getStartDate() {
        return LocalDate.of( 1970, 1, 4);
    }

    private Interpreter config() {
        return new Interpreter()
                .setVariable( "koszty", new BigDecimal( "0.5"))
                .setVariable( "podatek", new BigDecimal( "0.18"));
    }

}
