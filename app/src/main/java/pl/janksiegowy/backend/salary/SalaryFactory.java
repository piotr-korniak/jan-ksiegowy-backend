package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.salary.dto.PayslipDto;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SalaryFactory {

    private final PeriodRepository periods;
    private final EntityRepository entities;
    private final PayslipLineFactory line;

    Payslip from( PayslipDto source) {

        return periods.findMonthByDate( source.getDate())
            .map( period-> entities.findByEntityIdAndDate( source.getEntity().getEntityId(), period.getBegin())
                    .map( entity-> (Payslip) new Payslip()
                        //.setGross( source.getAmount())
                        .setContractId( source.getContract().getContractId())
                        .setDates( source.getDate(), source.getDueDate())
                        .setNumber( source.getNumber())
                        .setEntity( entity)
                        .setPeriod( period)
                        .setAmount( source.getAmount())
                        .setDocumentId( Optional.ofNullable( source.getDocumentId())
                                .orElseGet( UUID::randomUUID)))
                        .map( payslip-> Optional.ofNullable( source.getLines())
                                .map( lines-> payslip.setLines( lines.stream()
                                        .map( statementLineDto -> line.from( statementLineDto).setPayslip( payslip))
                                                .collect( Collectors.toList())))
                                        .orElseGet( ()-> payslip))
                        .orElseThrow())
                .orElseThrow();
    }

}
