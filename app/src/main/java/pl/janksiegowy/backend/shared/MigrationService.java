package pl.janksiegowy.backend.shared;

import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.finances.notice.dto.NoticeDto;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.report.dto.ReportSchemaDto;
import pl.janksiegowy.backend.contract.dto.ContractDto;

import java.util.List;

public interface MigrationService {

    List<ReportSchemaDto> loadReportSchemas();
    List<MetricDto> loadMetrics();
    List<ContractDto.Proxy> loadContracts();
    List<NoticeDto.Proxy> loadNotices();
    List<AccountDto> loadAccounts();

    List<TemplateDto> loadTemplates();
}
