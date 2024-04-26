package pl.janksiegowy.backend.finances.note;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SqlNoteRepository extends JpaRepository<Note, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class NoteRepositoryImpl implements NoteRepository {

    private final SqlNoteRepository repository;

    @Override public Note save( Note note ) {
        return repository.save( note );
    }
}