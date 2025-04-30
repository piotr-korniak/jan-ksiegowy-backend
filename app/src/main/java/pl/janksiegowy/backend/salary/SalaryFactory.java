package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.contract.ContractType.ContractTypeVisitor;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.*;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SalaryFactory implements ContractTypeVisitor<PayrollPayslip> {

    private final PeriodRepository periods;
    private final EntityRepository entities;
    protected final NumeratorFacade numerators;

    PayrollPayslip from(PayslipDto source) {

        return periods.findMonthByDate( source.getDate())
            .map( period-> entities.findByEntityIdAndDate( source.getEntityEntityId(), period.getBegin())
                    .map( entity-> source.getType().accept( this)
                        .setContractId( source.getContractContractId())
                        .setDates( source.getDate(), source.getDueDate())
                        .setNumber( Optional.ofNullable( source.getNumber())
                                .orElseGet(()-> numerators.increment( source.getType()
                                                .accept( PayslipNumeratorResolver.INSTANCE), source.getDate())))
                        .setEntity( entity)
                        .setSettlementPeriod( period)
                        .setAmount( source.getAmount())
                        .setPayslipId( Optional.ofNullable( source.getPayslipId())
                                .orElseGet( UUID::randomUUID))
                        .setElements( source.getElements().entrySet().stream()
                                .filter(entry-> entry.getValue().signum()!= 0)
                                .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue)))
                    ).orElseThrow())
                .orElseThrow();
    }

    @Override public PayrollPayslip visitEmploymentContract() {
        return new EmploymentPayslip();
    }

    @Override public PayrollPayslip visitMandateContract() {
        return new MandatePayslip();
    }

    @Override public PayrollPayslip visitWorkContract() {
        return new WorkPayslip();
    }
}
