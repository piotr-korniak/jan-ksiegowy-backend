package pl.janksiegowy.backend.item;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.item.dto.ItemDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface SqlItemRepository extends JpaRepository<Item, Long> {

    @Query( value= "FROM Item M " +
            "LEFT OUTER JOIN Item P "+
            "ON M.itemId= P.itemId AND (P.date <= :date AND M.date < P.date)"+
            "WHERE M.itemId= :itemId AND M.date <= :date AND P.date IS NULL")
    Optional<Item> findByItemIdAndDate( UUID itemId, LocalDate date);

    Optional<Item> findItemByItemIdAndDate( UUID itemId, LocalDate date);

}

interface ItemQueryRepositoryImpl extends ItemQueryRepository, Repository<Item, Long> {
    @Override
    @Query( value= "SELECT M " +
            "FROM Item M " +
            "LEFT OUTER JOIN Item P "+
            "ON M.itemId= P.itemId AND M.date < P.date "+
            "WHERE M.code= :code AND P.date IS NULL")
    Optional<ItemDto> findByCode( String code);

    @Query( value= "FROM Item M " +
            "LEFT OUTER JOIN Item P "+
            "ON M.itemId= P.itemId AND (P.date <= :date AND M.date < P.date)"+
            "WHERE M.code= :code AND M.date <= :date AND P.date IS NULL")
    Optional<ItemDto> findByCodeAndDate( String code, LocalDate date);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ItemRepositoryImpl implements ItemRepository {

    private final SqlItemRepository repository;
    @Override public Item save( Item item) {
        return repository.save( item);
    }

    @Override
    public Optional<Item> findByItemIdAndDate( UUID itemId, LocalDate date) {
        return repository.findByItemIdAndDate( itemId, date);
    }

    @Override
    public Optional<Item> findItemByItemIdAndDate( UUID itemId, LocalDate date) {
        return repository.findItemByItemIdAndDate( itemId, date);
    }

}