package pl.janksiegowy.backend.register.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.financial.Currency;

public interface BankAccountDto extends RegisterDto {

    static Proxy create() {
        return new Proxy();
    }

    Currency getCurrency();
    String getNumber();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy extends RegisterDto.Proxy implements BankAccountDto  {

        private Currency currency;
        private String number;

        @Override public Currency getCurrency() {
            return currency;
        }

        @Override public String getNumber() {
            return number;
        }
    }
}
