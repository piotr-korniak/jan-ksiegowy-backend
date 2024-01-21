package pl.janksiegowy.backend.item;

import pl.janksiegowy.backend.item.dto.ItemDto;

import java.util.Optional;

public interface ItemQueryRepository {
    Optional<ItemDto> findByCode( String code);
}
