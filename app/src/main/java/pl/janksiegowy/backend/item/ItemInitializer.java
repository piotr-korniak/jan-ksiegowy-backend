package pl.janksiegowy.backend.item;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.financial.TaxMethod;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.item.dto.ItemDto;
import pl.janksiegowy.backend.shared.DataLoader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class ItemInitializer {

    private final ItemQueryRepository items;
    private final ItemFacade facade;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    public void init() {
        var history= new Object(){ LocalDate date= LocalDate.EPOCH;};

        for( String[] fields: loader.readData( "items.txt")) {
            if( fields[0].startsWith( "--- ")) {    // set date
                history.date= LocalDate.parse( fields[0], formatter);
                continue;
            }
            var code= fields[1];
            var item= items.findByCode( code);

            if( item.map( i-> !i.getDate().isBefore( history.date)).orElse( false))
                continue; // optimization, omit if Item already exists

            System.out.println( ">>> SAVE ITEMS!!! "+ code);
            facade.save( item
                    .map( itemDto-> ItemDto.create()
                            .itemId( itemDto.getItemId()))
                    .orElse( ItemDto.create())
                            .date( history.date)
                            .type( ItemType.valueOf( fields[0]))
                            .code( code)
                            .taxMetod( TaxMethod.valueOf( fields[2]))
                            .taxRate( TaxRate.valueOf( fields[3]))
                            .name( fields[4])
                            .measure( fields[5])
                            .sold( isSold( code))
                            .purchased( !isSold( code)));

        }
    }

    private boolean isSold( String code) {
        return code.startsWith( "ZL")|| code.startsWith( "US");
    }
}
