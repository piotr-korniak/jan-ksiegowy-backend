package pl.janksiegowy.backend.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import pl.janksiegowy.backend.shared.financial.TaxRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public record TaxedAmount(
        BigDecimal net,
        BigDecimal tax,
        @Enumerated( EnumType.STRING) TaxRate taxRate) {

    @JsonCreator
    public static TaxedAmount of(
            @JsonProperty( "net") BigDecimal net,
            @JsonProperty( "tax") BigDecimal tax,
            @JsonProperty( "taxRate") TaxRate taxRate) {
        return new TaxedAmount( net, tax, taxRate);
    }

    public BigDecimal gross() {
        return net.add( tax);
    }

    public BigDecimal net( String percent) {
        return net
                .multiply( new BigDecimal( percent))
                .setScale(2, RoundingMode.HALF_DOWN);
    }

    public BigDecimal tax( String percent) {
        return tax
                .multiply( new BigDecimal( percent))
                .setScale(2, RoundingMode.HALF_DOWN);
    }

}
