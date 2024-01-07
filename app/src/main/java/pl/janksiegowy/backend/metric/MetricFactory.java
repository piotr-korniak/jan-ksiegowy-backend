package pl.janksiegowy.backend.metric;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.metric.dto.MetricDto;

@AllArgsConstructor
public class MetricFactory {

    public Metric from( MetricDto source) {
        return new Metric()
                .setId( source.getId())
                .setTaxNumber( source.getTaxNumber())
                .setRegistrationNumber( source.getRegistrationNumber())
                .setBusinessNumber( source.getBusinessNumber())
                .setName( source.getName())
                .setAddress( source.getAddress())
                .setPostcode( source.getPostcode())
                .setTown( source.getTown())
                .setCountry( source.getCountry())
                .setCapital( source.getCapital())
                .setVatQuarterly( source.isVatQuarterly())
                .setCitQuarterly( source.isCitQuarterly())
                .setVatUe( source.isVatUe())
                .setRcCode( source.getRcCode());
    }

}
