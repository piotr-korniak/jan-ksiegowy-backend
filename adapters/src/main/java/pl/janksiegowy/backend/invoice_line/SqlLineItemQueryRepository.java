package pl.janksiegowy.backend.invoice_line;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.janksiegowy.backend.shared.financial.TaxMetod;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineSumDto;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.QuarterPeriod;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.util.List;
import java.util.UUID;


@org.springframework.stereotype.Repository
public interface SqlLineItemQueryRepository extends InvoiceLineQueryRepository, Repository<InvoiceLine, UUID> {

    @Override
    @Query( value= "SELECT L.invoice.settlement.number AS invoiceNumber, " +
                    "L.invoice.settlement.entity.name AS contactName, SUM(L.tax) AS tax "+
                    "FROM InvoiceLine L "+
                    "WHERE L.item.taxMetod IN :taxMetod "+
                    "GROUP BY L.invoice, invoiceNumber, contactName ")
    List<InvoiceLineDto> findAll( List<TaxMetod> taxMetod);

    @Override
    @Query( value= "SELECT L.invoice.invoiceId AS invoiceId, " +
                    "TREAT( L.invoice as CustomerInvoice).vatRegister.kind AS salesKind, " +
                    "TREAT( L.invoice as SupplierInvoice).vatRegister.kind AS purchaseKind, " +
                    "L.taxRate AS rate," +
                    "SUM( L.base) AS base, SUM( L.vat) AS vat " +
                    "FROM InvoiceLine L "+
                    "WHERE L.invoice.invoiceId= :id "+
                    "GROUP BY invoiceId, salesKind, purchaseKind, rate")
    List<InvoiceLineSumDto> findByInvoiceId( UUID id);

