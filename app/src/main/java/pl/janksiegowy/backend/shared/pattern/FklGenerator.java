package pl.janksiegowy.backend.shared.pattern;

import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.shared.pattern.PatternId.PatternIdVisitor;

public abstract class FklGenerator implements PatternIdVisitor<String> {
    @Override public String visit_FA_2_v1_0e() {
        return null;
    }

    public void fa( Invoice invoice) {

        var dupa= new XmlGenerator<Object>() {

            @Override
            public Object visit_FA_2_v1_0e() {
                return null;
            }
        };

    }
}
