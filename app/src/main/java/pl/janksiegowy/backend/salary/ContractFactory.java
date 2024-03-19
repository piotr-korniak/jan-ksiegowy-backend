package pl.janksiegowy.backend.salary;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.salary.dto.ContractDto;
import pl.janksiegowy.backend.salary.ContractType.ContractTypeVisitor;

import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
public class ContractFactory implements ContractTypeVisitor<Contract>{

    private final EntityQueryRepository entities;
    private final EntityRepository entityRepository;

    public Contract from( ContractDto source) {
        return update( source, source.getType().accept( this )
                .setContractId( UUID.randomUUID()));
    }

    public Contract update( ContractDto source) {
        return update( source, source.getType().accept( this )
                .setContractId( source.getContractId()));
    }

    public Contract update( ContractDto source, Contract contract) {
        var taxNumber= source.getEntity().getTaxNumber();
        return entities.findByTypeAndTaxNumber( EntityType.E, taxNumber)
                .map( entityDto-> entityRepository.findByEntityIdAndDate( entityDto.getEntityId(), source.getBegin() )
                        .map( entity-> contract.setEntity( entity)
                                .setNumber( source.getNumber())
                                .setBegin( source.getBegin())
                                .setEnd( source.getEnd())
                                .setSalary( source.getSalary()))
                        .orElseThrow( ()-> new NoSuchElementException( "No Employee with tax number: "+ taxNumber)))
                .orElseThrow( ()-> new NoSuchElementException( "No Employee with tax number: "+ taxNumber));

    }

    @Override public Contract visitEmploymentContract() {
        return new EmploymentContract();
    }

    @Override public Contract visitServicesContract() {
        return new ServiceContract();
    }
}
