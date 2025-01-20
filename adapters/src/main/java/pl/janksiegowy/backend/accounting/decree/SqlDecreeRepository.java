package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.entity.Entity;
import pl.janksiegowy.backend.entity.EntityQueryRepository;


import java.util.Optional;
import java.util.UUID;

public interface SqlDecreeRepository extends JpaRepository<Decree, UUID> {
}

interface SqlDecreeQueryRepository extends DecreeQueryRepository, Repository<Decree, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class DecreeRepositoryImpl implements DecreeRepository {

    private final SqlDecreeRepository repository;
    @Override public Decree save( Decree decree) {
        return repository.save( decree);
    }

    @Override public void delete( Decree decree) {
        repository.delete( decree);
    }

    @Override public Optional<Decree> findById( UUID id) {
        return repository.findById( id);
    }
}