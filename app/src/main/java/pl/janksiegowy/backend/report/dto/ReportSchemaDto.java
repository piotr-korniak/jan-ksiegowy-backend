package pl.janksiegowy.backend.report.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.report.ReportFunction;
import pl.janksiegowy.backend.report.ReportType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ReportSchemaDto {

    UUID getReportSchemaId();
    UUID getParentSchemaId();

    ReportType getType();
    String getCode();
    String getParentCode();

    String getName();

    ReportFunction getFunction();
    String getParameters();

    List<ReportSchemaDto> getItems();

    int getNo();
    boolean isHidden();

    @Setter
    @Accessors( fluent= true, chain= true)
    @JsonIgnoreProperties( ignoreUnknown= true)
    class Proxy implements ReportSchemaDto {

        private UUID reportId;
        private UUID parentId;

        @JsonProperty( "Parent Code")
        private String parentCode;

        @JsonProperty( "Type")
        private ReportType type;

        @JsonProperty( "Code")
        private String code;

        @Setter
        @JsonProperty( "Name")
        private String name;

        @JsonProperty( "Function")
        private ReportFunction function;

        @JsonProperty( "Parameters")
        private String parameters;

        @JsonProperty( "No")
        private int no;

        @JsonProperty( "Hidden")
        private boolean hidden;

        private List<ReportSchemaDto> items= new ArrayList<>();

        @Override public UUID getReportSchemaId() {
            return reportId;
        }

        @Override public UUID getParentSchemaId() {
            return parentId;
        }

        @Override public ReportType getType() {
            return type;
        }

        @Override public String getCode() {
            return code;
        }

        @Override public String getName() {
            return name;
        }

        @Override public ReportFunction getFunction() {
            return function;
        }

        @Override public String getParameters() {
            return parameters;
        }

        @Override public List<ReportSchemaDto> getItems() {
            return items;
        }

        @Override public int getNo() {
            return no;
        }

        @Override public boolean isHidden() {
            return hidden;
        }

        @Override public String getParentCode() {
            return parentCode;
        }

    }
}
