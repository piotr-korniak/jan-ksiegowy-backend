package pl.janksiegowy.backend.item;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.item.dto.ItemDto;

import java.util.Optional;

@AllArgsConstructor
public class ItemFacade {

    private final ItemFactory factory;
    private final ItemRepository repository;

    public Item save( ItemDto source) {
        return repository.save( Optional.ofNullable( source.getItemId())
                .map( itemId-> repository.findItemByItemIdAndDate( itemId, source.getDate())
                        .map( item-> factory.update( source, item))
                        .orElse( factory.update( source)))
                .orElse( factory.from( source)));
    }
}
