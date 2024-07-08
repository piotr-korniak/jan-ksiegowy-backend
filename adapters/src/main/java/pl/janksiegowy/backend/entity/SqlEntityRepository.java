package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlEntityRepository extends JpaRepository<Entity, Long> {

    @Query( value= "FROM Entity M " +
            "LEFT OUTER JOIN Entity P "+
            "ON M.entityId= P.entityId AND (P.date <= :date AND M.date < P.date) "+
            "WHERE M.entityId= :entityId AND M.date <= :date AND P.date IS NULL")
    Optional<Entity> findByEntityIdAndDate( UUID entityId, LocalDate date);

    public Optional<Entity> findEntityByEntityIdAndDate( UUID entityId, LocalDate date);

}

interface SqlEntityQueryRepository extends EntityQueryRepository, Repository<Entity, Long> {

    @Override
    @Query( value= "SELECT M " +
            "FROM Entity M " +
            "LEFT OUTER JOIN Entity P "+
            "ON M.entityId= P.entityId AND M.date < P.date "+
            "WHERE M.taxNumber= :taxNumber AND M.type= :type AND P.date IS NULL")
    Optional<EntityDto> findByTypeAndTaxNumber( EntityType type, String taxNumber );

    @Override
    @Query( value= "SELECT M " +
            "FROM Entity M " +
            "LEFT OUTER JOIN Entity P "+
            "ON M.entityId= P.entityId AND M.date < P.date "+
            "WHERE M.taxNumber= :taxNumber AND M.country= :country AND M.type= :type AND P.date IS NULL")
    Optional<EntityDto> findByCountryAndTypeAndTaxNumber( Country country, EntityType type, String taxNumber);

    @Override
    @Query( value= "SELECT M " +
            "FROM Entity M " +
            "LEFT OUTER JOIN Entity P "+
            "ON M.entityId= P.entityId AND M.date < P.date "+
            "WHERE TYPE(M)= :type AND P.date IS NULL")
    <T> List<T> findByType(Class<T> tClass, EntityType type);
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
    public Optional<Entity> findByEntityIdAndDate( UUID entityId, LocalDate date) {
        return repository.findByEntityIdAndDate( entityId, date);
    }

    @Override
    public Optional<Entity> findEntityByEntityIdAndDate( UUID entityId, LocalDate date) {
        return repository.findEntityByEntityIdAndDate( entityId, date);
    }
}