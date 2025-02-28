package pl.janksiegowy.backend.item;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.item.dto.ItemDto;
import pl.janksiegowy.backend.shared.MigrationService;

import java.util.Optional;
@Log4j2

@AllArgsConstructor
public class ItemFacade {

    private final ItemFactory factory;
    private final ItemRepository repository;
    private final MigrationService migrationService;

    public Item save( ItemDto source) {
        return repository.save( Optional.ofNullable( source.getItemId())
                .map( itemId-> repository.findItemByItemIdAndDate( itemId, source.getDate())
                        .map( item-> factory.update( source, item))
                        .orElse( factory.update( source)))
                .orElse( factory.from( source)));
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadItems().forEach( item-> {
            counters[0]++;

            repository.findItemByItemCodeAndDate( item.getCode(), item.getDate()).orElseGet(()-> {
                counters[1]++;
                return save( item);
            });
        });
        log.warn( "Items migration complete!");
        return "%-50s %13s".formatted("Items migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }
}
