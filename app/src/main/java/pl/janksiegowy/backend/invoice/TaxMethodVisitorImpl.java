package pl.janksiegowy.backend.invoice;

import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.shared.financial.TaxMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxMethodVisitorImpl implements TaxMethod.TaxMethodVisitor<InvoiceLine> {

    private final InvoiceLine line;
    private final TaxedAmount amount;

    public TaxMethodVisitorImpl( InvoiceLine line) {
        this.line= line;
        this.amount= line.getAmount();
    }

    /** Normal, no change   */
    @Override public InvoiceLine visitNL() {
        return line.setBase( amount.net())
                .setCit( amount.net())
                .setVat( amount.tax());
    }

    /** Not CIT */
    @Override public InvoiceLine visitNC() {
        return line.setBase( amount.net())
                .setCit( BigDecimal.ZERO)
                .setVat( amount.tax());
    }

    /** VAT to CIT */
    @Override public InvoiceLine visitVC() {
        return line.setBase( BigDecimal.ZERO)
                .setCit( amount.gross())
                .setVat( BigDecimal.ZERO);
    }

    /** VAT 50% */
    @Override public InvoiceLine visitV5() {
        var vat= amount.tax( "0.5");

        return line.setBase( amount.net( "0.5"))
                .setVat( vat)
                .setCit( amount.gross().subtract( vat));
    }
    @Override public InvoiceLine visitC7() {
        var vat= amount.tax( "0.5");

        return line.setBase( amount.net( "0.5"))
                .setVat( vat)
                .setCit( amount.gross().subtract( vat)
                        .multiply( new BigDecimal("0.75"))
                        .setScale( 2, RoundingMode.HALF_DOWN));
    }
}
