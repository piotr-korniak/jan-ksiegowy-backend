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
        return repository.save(
                Optional.ofNullable( source.getItemId())
                        .map( itemId-> repository.findItemByItemIdAndDate( itemId, source.getDate())
                                .map( item-> factory.update( source, item))
                                .orElseGet(()-> factory.from( source)))
                        .orElseGet(()-> factory.from( source)));
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadItems().forEach( item-> {
            counters[0]++;

            repository.findItemByCodeAndDate( item.getCode(), item.getDate())
                    .ifPresentOrElse(existing-> {
                        if( !existing.getDate().equals( item.getDate())) {
                            counters[1]++;
                            repository.save( factory.from( updateSold( (ItemDto.Proxy)item)
                                    .itemId( existing.getItemId())));
                        }
                    }, ()-> {
                        counters[1]++;
                        repository.save( factory.from(( updateSold( (ItemDto.Proxy)item))));
                    });

        });
        log.warn( "Items migration complete!");
        return "%-50s %13s".formatted("Items migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }

    private ItemDto.Proxy updateSold( ItemDto.Proxy source) {
        var isSold= source.getCode().startsWith( "ZL")
                || source.getCode().startsWith( "US");
        return source.sold( isSold).purchased( !isSold);
    }

}
