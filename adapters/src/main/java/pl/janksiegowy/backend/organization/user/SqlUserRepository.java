package pl.janksiegowy.backend.organization.user;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface SqlUserRepository extends JpaRepository<User, UUID> {
}

interface SqlUserQueryRepository extends UserQueryRepository, Repository<User, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class UserRepositoryImpl implements UserRepository {

    private final SqlUserRepository repository;

    @Override
    public User save( User user) {
        return repository.save( user);
    }


}