    @Override
    @Query( value= "SELECT L.invoice.invoiceId AS invoiceId, " +
                    "L.invoice.settlement.number AS invoiceNumber, "+
                    "L.invoice.settlement.entity.name AS entityName, "+
                    "L.invoice.settlement.entity.taxNumber AS taxNumber, "+
                    "L.invoice.settlement.entity.country AS entityCountry, "+
                    "L.invoice.invoiceDate AS invoiceDate, "+
                    "L.invoice.settlement.date AS issueDate, " +
                    "CASE WHEN L.invoice.type= 'C' " +
                    "   THEN TREAT(L.invoice AS CustomerInvoice).vatRegister.kind ELSE NULL " +
                    "END AS salesKind, " +
                    "CASE WHEN L.invoice.type= 'S' " +
                    "   THEN TREAT(L.invoice AS SupplierInvoice).vatRegister.kind ELSE NULL " +
                    "END AS purchaseKind, " +
                    "L.item.type AS itemType, "+
                    "L.taxRate AS taxRate, " +
                    "SUM( L.base) AS base, SUM( L.vat) AS vat " +
                    "FROM InvoiceLine L "+
                    "WHERE vat!=0 AND L.invoice.settlement.period.id= :period AND " +
                    "((L.invoice.type='C' AND "+
                    "  TREAT(L.invoice AS CustomerInvoice).vatRegister.kind IN :salesKinds) OR"+
                    " (L.invoice.type='S' AND "+
                    "  TREAT(L.invoice AS SupplierInvoice).vatRegister.kind IN :purchaseKinds)) "+
                    "GROUP BY invoiceId, invoiceNumber, entityName, taxNumber, "+
                            "entityCountry, invoiceDate, issueDate, salesKind, purchaseKind, "+
                            "taxRate, itemType "+
                    "ORDER BY invoiceDate ASC ")
    List<JpaInvoiceSumDto> findByKindAndPeriodIdGroupByRate(
            @Param( "salesKinds") List<InvoiceRegisterKind> salesKinds,
            @Param( "purchaseKinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "period") String periodId);

    @Override
    @Query( value= "SELECT L.taxRate AS taxRate, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "L.invoice.settlement.period.parent= :period AND " +
            "(L.invoice.type='C' AND TREAT(L.invoice AS CustomerInvoice).vatRegister.kind= :kind)" +
            "GROUP BY taxRate")
    List<JpaInvoiceSumDto> sumSalesByKindAndPeriodGroupByRate(
            @Param( "kind") InvoiceRegisterKind kind,
            @Param( "period") QuarterPeriod quarterPeriod);

    @Override
    @Query( value= "SELECT L.item.type AS itemType, " +
            "TREAT(L.invoice AS CustomerInvoice).vatRegister.kind AS invoiceRegisterKind, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "L.invoice.settlement.period.parent.id= :period AND " +
            "(L.invoice.type='C' AND TREAT(L.invoice AS CustomerInvoice).vatRegister.kind IN :kinds)" +
            "GROUP BY invoiceRegisterKind, itemType")
    List<JpaInvoiceSumDto> sumSalesByKindAndItemTypeGroupByType(
            @Param( "kinds") List<InvoiceRegisterKind> salesKinds,
            @Param( "period") String quarterPeriod);

    @Override
    @Query( value= "SELECT L.item.type AS itemType, " +
            "TREAT(L.invoice AS SupplierInvoice).vatRegister.kind AS purchaseKind, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "L.invoice.settlement.period.parent= :period AND " +
            "(L.invoice.type='S' AND TREAT(L.invoice AS SupplierInvoice).vatRegister.kind IN :kinds)" +
            "GROUP BY purchaseKind, itemType")
    List<JpaInvoiceSumDto> sumPurchaseByKindAndItemTypeGroupByType(
            @Param( "kinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "period") QuarterPeriod quarterPeriod);


    @Override
    @Query( value= "SELECT L.taxRate AS rate, " +
            "CASE WHEN L.invoice.type= 'C' " +
            "   THEN TREAT(L.invoice AS CustomerInvoice).vatRegister.kind ELSE NULL " +
            "END AS salesKind, " +
            "CASE WHEN L.invoice.type= 'S' " +
            "   THEN TREAT(L.invoice AS SupplierInvoice).vatRegister.kind ELSE NULL " +
            "END AS purchaseKind, " +
            "SUM(L.base) AS base, SUM(L.vat) AS vat " +
            "FROM InvoiceLine L " +
            "WHERE vat != 0 AND " +
            "L.invoice.settlement.period.parent= :period AND " +
            "((L.invoice.type='C' AND "+
            "  TREAT(L.invoice AS CustomerInvoice).vatRegister.kind IN :salesKinds) OR" +
            " (L.invoice.type='S' AND "+
            "  TREAT(L.invoice AS SupplierInvoice).vatRegister.kind IN :purchaseKinds)) " +
            "GROUP BY salesKind, purchaseKind, L.taxRate")
    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByRate(
            @Param( "salesKinds") List<InvoiceRegisterKind> salesKinds,
            @Param( "purchaseKinds") List<InvoiceRegisterKind> purchaseKinds,
            @Param( "period") QuarterPeriod period);

    @Override
    @Query( value= "SELECT L.invoice.invoiceId AS invoiceId, " +
                    "L.invoice.settlement.number AS invoiceNumber, "+
                    "L.invoice.settlement.entity.name AS entityName, "+
                    "L.invoice.settlement.entity.taxNumber AS taxNumber, "+
                    "L.invoice.settlement.entity.country AS entityCountry, "+
                    "L.invoice.invoiceDate AS invoiceDate, "+
                    "L.invoice.settlement.date AS issueDate, "+
                    "L.item.type AS itemType, "+
                    "SUM( L.base) AS base, SUM( L.vat) AS vat " +
                    "FROM InvoiceLine L "+
                    "WHERE vat!=0 AND L.invoice.settlement.period.id= :period AND "+
                    "L.invoice.type='S' "+
                    "GROUP BY invoiceId, invoiceNumber, entityName, taxNumber, " +
                            "entityCountry, invoiceDate, issueDate, itemType "+
                    "ORDER BY invoiceDate ASC ")
    List<JpaInvoiceSumDto> findByKindAndPeriodGroupByType( @Param( "period") String periodId);

    @Override
    @Query( value= "SELECT L.item.type AS itemType, "+
            "CASE WHEN L.invoice.type= 'S' " +
            "   THEN TREAT(L.invoice AS SupplierInvoice).vatRegister.kind ELSE NULL " +
            "END AS purchaseKind, " +
            "SUM( L.base) AS base, SUM( L.vat) AS vat " +
            "FROM InvoiceLine L "+
            "WHERE vat!=0 AND L.invoice.settlement.period.parent= :period AND "+
            "(L.invoice.type='S' AND "+
            "  TREAT(L.invoice AS SupplierInvoice).vatRegister.kind IN :purchaseKinds) " +
            "GROUP BY purchaseKind, itemType ")
    List<JpaInvoiceSumDto> sumByKindAndPeriodGroupByType(
            List<InvoiceRegisterKind> purchaseKinds,
            QuarterPeriod period);

    @Override
    @Query( value= "SELECT L.item.type AS itemType, "+
            "SUM( L.base) AS base, SUM( L.vat) AS vat " +
            "FROM InvoiceLine L "+
            "WHERE vat!=0 AND L.invoice.settlement.period.parent= :quarterPeriod AND " +
            "L.invoice.type='S' "+
            "GROUP BY itemType ")
    List<JpaInvoiceSumDto> sumPurchaseByTypeAndPeriodGroupByType( QuarterPeriod quarterPeriod);
}
