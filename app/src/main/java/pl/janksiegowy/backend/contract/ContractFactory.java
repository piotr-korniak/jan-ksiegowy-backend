package pl.janksiegowy.backend.contract;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.salary.Contract;
import pl.janksiegowy.backend.salary.ContractType.ContractTypeVisitor;
import pl.janksiegowy.backend.salary.EmploymentContract;
import pl.janksiegowy.backend.salary.ServiceContract;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ContractFactory implements ContractTypeVisitor<Contract>{

    private final EntityRepository entityRepository;

    public Contract from( ContractDto source) {
        return entityRepository.findByEntityIdAndDate( source.getEntity().getEntityId(), source.getBegin())
                .map( entity-> source.getType().accept( this )
                        .setNumber( source.getNumber())
                        .setBegin( source.getBegin())
                        .setEnd( source.getEnd())
                        .setSalary( source.getSalary())
                        .setContractId( Optional.ofNullable( source.getContractId())
                                .orElseGet( UUID::randomUUID))
                        .setEntity( entity))
                .orElseThrow(()-> new NoSuchElementException(
                        "No Employee with tax number: "+ source.getEntity().getTaxNumber()));
    }

    @Override public Contract visitEmploymentContract() {
        return new EmploymentContract();
    }

    @Override public Contract visitServicesContract() {
        return new ServiceContract();
    }
}
