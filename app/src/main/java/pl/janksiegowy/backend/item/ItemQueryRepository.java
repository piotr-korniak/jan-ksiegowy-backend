package pl.janksiegowy.backend.item;

import pl.janksiegowy.backend.item.dto.ItemDto;

import java.time.LocalDate;
import java.util.Optional;

public interface ItemQueryRepository {

    Optional<ItemDto> findByCode( String code);
    Optional<ItemDto> findByCodeAndDate(String code, LocalDate date);
}
