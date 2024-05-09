package pl.janksiegowy.backend.finances.note;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.finances.note.dto.NoteDto;

@AllArgsConstructor
public class NoteFacade {

    private final NoteFactory factory;
    private final NoteRepository repository;
    private final DecreeFacade decrees;

    public void approval( Note note) {
        decrees.book( note);
    }

    public Note save( NoteDto source ) {
        return repository.save( factory.from( source));
    }
}
