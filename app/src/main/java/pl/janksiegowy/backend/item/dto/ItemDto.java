package pl.janksiegowy.backend.item.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.financial.TaxMetod;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.item.ItemType;

import java.time.LocalDate;
import java.util.UUID;

public interface ItemDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getItemId();
    LocalDate getDate();
    ItemType getType();
    String getCode();
    String getName();
    TaxRate getTaxRate();
    TaxMetod getTaxMetod();
    String getMeasure();
    boolean getSold();
    boolean getPurchased();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ItemDto {

        private UUID itemId;
        private LocalDate date;
        private ItemType type;

        private String code;
        private String name;

        private TaxRate taxRate;
        private TaxMetod taxMetod;
        private String measure;

        private boolean sold;
        private boolean purchased;

        @Override public UUID getItemId() {
            return itemId;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public ItemType getType() {
            return type;
        }
        @Override public String getCode() {
            return code;
        }
        @Override public String getName() {
            return name;
        }
        @Override public TaxRate getTaxRate() {
            return taxRate;
        }
        @Override public TaxMetod getTaxMetod() {
            return taxMetod;
        }
        @Override public String getMeasure() {
            return measure;
        }
        @Override public boolean getSold() {
            return sold;
        }
        @Override public boolean getPurchased() {
            return purchased;
        }
    }
}
