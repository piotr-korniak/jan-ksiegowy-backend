package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.salary.dto.ContractDto;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
public class SalaryFacade {

    private final PayslipRepository payslips;
    private final SalaryFactory payslip;
    private final DecreeFacade decree;


    public Payslip save( PayslipDto source) {
        return payslips.save( payslip.from( source));
    }

    public Payslip approval( Payslip payslip) {
        decree.book( payslip);
        return payslip;
    }

    Payslip calculate( ContractDto contractDto, LocalDate date) {

        var inter= config();
        inter.setVariable( "brutto", contractDto.getSalary());
        System.err.println( "Wynagrodzenie brutto:     "+ inter.getVariable( "brutto"));
        inter.interpret( "emerytalne", "[brutto]* [emerytalne]");
        inter.interpret( "rentowa", "[brutto]* [rentowa]");
        inter.interpret( "chorobowe", "[brutto]* [chorobowe]");
        inter.interpret( "insuranceEmployee", "[emerytalne]+ [rentowa]+ [chorobowe]");
        inter.interpret( "insuranceHealth", "[brutto]-[insuranceEmployee]* [zdrowotne]");
        inter.interpret( "rentowy", "[brutto]* [rentowy]");
        inter.interpret( "wypadkowe", "[brutto]* [wypadkowe]");
        inter.interpret(  "FPFS", "[brutto]* [FPFS]");
        inter.interpret(  "FGSP", "[brutto]* [FGSP]");
        inter.interpret( "insuranceEmployer", "[emerytalne]+ [rentowy]+ [wypadkowe]+ [FPFS]+ [FGSP]");
        inter.interpret( "do_wyplaty", "[brutto]-insuranceEmployee]-[insuranceHealth]");
        

        System.err.println( "Ubezpieczenie pracownika:  "+ inter.getVariable( "insuranceEmployee"));
        System.err.println( "Ubezpieczenie zdrowotne:   "+ inter.getVariable( "insuranceHealth"));
        System.err.println( "Ubezpieczenie pracodawcy: "+ inter.getVariable( "insuranceEmployer"));
        System.err.println( "Wypłata pracownika:       "+ inter.getVariable( "do_wyplaty"));

        return save( PayslipDto.create()
                .number( contractDto.getNumber())
                .gross( contractDto.getSalary())
                .entity( contractDto.getEntity())
                .date( date)
                .insuranceEmployee( inter.getVariable( "insuranceEmployee"))
                .insuranceEmployer( inter.getVariable( "insuranceEmployer"))
                .insuranceHealth( inter.getVariable( "insuranceHealth"))
                .taxAdvance( BigDecimal.ZERO));

    }

    private Interpreter config() {
        return new Interpreter()
                .setVariable( "emerytalne", new BigDecimal( "0.0976"))
                .setVariable( "rentowa", new BigDecimal( "0.015"))
                .setVariable( "chorobowe", new BigDecimal( "0.0245"))
                .setVariable( "zdrowotne", new BigDecimal( "0.09"))
                .setVariable( "rentowy", new BigDecimal( "0.065"))
                .setVariable( "wypadkowe", new BigDecimal( "0.0167"))
                .setVariable( "FPFS", new BigDecimal( "0.0245"))
                .setVariable( "FGSP", new BigDecimal( "0.001" ));
    }

}
