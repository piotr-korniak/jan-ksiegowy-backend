package pl.janksiegowy.backend.finances.document;

import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

public class DocumentConverter implements SettlementType.SettlementTypeVisitor<TemplateType> {

    @Override public TemplateType visitPurchaseInvoice() {
        return TemplateType.IP;
    }
    @Override public TemplateType visitSalesInvoice() {
        return TemplateType.IS;
    }

    @Override public TemplateType visitPaymentReceipt() {
        return TemplateType.PR;
    }
    @Override public TemplateType visitPaymentExpense() {
        return TemplateType.PE;
    }

    @Override public TemplateType visitVatStatement() {
        return TemplateType.SV;
    }
    @Override public TemplateType visitCitStatement() {
        return TemplateType.SC;
    }
    @Override
    public TemplateType visitPitStatement() {
        return TemplateType.SP;
    }
    @Override
    public TemplateType visitDraStatement() {
        return TemplateType.HD;
    }

    @Override
    public TemplateType visitPayslipSettlement() {
        return TemplateType.EP;
    }

    @Override
    public TemplateType visitLevyCharge() {
        return TemplateType.CL;
    }
    @Override
    public TemplateType visitFeeCharge() {
        return TemplateType.CF;
    }

    @Override public TemplateType visitReceiveNote() {
        return TemplateType.NR;
    }
    @Override
    public TemplateType visitIssuedNote() {
        return TemplateType.NI;
    }
}
