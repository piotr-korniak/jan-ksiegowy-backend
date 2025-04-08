package pl.janksiegowy.backend.metric.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByNames;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    boolean isVatPL();
    boolean isVatUE();
    String getRcCode();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements MetricDto {

        @JsonProperty( "Date")
        private LocalDate id;

        @JsonProperty( "Tax Number")
        private String taxNumber;

        @JsonProperty( "Registration Number")
        private String registrationNumber;

        @JsonProperty( "Business Number")
        private String businessNumber;

        @JsonProperty( "Name")
        private String name;

        @JsonProperty( "Address")
        private String address;

        @JsonProperty( "Town")
        private String town;

        @JsonProperty( "Postcode")
        private String postcode;

        @JsonProperty( "Country")
        private String country;

        @JsonProperty( "Capital")
        private BigDecimal capital;

        @JsonProperty( "VAT Quarterly")
        private boolean vatQuarterly;

        @JsonProperty( "CIT Quarterly")
        private boolean citQuarterly;

        @JsonProperty( "VAT PL")
        private boolean vatPL;

        @JsonProperty( "VAT UE")
        private boolean vatUE;

        @JsonProperty( "RC Code")
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
        @Override public boolean isVatPL() { return vatPL;}
        @Override public boolean isVatUE() {
            return vatUE;
        }
        @Override public String getRcCode() {
            return rcCode;
        }
    }
}
