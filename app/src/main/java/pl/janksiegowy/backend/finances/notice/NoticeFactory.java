package pl.janksiegowy.backend.finances.notice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.notice.dto.NoticeDto;
import pl.janksiegowy.backend.finances.notice.NoteType.NoteTypeVisitor;
import pl.janksiegowy.backend.period.PeriodFacade;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class NoticeFactory implements NoteTypeVisitor<Note> {

    private final EntityRepository entityRepository;
    private final PeriodFacade periods;

    public Note from( NoticeDto source) {
        return entityRepository.findByCountryAndTaxNumberAndTypesAndDate(
                        source.getEntityCountry(),
                        source.getEntityTaxNumber(),
                        source.getDate(),
                        EntityType.C, EntityType.R, EntityType.B)
                .map(entity-> (Note) source.getType().accept( this)
                    .setNumber( source.getNumber())
                    .setIssueDate( source.getDate())
                    .setDueDate( source.getDue())
                    .setAmount( source.getAmount())
                    .setDocumentId( Optional.ofNullable( source.getNoticeId())
                            .orElseGet( UUID::randomUUID))
                    .setPeriod( periods.findMonthPeriodOrAdd( source.getDate()))
                    .setEntity( entity))
                .orElseThrow();
    }

    @Override public Note visitIssuedNote() {
        return new IssuedNote();
    }

    @Override public Note visitReceiveNote() {
        return new ReceiveNote();
    }
}
