package pl.janksiegowy.backend.item;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {

    Item save( Item item);
    Optional<Item> findByItemIdAndDate( UUID itemId, LocalDate date);
    Optional<Item> findItemByItemIdAndDate( UUID itemId, LocalDate date);


    Optional<Item> findItemByItemCodeAndDate( String code, LocalDate date);
}
