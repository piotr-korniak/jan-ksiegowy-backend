package pl.janksiegowy.backend.finances.note;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.finances.note.dto.NoteDto;

@AllArgsConstructor
public class NoteFacade {

    private final NoteFactory factory;
    private final NoteRepository repository;

    public void save( NoteDto source ) {
        repository.save( factory.from( source));

    }
}
