package pl.janksiegowy.backend.finances.share;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SqlShareRepository extends JpaRepository<Share, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ShareRepositoryImpl implements ShareRepository {
    private final JpaRepository<Share, UUID> repository;


    @Override
    public Share save( Share share) {
        return repository.save( share);
    }
}
