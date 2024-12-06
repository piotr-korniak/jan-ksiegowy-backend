package pl.janksiegowy.backend.shared.report;

import lombok.Getter;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;

@Getter
public class Summary {
    private BigDecimal dt= BigDecimal.ZERO;
    private BigDecimal ct= BigDecimal.ZERO;
    private int count= 0;

    public Summary addDt( BigDecimal dt) {
        count ++;
        this.dt= Util.addOrAddend( this.dt, dt);
        return this;
    }

    public Summary addCt( BigDecimal ct) {
        count ++;
        this.ct= Util.addOrAddend( this.ct, ct);
        return this;
    }

    public BigDecimal getSaldo() {
        return dt.subtract( ct);
    }
}
