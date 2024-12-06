package pl.janksiegowy.backend.finances.settlement.dto;

import pl.janksiegowy.backend.shared.report.Summary;

import java.util.List;

public interface EntityReport {

    String getEntityType();
    String getEntityAccountNumber();
    String getEntityName();
    List<SettlementReportLine> getSettlements();
    List<String> getHeaders(); // Nagłówki tabeli
    List<List<Object>> getRows(); // Wiersze z danymi rozrachunków
    Summary getSum();
}
