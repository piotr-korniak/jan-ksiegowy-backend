package pl.janksiegowy.backend.statement;

import jakarta.xml.bind.annotation.XmlSeeAlso;
import pl.gov.crd.wzor._2023._06._29._12648.Faktura_FA_2;
import pl.gov.crd.wzor._2023._06._29._12648.TRachunekBankowy;

import java.util.List;

@XmlSeeAlso( {Faktura_FA_2.class})
public abstract class Invoice_FA {

    public abstract void addBankAccount( String name, String number);
}
