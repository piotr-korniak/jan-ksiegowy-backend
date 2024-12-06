package pl.janksiegowy.backend.metric.dto;

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

        @CsvBindByName( column= "Date")
        @CsvDate( "dd.MM.yyyy")
        private LocalDate id;

        @CsvBindByName( column= "Tax Number")
        private String taxNumber;

        @CsvBindByName( column= "Registration Number")
        private String registrationNumber;

        @CsvBindByName( column= "Business Number")
        private String businessNumber;

        @CsvBindByName
        private String name;

        @CsvBindByName
        private String address;

        @CsvBindByName
        private String town;

        @CsvBindByName
        private String postcode;

        @CsvBindByName
        private String country;

        @CsvBindByName
        private BigDecimal capital;

        @CsvBindByName
        private boolean vatQuarterly;
        @CsvBindByName
        private boolean citQuarterly;

        @CsvBindByName
        private boolean vatPL;
        @CsvBindByName
        private boolean vatUE;

        @CsvBindByName
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
