package pl.janksiegowy.backend.entity;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.entity.EntityType.*;

import java.time.LocalDate;
import java.util.UUID;

public class EntityFactory implements EntityTypeVisitor<Entity> {
    public Entity from( EntityDto source) {     // New Entity
        return update(  source.getType()
                .accept( this)
                    .setEntityId( UUID.randomUUID())
                    .setDate( LocalDate.EPOCH.plusDays( 3)),
                source);
    }

public Entity update( EntityDto source) {       // New Entity history
        return update(  source.getType()
                .accept( this)
                    .setEntityId( source.getEntityId())
                    .setDate( source.getDate()),
                source);
    }

    public Entity update( Entity entity, EntityDto source) {
        return entity                           // Update Entity history
                .setName( source.getName())
                .setType( source.getType())
                .setTaxNumber( source.getTaxNumber())
                .setCountry( source.getCountry())
                .setAddress( source.getAddress())
                .setTown( source.getTown())
                .setCustomer( source.isCustomer())
                .setSupplier( source.isSupplier());
    }

    @Override public Entity visitContact() {
        return new Contact();
    }

    @Override public Entity visitRevenue() {
        return null;
    }

    @Override public Entity visitShareholders() {
        return null;
    }


}
