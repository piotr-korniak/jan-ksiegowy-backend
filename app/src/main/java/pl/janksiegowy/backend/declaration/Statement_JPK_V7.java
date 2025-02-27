package pl.janksiegowy.backend.declaration;

import jakarta.xml.bind.annotation.XmlSeeAlso;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e;

import java.math.BigDecimal;

@XmlSeeAlso( {Ewidencja_JPK_V7K_2_1_0e.class})
public abstract class Statement_JPK_V7 {
    public abstract BigDecimal getZobowiazanie();

    public abstract BigDecimal getVatNalezny();

    public abstract BigDecimal getVatNaliczony();

}
