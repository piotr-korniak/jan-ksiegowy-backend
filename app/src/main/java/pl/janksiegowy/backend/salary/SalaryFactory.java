package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.settlement.PayslipSettlement;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.dto.PayslipDto;

import java.util.UUID;

@AllArgsConstructor
public class SalaryFactory {

    private final PeriodRepository periods;
    private final EntityRepository entities;

    Payslip from( PayslipDto source) {

        return periods.findMonthByDate( source.getDate())
                .map( period-> entities.
                        findByEntityIdAndDate( source.getEntity().getEntityId(), period.getBegin())
                        .map( entity-> update( source, new Payslip()
                                ))
                        .orElseThrow())
                .orElseThrow();
    }

    Payslip update( PayslipDto source, Payslip payslip) {
        return payslip.setGross( source.getGross())
                        .setDate( source.getDate())
                        .setNumber( source.getNumber())
                        .setInsuranceEmployee( source.getInsuranceEmployee())
                        .setInsuranceEmployer( source.getInsuranceEmployer())
                        .setInsuranceHealth( source.getInsuranceHealth())
                        .setTaxAdvance( source.getTaxAdvance())
                        .setPayable( (source.getGross().subtract( source.getInsuranceEmployee())
                                        .subtract( source.getInsuranceHealth())));
    }
}
