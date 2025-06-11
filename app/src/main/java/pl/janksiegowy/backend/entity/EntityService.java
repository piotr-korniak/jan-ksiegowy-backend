package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EntityService {

    private final EntityFactory factory;
    private final EntityRepository repository;
    private final EntityQueryRepository entities;

    public EntityService( NumeratorFacade numeratorFacade,
                          EntityRepository repository,
                          EntityQueryRepository entities) {
        this.factory= new EntityFactory( numeratorFacade);
        this.repository= repository;
        this.entities= entities;
    }

    public Entity create( EntityDto source) {

        var date= Optional.ofNullable( source.getDate()).orElseGet(()-> LocalDate.EPOCH);
        var existingEntity= entities.findByCountryAndTaxNumberAndDateAndTypeIn(
                source.getCountry(), source.getTaxNumber(), date, source.getType());

        existingEntity
                .filter(e-> e.getDate().equals( date))
                .ifPresent(e-> {
                    throw new EntityAlreadyExistsException(
                            "Entity %s already exists".formatted(e.getName()));
                });

        existingEntity.ifPresent( entityDto-> {
            if( source instanceof EntityDto.Proxy proxy) {
                proxy.accountNumber( entityDto.getAccountNumber());
            }
        });

        return repository.save( factory.from( source));
    }
}
