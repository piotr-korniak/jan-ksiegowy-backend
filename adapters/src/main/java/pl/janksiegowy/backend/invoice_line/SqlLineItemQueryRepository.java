package pl.janksiegowy.backend.invoice_line;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.janksiegowy.backend.shared.financial.TaxMethod;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineSumDto;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.QuarterPeriod;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@org.springframework.stereotype.Repository
public interface SqlLineItemQueryRepository extends InvoiceLineQueryRepository, Repository<InvoiceLine, UUID> {

    @Override
    @Query( value= "SELECT L.invoice.number AS invoiceNumber, " +
                    "L.invoice.entity.name AS contactName, SUM(L.tax) AS tax "+
                    "FROM InvoiceLine L "+
                    "WHERE L.item.taxMetod IN :taxMetod "+
                    "GROUP BY L.invoice, invoiceNumber, contactName ")
    List<InvoiceLineDto> findAll( List<TaxMethod> taxMetod);

    @Override
    @Query( value= "SELECT L.invoice.documentId AS invoiceId, " +
                    "TREAT( L.invoice as SalesInvoice).register.kind AS salesKind, " +
                    "TREAT( L.invoice as PurchaseInvoice).register.kind AS purchaseKind, " +
                    "L.taxRate AS rate," +
                    "SUM( L.base) AS base, SUM( L.vat) AS vat " +
                    "FROM InvoiceLine L "+
                    "WHERE L.invoice.documentId= :id "+
                    "GROUP BY invoiceId, salesKind, purchaseKind, rate")
    List<InvoiceLineSumDto> findByInvoiceId( UUID id);
/*
    @Override
    @Query( value= "SELECT L.invoice.documentId AS invoiceId, " +
                    "L.invoice.number AS invoiceNumber, "+
                    "L.invoice.entity.name AS entityName, "+
                    "L.invoice.entity.taxNumber AS taxNumber, "+
                    "L.invoice.entity.country AS entityCountry, "+
                    "L.invoice.invoiceDate AS invoiceDate, "+
                    "L.invoice.issueDate AS issueDate, "+
                    "CASE WHEN TYPE( L.invoice) = SalesInvoice "+
                    "   THEN TREAT( L.invoice AS SalesInvoice).register.kind ELSE NULL " +
                    "END AS salesKind, " +
                    "CASE WHEN TYPE( L.invoice)= PurchaseInvoice " +
                    "   THEN TREAT( L.invoice AS PurchaseInvoice).register.kind ELSE NULL " +
                    "END AS purchaseKind, " +
                    "L.item.type AS itemType, "+
                    "L.taxRate AS taxRate, " +
                    "SUM( L.base) AS base, SUM( L.vat) AS vat " +
                    "FROM InvoiceLine L "+
                    "WHERE vat!=0 AND L.invoice.invoicePeriodId= :period AND " +
                    "((TYPE( L.invoice) = SalesInvoice AND "+
                    "  TREAT( L.invoice AS SalesInvoice).register.kind IN :salesKinds) OR"+
                    " (TYPE( L.invoice) = PurchaseInvoice AND "+
                    "  TREAT(L.invoice AS PurchaseInvoice).register.kind IN :purchaseKinds)) "+
                    "GROUP BY invoiceId, invoiceNumber, entityName, taxNumber, "+
                            "entityCountry, invoiceDate, issueDate, salesKind, purchaseKind, "+
                            "taxRate, itemType "+
                    "ORDER BY issueDate ASC, invoiceDate ASC")
    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByRate(
            @Param( "salesKinds") List<InvoiceRegisterKind> salesKinds,
            @Param( "purchaseKinds") List<InvoiceRegisterKind> purchaseKinds,
            String period);
*/
    @Query( value= "SELECT L.invoice.documentId AS invoiceId, " +
            "L.invoice.number AS invoiceNumber, " +
            "L.invoice.entity.name AS entityName, " +
            "L.invoice.entity.taxNumber AS taxNumber, " +
            "L.invoice.entity.country AS entityCountry, " +
            "L.invoice.invoiceDate AS invoiceDate, " +
            "L.invoice.issueDate AS issueDate, " +
            "CASE WHEN TYPE( L.invoice) = SalesInvoice " +
            "   THEN TREAT( L.invoice AS SalesInvoice).register.kind ELSE NULL " +
            "END AS salesKind, " +
            "CASE WHEN TYPE( L.invoice)= PurchaseInvoice " +
            "   THEN TREAT( L.invoice AS PurchaseInvoice).register.kind ELSE NULL " +
            "END AS purchaseKind, " +
            "L.item.type AS itemType, " +
            "L.taxRate AS taxRate, " +
            "SUM( L.base) AS base, SUM( L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat!=0 AND " +
            "((TYPE( L.invoice) = SalesInvoice AND " +
            "  LEAST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate AND " +
            "  TREAT( L.invoice AS SalesInvoice).register.kind IN :salesKinds) OR" +
            " (TYPE( L.invoice) = PurchaseInvoice AND " +
            "  GREATEST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate AND" +
            "  TREAT(L.invoice AS PurchaseInvoice).register.kind IN :purchaseKinds)) " +
            "GROUP BY invoiceId, invoiceNumber, entityName, taxNumber, " +
            "entityCountry, invoiceDate, issueDate, salesKind, purchaseKind, " +
            "taxRate, itemType " +
            "ORDER BY issueDate ASC, invoiceDate ASC")
    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByRate(
            @Param( "salesKinds") List<InvoiceRegisterKind> salesKinds,
            @Param( "purchaseKinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);

    @Override
    @Query( value= "SELECT L.taxRate AS taxRate, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "(TYPE( L.invoice)= SalesInvoice AND " +
            " LEAST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate AND " +
            " TREAT( L.invoice AS SalesInvoice).register.kind= :kind)" +
            "GROUP BY taxRate")
    List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            @Param( "kind") InvoiceRegisterKind kind,
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);
/*
    @Override
    @Query( value= "SELECT L.taxRate AS taxRate, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "L.invoice.invoicePeriod= :period AND " +
            "(TYPE( L.invoice)= SalesInvoice AND" +
            " TREAT( L.invoice AS SalesInvoice).register.kind= :kind)" +
            "GROUP BY taxRate")
    List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            @Param( "kind") InvoiceRegisterKind salesKind,
            @Param( "period") MonthPeriod period);
*/

    @Override
    @Query( value= "SELECT L.item.type AS itemType, " +
            "TREAT( L.invoice AS PurchaseInvoice).register.kind AS purchaseKind, " +
            "SUM( L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "(TYPE( L.invoice)= PurchaseInvoice AND " +
            " LEAST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate AND " +
            " TREAT( L.invoice AS PurchaseInvoice).register.kind IN :kinds)" +
            "GROUP BY purchaseKind, itemType")
    List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            @Param( "kinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);
/*
    @Override
    @Query( value= "SELECT L.item.type AS itemType, " +
            "TREAT( L.invoice AS PurchaseInvoice).register.kind AS purchaseKind, " +
            "SUM( L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "L.invoice.invoicePeriod= :period AND " +
            "(TYPE( L.invoice)= PurchaseInvoice AND" +
            " TREAT( L.invoice AS PurchaseInvoice).register.kind IN :kinds)" +
            "GROUP BY purchaseKind, itemType")
    List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            @Param( "kinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "period") MonthPeriod period);
*/
    @Override
    @Query( value= "SELECT L.taxRate AS rate, " +
            "CASE WHEN TYPE( L.invoice) = SalesInvoice " +
            "   THEN TREAT( L.invoice AS SalesInvoice).register.kind ELSE NULL " +
            "END AS salesKind, " +
            "CASE WHEN TYPE( L.invoice) = PurchaseInvoice " +
            "   THEN TREAT( L.invoice AS PurchaseInvoice).register.kind ELSE NULL " +
            "END AS purchaseKind, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "(( TYPE( L.invoice) = SalesInvoice AND" +
            "   LEAST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate AND "+
            "  TREAT(L.invoice AS SalesInvoice).register.kind IN :salesKinds) OR" +
            " ( TYPE( L.invoice) = PurchaseInvoice AND " +
            "   GREATEST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate AND"+
            "  TREAT(L.invoice AS PurchaseInvoice).register.kind IN :purchaseKinds)) " +
            "GROUP BY salesKind, purchaseKind, L.taxRate")
    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByRate(
            @Param( "salesKinds") List<InvoiceRegisterKind> salesKinds,
            @Param( "purchaseKinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);

    @Override
    @Query( value= "SELECT L.invoice.documentId AS invoiceId, " +
                    "L.invoice.number AS invoiceNumber, "+
                    "L.invoice.entity.name AS entityName, "+
                    "L.invoice.entity.taxNumber AS taxNumber, "+
                    "L.invoice.entity.country AS entityCountry, "+
                    "L.invoice.invoiceDate AS invoiceDate, "+
                    "L.invoice.issueDate AS issueDate, "+
                    "L.item.type AS itemType, "+
                    "SUM( L.base) AS base, SUM( L.vat) AS vat " +
                    "FROM InvoiceLine L "+
                    "WHERE vat!=0 AND "+
                    "TYPE( L.invoice)= PurchaseInvoice AND " +
                    "  GREATEST(L.invoice.invoiceDate, L.invoice.issueDate) BETWEEN :startDate AND :endDate "+
                    "GROUP BY invoiceId, invoiceNumber, entityName, taxNumber, " +
                            "entityCountry, invoiceDate, issueDate, itemType "+
                    "ORDER BY issueDate ASC, invoiceDate ASC ")
    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByType(
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);

    @Override
    @Query( value= "SELECT L.item.type AS itemType, "+
            "CASE WHEN TYPE( L.invoice)= PurchaseInvoice " +
            "   THEN TREAT( L.invoice AS PurchaseInvoice).register.kind ELSE NULL " +
            "END AS purchaseKind, " +
            "SUM( L.base) AS base, SUM( L.vat) AS vat " +
            "FROM InvoiceLine L "+
            "WHERE vat!=0 AND L.invoice.issueDate BETWEEN :startDate AND :endDate AND "+
            "(TYPE( L.invoice) = PurchaseInvoice AND "+
            " TREAT( L.invoice AS PurchaseInvoice).register.kind IN :purchaseKinds) " +
            "GROUP BY purchaseKind, itemType ")
    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);

    @Override
    @Query( value= "SELECT L.item.type AS itemType, "+
            "SUM( L.base) AS base, SUM( L.vat) AS vat " +
            "FROM InvoiceLine L "+
            "WHERE vat!=0 AND L.invoice.issueDate BETWEEN :startDate AND :endDate AND " +
            "TYPE( L.invoice) = PurchaseInvoice "+
            "GROUP BY itemType ")
    List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType(
            @Param( "startDate") LocalDate startDate,
            @Param( "endDate") LocalDate endDate);
/*
    @Override
    @Query( value= "SELECT L.item.type AS itemType, "+
            "SUM( L.base) AS base, SUM( L.vat) AS vat " +
            "FROM InvoiceLine L "+
            "WHERE vat!=0 AND L.invoice.invoicePeriod= :period AND " +
            "TYPE( L.invoice) = PurchaseInvoice "+
            "GROUP BY itemType ")
    List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( MonthPeriod period);
*/
}
