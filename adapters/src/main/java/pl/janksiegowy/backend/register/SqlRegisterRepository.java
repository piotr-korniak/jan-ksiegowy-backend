package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlRegisterRepository extends JpaRepository<Register, UUID> {

    Optional<Register> findByCode( String code);
    Optional<Register> findByTypeAndCode( RegisterType type, String code);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class RegisterRepositoryImpl implements RegisterRepository {

    private final SqlRegisterRepository repository;

    @Override public Optional<Register> findByCode( String code) {
        return repository.findByCode( code);
    }

    @Override
    public Optional<Register> findByTypeAndCode( RegisterType type, String code) {
        return repository.findByTypeAndCode( type, code);
    }

    @Override public Register save( Register register) {
        return repository.save( register);
    }
}
