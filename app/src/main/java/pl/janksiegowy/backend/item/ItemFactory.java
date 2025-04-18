package pl.janksiegowy.backend.item;

import pl.janksiegowy.backend.item.dto.ItemDto;
import pl.janksiegowy.backend.item.ItemType.ItemTypeVisitor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class ItemFactory implements ItemTypeVisitor<Item> {

    public Item from( ItemDto source) {
        return update( source, source.getType()
                .accept(this))
                    .setItemId( Optional.ofNullable( source.getItemId()).orElseGet( UUID::randomUUID))
                    .setDate( Optional.ofNullable( source.getDate()).orElseGet(()-> LocalDate.EPOCH));
    }

    Item update( ItemDto source, Item item) {
        return item.setCode( source.getCode())
                .setName( source.getName())
                .setTaxRate( source.getTaxRate())
                .setTaxMetod( source.getTaxMetod())
                .setMeasure( source.getMeasure())
                .setSold( source.getSold())
                .setPurchased( source.getPurchased());
    }

    @Override public Item visitAsset() {
        return null;
    }

    @Override public Item visitService() {
        return new Service();
    }

    @Override public Item visitMaterial() {
        return new Material();
    }

    @Override public Item visitProduct() {
        return null;
    }

    @Override public Item visitOther() {
        return new Other();
    }

}