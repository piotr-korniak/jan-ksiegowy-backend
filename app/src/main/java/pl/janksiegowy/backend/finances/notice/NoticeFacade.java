package pl.janksiegowy.backend.finances.notice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.accounting.decree.DecreeFacade;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.notice.dto.NoticeDto;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.Util;

@Log4j2

@AllArgsConstructor
public class NoticeFacade {

    private final NoticeFactory factory;
    private final NoteRepository repository;
    private final DecreeFacade decrees;
    private final MigrationService migrationService;
    private final EntityQueryRepository entities;
    private final SettlementQueryRepository settlements;

    public void approval( Note note) {
        decrees.book( note);
    }

    public Note save( NoticeDto source ) {
        return repository.save( factory.from( source));
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadNotices()
            .forEach( noticeDto-> {
                var entity= Util.parseTaxNumber( noticeDto.getTaxNumber(), "PL");
                counters[0]++;

                if( !settlements.existsByNumberAndEntityTaxNumber( noticeDto.getNumber(), entity.getTaxNumber())){
                    entities.findByCountryAndTaxNumberAndTypes( entity.getCountry(), entity.getTaxNumber(),
                            EntityType.C,EntityType.R, EntityType.B).stream().findFirst()
                            .ifPresent( entityDto -> {
                                approval( save( noticeDto.entity( entityDto)));
                                counters[1]++;
                            });
                }
            });
        log.warn( "Notices migration complete!");
        return String.format( "%-40s %16s", "Notices migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }
}
