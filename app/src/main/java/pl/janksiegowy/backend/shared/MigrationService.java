package pl.janksiegowy.backend.shared;

import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.finances.notice.dto.NoticeDto;
import pl.janksiegowy.backend.item.dto.ItemDto;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.report.dto.ReportSchemaDto;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.util.List;

public interface MigrationService {

    List<ReportSchemaDto> loadReportSchemas();
    List<MetricDto> loadMetrics();
    List<ContractDto.Proxy> loadContracts();
    List<NoticeDto.Proxy> loadNotices();
    List<AccountDto> loadAccounts();
    List<PeriodDto> loadPeriods();

    List<TemplateDto> loadTemplates();

    List<NumeratorDto> loadNumerators();

    List<ItemDto> loadItems();

    List<RegisterDto> loadRegisters();
}
