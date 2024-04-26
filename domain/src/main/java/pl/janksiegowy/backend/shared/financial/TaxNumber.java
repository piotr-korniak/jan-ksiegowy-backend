package pl.janksiegowy.backend.shared.financial;

import pl.janksiegowy.backend.entity.Country;

public interface TaxNumber {
    String getTaxNumber();
    Country getCountry();
}
