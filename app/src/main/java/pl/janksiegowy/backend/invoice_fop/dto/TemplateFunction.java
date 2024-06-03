package pl.janksiegowy.backend.invoice_fop.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;

public interface TemplateFunction {

    static Proxy create() {
        return new Proxy();
    }

    String getCode();
    String getLabel();
    String getFunction();
    Integer getTop();
    Integer getLeft();
    Integer getWidth();
    String getColor();
    String getBackgroundColor();
    String getFontWeight( );
    Integer getPadding();
    Integer getPaddingTop();
    Integer getPaddingLeft();
    Integer getPaddingRight();
    Integer getFontSize();
    String getTextAlign();
    String getBorderBottom();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateFunction {

        private String code;
        private String label;
        private String function;
        private Integer top;
        private Integer left;
        private Integer width;
        private String color;
        private String backgroundColor;
        private String fontWeight;
        private Integer padding;
        private Integer paddingTop;
        private Integer paddingLeft;
        private Integer paddingRight;
        private Integer fontSize;
        private String textAlign;
        private String borderBottom;

        @Override public String getCode() {
            return code;
        }
        @Override public String getLabel() {
            return label;
        }
        @Override public String getFunction() {
            return function;
        }
        @Override public Integer getTop() {
            return top;
        }
        @Override public Integer getLeft() {
            return left;
        }
        @Override public Integer getWidth() {
            return width;
        }
        @Override public String getColor() {
            return color;
        }
        @Override public String getBackgroundColor() {
            return backgroundColor;
        }
        @Override public String getFontWeight() {
            return fontWeight;
        }
        @Override public Integer getPadding() {
            return padding;
        }
        @Override public Integer getPaddingTop() {
            return paddingTop;
        }
        @Override public Integer getPaddingLeft() {
            return paddingLeft;
        }
        @Override public Integer getPaddingRight() {
            return paddingRight;
        }
        @Override public Integer getFontSize() {
            return fontSize;
        }
        @Override public String getTextAlign() {
            return textAlign;
        }
        @Override public String getBorderBottom() {
            return borderBottom;
        }
    }
}
