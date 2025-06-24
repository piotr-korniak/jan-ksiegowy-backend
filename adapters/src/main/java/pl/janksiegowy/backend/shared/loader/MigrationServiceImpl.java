package pl.janksiegowy.backend.shared.loader;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.notice.dto.NoticeDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceCsv;
import pl.janksiegowy.backend.item.dto.ItemDto;
import pl.janksiegowy.backend.metric.dto.MetricDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.report.dto.ReportSchemaDto;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.util.List;

@Service
@AllArgsConstructor
public class MigrationServiceImpl implements MigrationService {

    private final DataLoader dataLoader;
    private final RegisterQueryRepository registers;

    @Override public List<ReportSchemaDto> loadReportSchemas() {
        return dataLoader.loadCsv("report_schemas.csv", ReportSchemaDto.Proxy.class);
    }

    @Override public List<MetricDto> loadMetrics() {
        return dataLoader.loadCsv("metrics.csv", MetricDto.Proxy.class);
    }

    @Override public List<ContractDto.Proxy> loadContracts() {
        return dataLoader.loadCsv("contracts.csv", ContractDto.Proxy.class);
    }

    @Override public List<NoticeDto.Proxy> loadNotices() {
        return dataLoader.loadCsv("notices.csv", NoticeDto.Proxy.class, row-> !row.startsWith( "---"));
    }

    @Override public List<AccountDto> loadAccounts() {
        return dataLoader.loadCsv("accounts.csv", AccountDto.Proxy.class);
    }

    @Override public List<PeriodDto> loadPeriods() {
        return dataLoader.loadCsv("periods.csv", PeriodDto.Proxy.class);
    }

    @Override public List<NumeratorDto> loadNumerators() {
        return dataLoader.loadCsv("numerators.csv", NumeratorDto.Proxy.class);
    }

    @Override public List<ItemDto> loadItems() {
        return dataLoader.loadCsv("items.csv", ItemDto.Proxy.class);
    }

    @Override
    public List<RegisterDto> loadRegisters() {
        return dataLoader.loadCsv("registers.csv", RegisterDto.Proxy.class);
    }

    @Override
    public List<InvoiceCsv> loadInvoices() {
        return dataLoader.loadCsv("invoices.csv", InvoiceCsv.class, row-> !row.startsWith( "---"));
    }

    @Override
    public List<EntityDto> loadEntity() {
        return dataLoader.loadCsv("entities.csv", EntityDto.class);
    }

    @Override
    public List<TemplateDto> loadTemplates() {
        return dataLoader.loadYml( "templates.yaml",
                TemplateDto.Proxy.class, RegisterQueryRepository.class, registers);
    }

}
