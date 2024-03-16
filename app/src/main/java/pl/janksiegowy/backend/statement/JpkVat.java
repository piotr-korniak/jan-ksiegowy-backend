package pl.janksiegowy.backend.statement;

import jakarta.xml.bind.annotation.XmlSeeAlso;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;

import java.math.BigDecimal;
import java.math.BigInteger;


@XmlSeeAlso( {Ewidencja_JPK_V7K_2_v1_0e.class})
public abstract class JpkVat {

    public abstract BigInteger getP51();

    public abstract BigInteger getVatNalezny();

    public abstract BigInteger getVatNaliczony();

    public abstract BigDecimal korektaNaleznego();
    public abstract BigDecimal korektaNaliczonego();

}
