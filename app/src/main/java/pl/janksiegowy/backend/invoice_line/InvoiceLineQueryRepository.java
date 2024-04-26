package pl.janksiegowy.backend.invoice_line;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.shared.financial.TaxMetod;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineSumDto;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.period.QuarterPeriod;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.util.List;
import java.util.UUID;

public interface InvoiceLineQueryRepository {

    List<InvoiceLineDto> findAll( List<TaxMetod> taxMetod);

    List<InvoiceLineSumDto> findByInvoiceId( UUID id);

    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds, List<InvoiceRegisterKind> purchaseKinds, MonthPeriod period);

    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByType( String periodId);

    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds,
            List<InvoiceRegisterKind> purchaseKinds,
            QuarterPeriod period);

    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            QuarterPeriod period);

    List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            InvoiceRegisterKind kind,
            QuarterPeriod quarterPeriod);

    List<JpaInvoiceSumDto> sumSalesByKindAndItemTypeGroupByType(
            List<InvoiceRegisterKind> salesKinds,
            String quarterPeriod);

    List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            QuarterPeriod quarterPeriod);

    List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( QuarterPeriod quarterPeriodId);

}
