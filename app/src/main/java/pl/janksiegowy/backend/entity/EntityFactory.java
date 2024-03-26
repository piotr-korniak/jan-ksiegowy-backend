package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.entity.EntityType.*;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.time.LocalDate;
import java.util.UUID;
@Log4j2
@AllArgsConstructor
public class EntityFactory implements EntityTypeVisitor<Entity> {

    private final NumeratorFacade numerators;

    public Entity from( EntityDto source) {     // New Entity
        return update( source, source.getType()
                .accept( this)
                    .setEntityId( UUID.randomUUID())
                    .setAccountNumber( EntityType.R== source.getType()?
                            source.getTaxNumber(): numerators.increment( NumeratorCode.EN,
                            EntityType.B== source.getType()?
                                    EntityType.C.name(): source.getType().name()))
                    .setDate( LocalDate.EPOCH.plusDays( 3)));
    }

    public Entity update( EntityDto source) {   // New Entity history
        return update( source, source.getType()
                .accept( this)
                    .setEntityId( source.getEntityId())
                    .setAccountNumber( source.getAccountNumber())
                    .setDate( source.getDate()));
    }

    public Entity update( EntityDto source, Entity entity) {
        return entity                           // Update Entity history
                .setName( source.getName())
                .setType( source.getType())
                .setTaxNumber( source.getTaxNumber())
                .setCountry( source.getCountry())
                .setAddress( source.getAddress())
                .setPostcode( source.getPostcode())
                .setTown( source.getTown())
                .setCustomer( source.isCustomer())
                .setSupplier( source.isSupplier());
    }

    @Override public Entity visitContact() {
        return new Contact();
    }

    @Override public Entity visitRevenue() {
        return new Revenue();
    }

    @Override public Entity visitShareholders() {
        return new Shareholders();
    }

    @Override public Entity visitEmployee() {
        return new Employee();
    }

    @Override public Entity visitBank() {
        return new Bank();
    }


}
