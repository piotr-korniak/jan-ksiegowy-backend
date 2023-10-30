package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface SqlEntityRepository extends JpaRepository<Entity, Long> {

    public Optional<Entity> findEntityByEntityIdAndDate( UUID entityId, LocalDate date);
}

@org.springframework.stereotype.Repository
interface SqlEntityQueryRepository extends EntityQueryRepository, Repository<Entity, Long> {

    @Override
    @Query( value= "SELECT M " +
            "FROM Entity M " +
            "LEFT OUTER JOIN Contact P "+
            "ON M.entityId= P.entityId AND M.date < P.date "+
            "WHERE M.taxNumber= :taxNumber AND M.country= :country AND M.type= :type AND P.date IS NULL")
    Optional<EntityDto> findByCountryAndTypeAndTaxNumber( Country country, EntityType type, String taxNumber);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class EntityRepositoryImpl implements EntityRepository {

    private final SqlEntityRepository repository;

    @Override
    public Entity save( Entity entity) {
        return repository.save( entity);
    }

    @Override
    public Optional<Entity> findEntityByEntityIdAndDate(UUID entityId, LocalDate date) {
        return repository.findEntityByEntityIdAndDate( entityId, date);
    }
}