package pl.janksiegowy.backend.billing.common;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum BillingType {
    INV(1L),
    VAT(1L << 1),
    PIT(1L << 2),
    CIT(1L << 3),
    ZUS(1L << 4);

    private final long bit;

    BillingType( long bit) {
        this.bit= bit;
    }

    public long bit() {
        return bit;
    }

    /** Składa maskę z podanych typów */
    public static long maskOf( BillingType... types) {
        long mask = 0L;
        for( BillingType t: types) {
            mask |= t.bit;
        }
        return mask;
    }

    /** Sprawdza czy maska zawiera dany typ? */
    public static boolean contains( long mask, BillingType type) {
        return (mask & type.bit) != 0;
    }

    public static Set<BillingType> getBillingTypes( long billingTypeMask) {
        return Arrays.stream( BillingType.values())
                .filter(t-> BillingType.contains( billingTypeMask, t))
                .collect( Collectors.toSet());
    }
}
