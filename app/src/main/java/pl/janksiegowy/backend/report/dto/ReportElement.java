package pl.janksiegowy.backend.report.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude( JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ "title", "startDate", "endDate", "name", "value", "elements" })
public interface ReportElement {
    static Proxy create() { return new Proxy();}

    @JsonProperty( "title")
    String getTitle();

    @JsonFormat( pattern= "yyyy-MM-dd")
    LocalDate getStartDate();

    @JsonFormat( pattern= "yyyy-MM-dd")
    LocalDate getEndDate();

    @JsonProperty( "name")
    String getName();

    @JsonProperty( "value")
    BigDecimal getValue();

    @JsonProperty( "elements")
    List<ReportElement> getElements();

    @Setter
    @Accessors( fluent= true, chain= true)
    @JsonIgnoreProperties( ignoreUnknown= true)
    class Proxy implements ReportElement {

        private String name;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal value;

        private List<ReportElement> elements;

        @Override public BigDecimal getValue() {
            return value;
        }

        @Override public String getTitle() {
            return title;
        }

        @Override public LocalDate getStartDate() {
            return startDate;
        }

        @Override public LocalDate getEndDate() {
            return endDate;
        }

        @Override public String getName() {
            return name;
        }

        @Override public List<ReportElement> getElements() {
            return elements;
        }
    }

}
