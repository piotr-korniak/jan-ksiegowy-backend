package pl.janksiegowy.backend.report;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.report.dto.ReportSchemaDto;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ReportFactory implements ReportType.ReportTypeVisitor<ReportSchema> {

    public ReportSchema from( ReportSchemaDto source) {
        return source.getType().accept( this)
                .setReportSchemaId( Optional.ofNullable( source.getReportSchemaId()).orElseGet( UUID::randomUUID))
                .setParentSchemaId( source.getParentSchemaId())
                .setCode( source.getCode())
                .setName( source.getName())
                .setFunction( source.getFunction())
                .setParameters( source.getParameters())
                .setNo( source.getNo())
                .setHidden( source.isHidden())
                .setItems( source.getItems().stream().map( this::from).collect( Collectors.toList()));
    }

    @Override public ReportSchema visitBalance() {
        return new BalanceReport();
    }

    @Override public ReportSchema visitCIT() {
        return new CITReport();
    }

    @Override public ReportSchema visitProfitAndLoss() {
        return new ProfitAndLossReport();
    }
}
