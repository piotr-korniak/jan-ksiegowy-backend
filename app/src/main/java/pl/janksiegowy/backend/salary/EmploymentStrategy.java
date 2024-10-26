package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.period.Period;
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
public class EmploymentStrategy implements SalaryStrategy {

    private final PayslipQueryRepository payslips;
    private final WageIndicatorService wageIndicatorService;

    @Override
    public boolean isApplicable( ContractType contractType) {
        return contractType== ContractType.E;
    }

    @Override
    public PayslipDto calculateSalary( ContractDto contract, Period period) {

        var inter= config( period.getBegin());
        inter.setVariable( "brutto", contract.getSalary());
        System.err.println( "Wynagrodzenie brutto:     "+ inter.getVariable( "brutto"));
        inter.interpret( "emerytalne", "[brutto]* [emerytalne]");
        inter.interpret( "rentowa", "[brutto]* [rentowa]");
        inter.interpret( "chorobowe", "[brutto]* [chorobowe]");
        inter.interpret( "insuranceEmployee", "[emerytalne]+ [rentowa]+ [chorobowe]");
        inter.interpret( "podstawa", "[brutto]- [insuranceEmployee]- [koszty]");
        inter.setVariable( "JEDEN", BigDecimal.ONE)
                .interpret( "podstawa", "[podstawa]@ [JEDEN]" )
                .interpret( "zaliczka", "[podstawa]* [dochodowy]- [wolna]");
        inter.interpret( "zdrowotne", "[brutto]-[insuranceEmployee]* [zdrowotne]");
        inter.interpret( "odliczenie", "[brutto]-[insuranceEmployee]* [odliczenie]");
        inter.interpret( "rentowy", "[brutto]* [rentowy]");
        inter.interpret( "wypadkowe", "[brutto]* [wypadkowe]");
        inter.interpret(  "FPFS", "[brutto]* [FPFS]");
        inter.interpret(  "FGSP", "[brutto]* [FGSP]");

        System.err.println( "Podstawa:  "+ inter.getVariable( "podstawa"));
        System.err.println( "Dochodowy:  "+ inter.getVariable( "dochodowy"));
        System.err.println( "Zaliczka przed:  "+ inter.getVariable( "zaliczka"));

        if( inter.getVariable( "zaliczka").signum()==-1)
            inter.setVariable( "zaliczka", BigDecimal.ZERO);

        if( inter.getVariable( "odliczenie").compareTo(  inter.getVariable( "zaliczka"))>0) {
            inter.setVariable( "zdrowotne", inter.getVariable( "zaliczka"));
            inter.setVariable( "zaliczka", BigDecimal.ZERO);
        }else {
            inter.setVariable( "zaliczka",
                    inter.getVariable( "zaliczka").subtract( inter.getVariable( "odliczenie")));
        }
        inter.interpret( "insuranceEmployer", "[emerytalne]+ [rentowy]+ [wypadkowe]+ [FPFS]+ [FGSP]");
        inter.interpret( "netto", "[brutto]-insuranceEmployee]-[zdrowotne]");

        System.err.println( "Podstawa:  "+ inter.getVariable( "podstawa"));
        System.err.println( "Zaliczka:  "+ inter.getVariable( "zaliczka"));
        System.err.println( "Do odliczenia:  "+ inter.getVariable( "odliczenie"));

        System.err.println( "Ubezpieczenie pracownika: "+ inter.getVariable( "insuranceEmployee"));
        System.err.println( "Ubezpieczenie zdrowotne:  "+ inter.getVariable( "zdrowotne"));
        System.err.println( "Ubezpieczenie pracodawcy: "+ inter.getVariable( "insuranceEmployer"));
        System.err.println( "Wypłata pracownika:       "+ inter.getVariable( "netto"));

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
                        .itemCode( PayslipItemCode.UB_PRA)
                        .amount( inter.getVariable("insuranceEmployer", BigDecimal.ZERO)))
                .addLine( PayslipLineDto.create()
                        .itemCode( PayslipItemCode.UB_ZDR)
                        .amount( inter.getVariable("zdrowotne")))
                .addLine( PayslipLineDto.create()
                        .itemCode( PayslipItemCode.UB_ZAT)
                        .amount( inter.getVariable("insuranceEmployee", BigDecimal.ZERO)))
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

    private Interpreter config( LocalDate date) {
        return new Interpreter()
                .setVariable( "koszty", getWageIndicatorValue( WageIndicatorType.KO_UZP, date))
                .setVariable( "wolna", getWageIndicatorValue( WageIndicatorType.KW_WLN, date))
                .setVariable( "emerytalne", new BigDecimal( "0.0976"))
                .setVariable( "rentowa", new BigDecimal( "0.015"))
                .setVariable( "chorobowe", new BigDecimal( "0.0245"))
                .setVariable( "zdrowotne", new BigDecimal( "0.09"))
                .setVariable( "odliczenie", new BigDecimal( "0.0775"))
                .setVariable( "rentowy", new BigDecimal( "0.065"))
                .setVariable( "wypadkowe", new BigDecimal( "0.0167"))
                .setVariable( "dochodowy", getWageIndicatorValue( WageIndicatorType.ST_PDZ, date))
                //.setVariable( "FPFS", new BigDecimal( "0.0245"))
                .setVariable( "FPFS", BigDecimal.ZERO)
                .setVariable( "FGSP", new BigDecimal( "0.001" ));
                //.setVariable( "FGSP", BigDecimal.ZERO);
    }

    private BigDecimal getWageIndicatorValue(WageIndicatorType code, LocalDate calculationDate) {
        return wageIndicatorService.getWageIndicator(code, calculationDate)
                .map(WageIndicatorService.WageIndicator::getValue)
                .orElse(BigDecimal.ZERO);
    }
/*
    public static BigDecimal obliczKwoteZmniejszajacaPodatek( BigDecimal podstawa) {
        // Deklaracja wartości stałych jako BigDecimal
        BigDecimal kwota1 = new BigDecimal("1440.00");
        BigDecimal kwota2 = new BigDecimal("883.98");
        BigDecimal kwota3 = new BigDecimal("556.02");
        BigDecimal granica1 = new BigDecimal("8000");
        BigDecimal granica2 = new BigDecimal("13000");
        BigDecimal granica3 = new BigDecimal("85528");
        BigDecimal granica4 = new BigDecimal("127000");
        BigDecimal przedzial2 = new BigDecimal("5000");
        BigDecimal przedzial4 = new BigDecimal("41472");

        if( podstawa.compareTo(granica1) <= 0) {
            return kwota1;
        }

        if (podstawa.compareTo(granica2) <= 0) {
            // (1440 - (883,98 × (podstawa - 8000) ÷ 5000))
            return kwota1.subtract(kwota2.multiply(podstawa.subtract(granica1))
                    .divide(przedzial2, 2, RoundingMode.HALF_UP));
        }

        if (podstawa.compareTo(granica3) <= 0) {
            return kwota3;
        }

        if (podstawa.compareTo(granica4) <= 0) {
            // (556,02 - (556,02 × (podstawa - 85528) ÷ 41472))
            return kwota3.subtract(kwota3.multiply(podstawa.subtract(granica3))
                    .divide(przedzial4, 2, RoundingMode.HALF_UP));
        }

        return BigDecimal.ZERO; // Jeśli podstawa przekracza 127000
    }
*/
}
