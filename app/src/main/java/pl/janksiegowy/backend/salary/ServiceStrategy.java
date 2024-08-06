package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.PeriodQueryRepository;
import pl.janksiegowy.backend.salary.dto.ContractDto;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.dto.PayslipLineDto;
import pl.janksiegowy.backend.salary.dto.PayslipMap;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ServiceStrategy implements SalaryStrategy {

    private final PeriodQueryRepository periodQueryRepository;
    private final PayslipQueryRepository payslips;

    @Override
    public boolean isApplicable( ContractType contractType) {
        return contractType== ContractType.S;
    }

    @Override
    public PayslipDto calculateSalary( ContractDto contract, Period period) {
        System.err.println( "Liczymy zlecenie!!!");
        var inter= config();
        inter.setVariable( "brutto", contract.getSalary());
        inter.interpret( "koszty", "[brutto]* [koszty]");
        inter.interpret( "dochod", "[brutto]- [koszty]");
        inter.interpret( "podatek", "[dochod]* [podatek]");
        inter.interpret( "netto", "[brutto]- [podatek]");

        System.err.println( "Netto: "+ inter.getVariable("netto"));
        System.err.println( "Podatek: "+ inter.getVariable("podatek"));

        var payslip= payslips.findByContractIdAndPeriod( contract.getContractId(), period)
                .map( payslipId-> PayslipDto.create().documentId( payslipId))
                .orElseGet( PayslipDto::create);

        System.err.println( "PayslipDto: "+payslip.getDocumentId());

        return PayslipMap.create(
                        payslips.findByContractIdAndPeriod( contract.getContractId(), period)
                                .map( payslipId-> PayslipDto.create().documentId( payslipId))
                                .orElseGet( PayslipDto::create)
                        .date( period.getBegin())
                        .dueDate( period.getEnd().plusDays( 10))
                        .amount( inter.getVariable("netto"))
                        .entity( contract.getEntity())
                        .contract( contract))
                .addLine( PayslipLineDto.create()
                        .itemCode( PayslipItemCode.TAX_ZA)
                        .amount( inter.getVariable("podatek")))
                .addLine( PayslipLineDto.create()
                        .itemCode( PayslipItemCode.KW_BRT)
                        .amount( inter.getVariable("brutto")))
                .addLine( PayslipLineDto.create()
                        .itemCode( PayslipItemCode.KW_NET)
                        .amount( inter.getVariable("netto")));
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
