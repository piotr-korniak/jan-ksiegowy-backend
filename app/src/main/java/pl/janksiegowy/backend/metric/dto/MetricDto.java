package pl.janksiegowy.backend.metric.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.tenant.dto.TenantDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MetricDto {

    static Proxy create() {
        return new Proxy();
    }

    LocalDate getId();
    String getTaxNumber();
    String getRegistrationNumber();
    String getBusinessNumber();
    String getName();
    String getAddress();
    String getTown();
    String getPostcode();
    String getCountry();
    BigDecimal getCapital();
    boolean isVatQuarterly();
    boolean isCitQuarterly();
    boolean isVatUe();
    String getRcCode();



    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements MetricDto {

        private LocalDate id;
        private String taxNumber;
        private String registrationNumber;
        private String businessNumber;
        private String name;
        private String address;
        private String town;
        private String postcode;
        private String country;
        private BigDecimal capital;
        private boolean vatQuarterly;
        private boolean citQuarterly;
        private boolean vatUE;
        private String rcCode;

        @Override public LocalDate getId() {
            return id;
        }
        @Override public String getTaxNumber() {
            return taxNumber;
        }
        @Override public String getRegistrationNumber() {
            return registrationNumber;
        }
        @Override public String getBusinessNumber() {
            return businessNumber;
        }
        @Override public String getName() {
            return name;
        }
        @Override public String getAddress() {
            return address;
        }
        @Override public String getTown() {
            return town;
        }
        @Override  public String getPostcode() {
            return postcode;
        }
        @Override public String getCountry() {
            return country;
        }
        @Override public BigDecimal getCapital() {
            return capital;
        }
        @Override public boolean isVatQuarterly() {
            return vatQuarterly;
        }
        @Override public boolean isCitQuarterly() {
            return citQuarterly;
        }
        @Override public boolean isVatUe() {
            return vatUE;
        }
        @Override public String getRcCode() {
            return rcCode;
        }
    }
}
