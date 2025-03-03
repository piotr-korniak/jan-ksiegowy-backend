package pl.janksiegowy.backend.item;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface ItemRepository {

    Item save( Item item);
    Optional<Item> findByItemIdAndDate( UUID itemId, LocalDate date);
    Optional<Item> findItemByItemIdAndDate( UUID itemId, LocalDate date);

    Optional<Item> findItemByCodeAndDate(String code, LocalDate date);
}
