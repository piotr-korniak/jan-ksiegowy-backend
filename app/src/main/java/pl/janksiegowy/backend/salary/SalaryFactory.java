package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.contract.ContractType.ContractTypeVisitor;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.EmploymentPayslip;
import pl.janksiegowy.backend.salary.payslip.MandatePayslip;
import pl.janksiegowy.backend.salary.payslip.Payslip;
import pl.janksiegowy.backend.salary.payslip.WorkPayslip;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SalaryFactory implements ContractTypeVisitor<Payslip> {

    private final PeriodRepository periods;
    private final EntityRepository entities;

    Payslip from( PayslipDto source) {

        return periods.findMonthByDate( source.getDate())
            .map( period-> entities.findByEntityIdAndDate( source.getEntityEntityId(), period.getBegin())
                    .map( entity-> source.getType().accept( this)
                        .setContractId( source.getContractContractId())
                        .setDates( source.getDate(), source.getDueDate())
                        .setNumber( source.getNumber())
                        .setEntity( entity)
                        .setSettlementPeriod( period)
                        .setAmount( source.getAmount())
                        .setPayslipId( Optional.ofNullable( source.getDocumentId())
                                .orElseGet( UUID::randomUUID))
                        .setElements( source.getElements().entrySet().stream()
                                .filter(entry-> entry.getValue().signum()!= 0)
                                .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue)))
                    ).orElseThrow())
                .orElseThrow();
//        return null;
    }

    @Override public Payslip visitEmploymentContract() {
        return new EmploymentPayslip();
    }

    @Override public Payslip visitMandateContract() {
        return new MandatePayslip();
    }

    @Override public Payslip visitWorkContract() {
        return new WorkPayslip();
    }
}
