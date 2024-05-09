package pl.janksiegowy.backend.invoice_fop.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;

public interface TemplateFunction {

    static Proxy create() {
        return new Proxy();
    }

    String getCode();
    String getLabel();
    String getFunction();
    int getTop();
    int getLeft();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateFunction {

        private String code;
        private String label;
        private String function;
        private int top;
        private int left;


        @Override public String getCode() {
            return code;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override public String getFunction() {
            return function;
        }

        @Override public int getTop() {
            return top;
        }

        @Override public int getLeft() {
            return left;
        }
    }

}
