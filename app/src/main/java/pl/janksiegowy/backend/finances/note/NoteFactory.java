package pl.janksiegowy.backend.finances.note;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.note.dto.NoteDto;
import pl.janksiegowy.backend.finances.note.NoteType.NoteTypeVisitor;
import pl.janksiegowy.backend.period.PeriodFacade;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class NoteFactory implements NoteTypeVisitor<Note> {
    private final EntityRepository entities;
    private final PeriodFacade periods;

    public Note from( NoteDto source ) {
        return (Note) source.getType().accept( this)
                .setNumber( source.getNumber())
                .setDate( source.getDate())
                .setDue( source.getDue())
                .setAmount( source.getAmount())
                .setDocumentId( Optional.ofNullable( source.getNoticeId())
                        .orElseGet( UUID::randomUUID))
                .setPeriod( periods.findMonthPeriodOrAdd( source.getDate()))
                .setEntity( entities.findByEntityIdAndDate(
                        source.getEntity().getEntityId(), source.getDate()).orElseThrow());
    }

    @Override public Note visitIssuedNote() {
        return new IssuedNote();
    }

    @Override public Note visitReceiveNote() {
        return new ReceiveNote();
    }
}
