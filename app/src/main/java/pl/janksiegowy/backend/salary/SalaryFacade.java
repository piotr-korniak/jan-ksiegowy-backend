package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.strategy.SalaryStrategy;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class SalaryFacade {

    private final PayslipRepository payslips;
    private final SalaryFactory payslip;
    private final DecreeFacade decree;
    private final List<SalaryStrategy> strategies;


    public Payslip save( PayslipDto source) {
        return payslips.save( payslip.from( source));
        //return payslip.from( source);
    }

    public Payslip approval( Payslip payslip) {
        System.err.println( "Księgujemy płace!!!");
        decree.book( payslip);
        return payslip;
    }

    public Payslip calculatePayslip( ContractDto contract, Period period) {
        SalaryStrategy strategy = strategies.stream()
                .filter( s-> s.isApplicable( contract.getType())&&
                             !period.getEnd().isBefore( s.getStartDate()))
                .sorted( (s1, s2)-> s2.getStartDate().compareTo( s1.getStartDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException( "Brak strategii dla umowy i daty rozliczenia"));

        return save( strategy.calculateSalary( contract, period));

    }

    Payslip calculate( ContractDto contractDto, LocalDate date) {


        return save( PayslipDto.create()
                .number( contractDto.getNumber())
                .entity( contractDto.getEntity())
                .date( date));

    }



}
