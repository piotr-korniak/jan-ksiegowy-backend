package pl.janksiegowy.backend.report.dto;

import pl.janksiegowy.backend.report.ReportFunction;
import pl.janksiegowy.backend.report.ReportType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportSchemaMap implements ReportSchemaDto {

    private final ReportSchemaDto reportSchema;
    private final List<ReportSchemaDto> items;

    public ReportSchemaMap( ReportSchemaDto reportSchema) {
        this.reportSchema = reportSchema;
        this.items= new ArrayList<>( reportSchema.getItems());
    }

    @Override public UUID getReportSchemaId() {
        return reportSchema.getReportSchemaId();
    }

    @Override public UUID getParentSchemaId() {
        return reportSchema.getParentSchemaId();
    }

    @Override public ReportType getType() {
        return reportSchema.getType();
    }

    @Override public String getCode() {
        return reportSchema.getCode();
    }

    @Override public String getParentCode() {
        return reportSchema.getParentCode();
    }

    @Override public String getName() {
        return reportSchema.getName();
    }

    @Override public ReportFunction getFunction() {
        return reportSchema.getFunction();
    }

    @Override public String getParameters() {
        return reportSchema.getParameters();
    }

    @Override public int getNo() {
        return reportSchema.getNo();
    }

    @Override public boolean isHidden() {
        return reportSchema.isHidden();
    }

    @Override public List<ReportSchemaDto> getItems() {
        return items;
    }

    public ReportSchemaMap add(ReportSchemaDto item) {
        items.add( new ReportSchemaMap( item));
        return this;
    }
}