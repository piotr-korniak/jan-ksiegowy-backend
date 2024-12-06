package pl.janksiegowy.backend.invoice_line;

import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.financial.TaxMethod;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineSumDto;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.period.QuarterPeriod;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface InvoiceLineQueryRepository {

    List<InvoiceLineDto> findAll( List<TaxMethod> taxMetod);

    List<InvoiceLineSumDto> findByInvoiceId( UUID id);

    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds,
            List<InvoiceRegisterKind> purchaseKinds,
            LocalDate startDate, LocalDate endDate);

    default List<JpaInvoiceSumDto> findByKindAndPeriodGroupByType( Period period) {
        return findByKindAndPeriodGroupByType( period.getBegin(), period.getEnd());
    };

    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByType( LocalDate startDate, LocalDate endDate);


    default List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds,
            List<InvoiceRegisterKind> purchaseKinds,
            QuarterPeriod period) {
        return sumByKindAndPeriodGroupByRate( salesKinds, purchaseKinds, period.getBegin(), period.getEnd());
    }

    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByRate(
            List<InvoiceRegisterKind> salesKinds,
            List<InvoiceRegisterKind> purchaseKinds,
            LocalDate startDate, LocalDate endDate
    );

    default List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            QuarterPeriod period){
        return sumByKindAndPeriodGroupByType( purchaseKinds, period.getBegin(), period.getEnd());
    }

    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByType(
            List<InvoiceRegisterKind> purchaseKinds, LocalDate startDate, LocalDate endDate);

    default List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            InvoiceRegisterKind salesKind, QuarterPeriod period) {
        return sumSalesByKindAndPeriodGroupByRate( salesKind, period.getBegin(), period.getEnd());
    }

    List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            InvoiceRegisterKind salesKind, LocalDate startDate, LocalDate endDate);


    default List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            List<InvoiceRegisterKind> purchaseKinds, QuarterPeriod period) {
        return sumPurchaseByKindAndItemTypeGroupByType( purchaseKinds, period.getBegin(), period.getEnd());
    }

    List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            List<InvoiceRegisterKind> purchaseKinds, LocalDate startDate, LocalDate endDate);


    default List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( QuarterPeriod period) {
        return sumPurchaseByTypeAndPeriodGroupByType( period.getBegin(), period.getEnd());
    };
    default List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( MonthPeriod period) {
        return sumPurchaseByTypeAndPeriodGroupByType( period.getBegin(), period.getEnd());
    };

    List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( LocalDate startDate, LocalDate endDate);
}
