package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.shared.MigrationService;

import java.time.LocalDate;
import java.util.Optional;
@Log4j2

@AllArgsConstructor
public class EntityFacade {

    private final EntityFactory factory;
    private final EntityRepository repository;
    private final EntityQueryRepository entities;
    private final MigrationService migrationService;

    public Entity save( EntityDto source) {
        return repository.save( Optional.ofNullable( source.getEntityId())
                .map( entityId-> repository.findEntityByEntityIdAndDate( entityId, source.getDate())
                        .map( entity-> factory.update( source, entity))   // Update Entity history
                        .orElse( factory.from( source)))                        // New Entity history
                .orElse( factory.from( source))                                 // New Entity
        );
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadEntity().forEach(entity -> {
            counters[0]++;

            var date= Optional.ofNullable( entity.getDate()).orElseGet(()-> LocalDate.EPOCH);
            var existingEntity= entities.findByCountryAndTaxNumberAndDateAndTypeIn(
                    entity.getCountry(), entity.getTaxNumber(), date, entity.getType());

            if( existingEntity.map(existing-> !existing.getDate().equals( date)).orElse(true)) {

                if( entity instanceof EntityDto.Proxy entityDto)
                    repository.save( factory.from(
                            entityDto.applyIf( existingEntity.isPresent(), dto-> dto
                                    .entityId( existingEntity.get().getEntityId())
                                    .accountNumber( existingEntity.get().getAccountNumber()))
                ));
                counters[1]++;
            }
        });

        log.warn( "Entities migration complete!");
        return "%-40s %16s".formatted( "Entities migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }
}
