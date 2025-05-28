package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.entity.EntityType.*;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
@Log4j2
@AllArgsConstructor
public class EntityFactory implements EntityTypeVisitor<Entity> {

    private final NumeratorFacade numerators;

    public Entity from( EntityDto source) {     // New Entity
        return update( source, source.getType().accept( this)
                .setEntityId( Optional.ofNullable( source.getEntityId()).orElseGet( UUID::randomUUID))
                .setAccountNumber( Optional.ofNullable( source.getAccountNumber()).orElseGet(()->
                        EntityType.R == source.getType()
                                ? source.getTaxNumber()
                                : numerators.increment( NumeratorCode.EN, EntityType.B== source.getType() ?
                                EntityType.C.name(): source.getType().name())))
                .setDate( Optional.of( source.getDate() ).orElseGet(()-> LocalDate.EPOCH))
        );
    }

    public Entity update( EntityDto source, Entity entity) {
        return entity                           // Update Entity history
                .setName( source.getName())
                .setTaxNumber( source.getTaxNumber())
                .setCountry( source.getCountry())
                .setAddress( source.getAddress())
                .setPostalCode( source.getPostalCode())
                .setCity( source.getCity())
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
