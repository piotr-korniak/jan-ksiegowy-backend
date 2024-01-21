package pl.janksiegowy.backend.invoice_line;

import pl.janksiegowy.backend.financial.TaxMetod;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineSumDto;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.QuaterPeriod;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.util.List;
import java.util.UUID;

public interface InvoiceLineQueryRepository {

    List<InvoiceLineDto> findAll( List<TaxMetod> taxMetod);

    List<InvoiceLineSumDto> findByInvoiceId( UUID id);

    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds, List<InvoiceRegisterKind> purchaseKinds, Period period);

    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByType( MonthPeriod period);

    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds,
            List<InvoiceRegisterKind> purchaseKinds,
            QuaterPeriod period);

    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            QuaterPeriod period);

    List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            InvoiceRegisterKind kind,
            QuaterPeriod period);

    List<JpaInvoiceSumDto> sumSalesByKindAndItemTypeGroupByType(
            List<InvoiceRegisterKind> salesKinds,
            QuaterPeriod period);

    List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            QuaterPeriod period);

    List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( QuaterPeriod period);

}
