package pl.janksiegowy.backend.accounting.decree.dto;


import java.math.BigDecimal;
import java.util.List;

public interface DecreeReportDto extends DecreeRaportLineDto {

    List<DecreeRaportLineDto> getDecree();
}
