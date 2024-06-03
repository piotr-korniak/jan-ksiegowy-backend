package pl.janksiegowy.backend.statement;

import pl.janksiegowy.backend.invoice.SalesInvoice;


public abstract class Factory_FA {

    public static Factory_FA create() {
        return new Factory_FA_2_v1_0e();
    }

    public abstract Invoice_FA prepare(SalesInvoice invoice);

}
