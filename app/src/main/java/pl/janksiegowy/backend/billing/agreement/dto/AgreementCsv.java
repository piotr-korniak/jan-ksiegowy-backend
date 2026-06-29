package pl.janksiegowy.backend.billing.agreement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.billing.common.BillingType;
import pl.janksiegowy.backend.entity.Country;

import java.time.LocalDate;

@JsonDeserialize( as= AgreementCsv.Proxy.class)
public interface AgreementCsv {

    String getTaxNumber();
    LocalDate getDate();
    long getBillingTypeMask();
    Country getCountry();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements AgreementCsv {

        @JsonProperty( "Tax Number")
        String taxNumber;
        Country country= Country.PL;

        @JsonProperty( "Date")
        private LocalDate date;

        @JsonProperty( "Billing Types")
        private String billingTypesRaw;
        private long billingTypeMask;

        /** Wywołany przez Jacksona po ustawieniu billingTypesRaw. */
        @JsonProperty( "Billing Types")
        public Proxy billingTypesRaw(String raw) {
            this.billingTypesRaw = raw;
            this.billingTypeMask = buildMask(raw);
            return this;
        }

        private static long buildMask(String raw) {
            if (raw == null || raw.isBlank()) return 0L;

            long mask= 0L;
            for( String code: raw.split("\\|")) {
                try {
                    mask |= BillingType.valueOf( code.trim().toUpperCase()).bit();
                } catch( IllegalArgumentException ignored) {}
            }
            return mask;
        }

        @Override public String getTaxNumber() {
            return taxNumber;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public long getBillingTypeMask() {
            return billingTypeMask;
        }
        @Override public Country getCountry() {
            return country;
        }
    }


}
