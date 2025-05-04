package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.dto.PayslipDto;
import pl.janksiegowy.backend.salary.payslip.*;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SalaryFactory  {

    private final PeriodRepository periods;
    private final EntityRepository entities;
    protected final NumeratorFacade numerators;

    Payslip from( PayslipDto source) {

        return periods.findMonthByDate( source.getDate())
            .map( period-> entities.findByEntityIdAndDate( source.getEntityEntityId(), period.getBegin())
                    .map( entity-> (Payslip) new Payslip()

                        .setElements( source.getElements().entrySet().stream()
                                    .filter(entry-> entry.getValue().signum()!= 0)
                                    .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue)))
                        .setContractId( source.getContractContractId())
                        .setDates( source.getDate(), source.getDueDate())
                        .setNumber( Optional.ofNullable( source.getNumber())
                                .orElseGet(()-> numerators.increment( source.getContractType()
                                                .accept( PayslipNumeratorResolver.INSTANCE), source.getDate())))
                        .setEntity( entity)
                        .setPeriod( period)
                        //.setSettlementPeriod( period)
                        .setAmount( source.getAmount())
                        //.setPayslipId

                            .setDocumentId( Optional.ofNullable( source.getDocumentId())
                                    .orElseGet( UUID::randomUUID))
                    ).orElseThrow())
                .orElseThrow();
    }

}
