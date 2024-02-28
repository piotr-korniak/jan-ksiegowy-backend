package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface SqlDecreeRepository extends JpaRepository<Decree, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class DecreeRepositoryImpl implements DecreeRepository {

    private final SqlDecreeRepository repository;
    @Override public Decree save( Decree decree) {
        return repository.save( decree);
    }
}