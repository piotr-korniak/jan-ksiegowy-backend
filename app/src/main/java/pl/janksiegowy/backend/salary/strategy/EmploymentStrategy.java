package pl.janksiegowy.backend.salary.strategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.contract.Contract;
import pl.janksiegowy.backend.salary.contract.ContractType;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;
import pl.janksiegowy.backend.shared.indicator.IndicatorsProperties;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
public abstract class EmploymentStrategy {

    private final PayslipQueryRepository payslips;
    private final IndicatorsProperties indicators;

    @Component
    public static class Employment_2022_10 extends EmploymentStrategy
            implements SalaryStrategy<Contract, Interpreter, PayslipDto> {

        @Override public boolean isApplicable( ContractType contractType) {
            return ContractType.E== contractType;
        }

        @Override
        public PayslipDto factory(Contract contract, Period period, Interpreter calculation) {
            return factoryPayslip( contract, period, calculation);
        }

        @Override public Interpreter calculate( Contract contract, Period period) {
            System.err.println( "Calculating employment strategy: Employment_2022_10" );
            return calculateSalary( contract, period);
        }



        @Override public LocalDate getStartDate() {
            return LocalDate.of(2022, 10, 1);
        }

        public Employment_2022_10( final PayslipQueryRepository payslips,
                                   final IndicatorsProperties indicatorsProperties) {
            super( payslips, indicatorsProperties);
        }
    }

    protected Interpreter calculateSalary( Contract contract, Period period) {

        var inter = config(period.getBegin());
        inter.setVariable("brutto", contract.getSalary());
        System.err.println("Wynagrodzenie brutto:     " + inter.getVariable("brutto"));

        inter.interpret("emerytalne", "[brutto]* [emerytalne]");
        inter.interpret("rentowa", "[brutto]* [rentowa]");
        inter.interpret("chorobowe", "[brutto]* [chorobowe]");
        inter.interpret("insuranceEmployee", "[emerytalne]+ [rentowa]+ [chorobowe]");
        inter.interpret("podstawa", "[brutto]- [insuranceEmployee]- [koszty]");
        inter.setVariable("JEDEN", BigDecimal.ONE)
                .interpret("podstawa", "[podstawa]@ [JEDEN]")
                .interpret("zaliczka", "[podstawa]* [dochodowy]- [wolna]");
        inter.interpret("zdrowotne", "[brutto]-[insuranceEmployee]* [zdrowotne]");
        inter.interpret("odliczenie", "[brutto]-[insuranceEmployee]* [odliczenie]");
        inter.interpret("rentowy", "[brutto]* [rentowy]");
        inter.interpret("wypadkowe", "[brutto]* [wypadkowe]");
        inter.interpret("FPFS", "[brutto]* [FPFS]");
        inter.interpret("FGSP", "[brutto]* [FGSP]");

        System.err.println("Podstawa:  " + inter.getVariable("podstawa"));
        System.err.println("Dochodowy:  " + inter.getVariable("dochodowy"));
        System.err.println("Zaliczka przed:  " + inter.getVariable("zaliczka"));

        if (inter.getVariable("zaliczka").signum() == -1)
            inter.setVariable("zaliczka", BigDecimal.ZERO);

        if (inter.getVariable("odliczenie").compareTo(inter.getVariable("zaliczka")) > 0) {
            inter.setVariable("zdrowotne", inter.getVariable("zaliczka"));
            inter.setVariable("zaliczka", BigDecimal.ZERO);
        } else {
            inter.setVariable("zaliczka",
                    inter.getVariable("zaliczka").subtract(inter.getVariable("odliczenie")));
        }
        inter.interpret("insuranceEmployer", "[emerytalne]+ [rentowy]+ [wypadkowe]+ [FPFS]+ [FGSP]");
        inter.interpret("netto", "[brutto]-insuranceEmployee]-[zdrowotne]");

        System.err.println("Podstawa:  " + inter.getVariable("podstawa"));
        System.err.println("Zaliczka:  " + inter.getVariable("zaliczka"));
        System.err.println("Do odliczenia:  " + inter.getVariable("odliczenie"));

        System.err.println("Ubezpieczenie pracownika: " + inter.getVariable("insuranceEmployee"));
        System.err.println("Ubezpieczenie zdrowotne:  " + inter.getVariable("zdrowotne"));
        System.err.println("Ubezpieczenie pracodawcy: " + inter.getVariable("insuranceEmployer"));
        System.err.println("Wypłata pracownika:       " + inter.getVariable("netto"));

        return inter;
    }

    protected PayslipDto factoryPayslip( Contract contract, Period period, Interpreter calculation) {
        return payslips.findByContractIdAndPeriod( contract.getContractId(), period)
                .map( payslipId-> PayslipDto.create().documentId( payslipId))
                .orElseGet( PayslipDto::create)
                    .date( period.getBegin())
                    .dueDate( period.getEnd().plusDays( 10))
                    .type( ContractType.E)
                    .amount( calculation.getVariable("netto"))
                    .entityId( contract.getEntity().getEntityId())
                    .contractId( contract.getContractId())
                .elements( Map.ofEntries(
                        Map.entry( WageIndicatorCode.UE_ZAT, calculation.getVariable( "emerytalne")),
                        Map.entry( WageIndicatorCode.UR_ZAT, calculation.getVariable("rentowa")),
                        Map.entry( WageIndicatorCode.UC_ZAT, calculation.getVariable( "chorobowe")),
                        Map.entry( WageIndicatorCode.UB_ZDR, calculation.getVariable("zdrowotne")),
                        Map.entry( WageIndicatorCode.UE_PRA, calculation.getVariable( "emerytalne")),
                        Map.entry( WageIndicatorCode.UR_PRA, calculation.getVariable( "rentowy")),
                        Map.entry( WageIndicatorCode.UW_PRA, calculation.getVariable( "wypadkowe")),
                        Map.entry( WageIndicatorCode.F_FPFS, calculation.getVariable( "FPFS")),
                        Map.entry( WageIndicatorCode.F_FGSP, calculation.getVariable( "FGSP")),
                        Map.entry( WageIndicatorCode.KW_BRT, calculation.getVariable("brutto")),
                        Map.entry( WageIndicatorCode.KW_NET, calculation.getVariable("netto"))
                ));
    }

    private Interpreter config( LocalDate date) {
        return new Interpreter()
                .setVariable( "koszty", indicators.getWageIndicator( WageIndicatorCode.KO_UZP, date))
                .setVariable( "wolna", indicators.getWageIndicator( WageIndicatorCode.KW_WLN, date))
                .setVariable( "emerytalne", indicators.getWageIndicator( WageIndicatorCode.UE_ZAT, date))
                .setVariable( "rentowa", new BigDecimal( "0.015"))
                .setVariable( "chorobowe", new BigDecimal( "0.0245"))
                .setVariable( "zdrowotne", new BigDecimal( "0.09"))
                .setVariable( "odliczenie", new BigDecimal( "0.0775"))
                .setVariable( "rentowy", new BigDecimal( "0.065"))
                .setVariable( "wypadkowe", new BigDecimal( "0.0167"))
                .setVariable( "dochodowy", indicators.getWageIndicator( WageIndicatorCode.ST_PDZ, date))
                //.setVariable( "FPFS", new BigDecimal( "0.0245"))
                .setVariable( "FPFS", BigDecimal.ZERO)
                .setVariable( "FGSP", new BigDecimal( "0.001" ));
                //.setVariable( "FGSP", BigDecimal.ZERO);
    }

}
