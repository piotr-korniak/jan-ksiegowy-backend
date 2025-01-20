package pl.janksiegowy.backend.period;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.period.tax.CIT;
import pl.janksiegowy.backend.period.tax.JPK;
import pl.janksiegowy.backend.period.tax.PIT;
import pl.janksiegowy.backend.period.tax.VAT;

import java.time.LocalDate;
@Getter

@Entity
@Table( name= "PERIODS")
@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
public abstract class Period {

    @Id
    private String id;

    private LocalDate begin;
    @Column( name= "\"END\"")
    private LocalDate end;

  //  @ManyToOne
  //  private Period parent;

    @Column( insertable= false, updatable= false)
    @Enumerated( EnumType.STRING)
    private PeriodType type;

    private boolean pit;
    private boolean cit;
    private boolean vat;
    private boolean jpk;

    public Period setBegin( LocalDate begin) {
        this.begin= begin;
        return this;
    }

    public Period setEnd( LocalDate end) {
        this.end= end;
        return this;
    }

    public Period setTax( PIT pit, JPK jpk, CIT cit, VAT vat){
        this.pit= pit.isPit();
        this.jpk= jpk.isJpk();
        this.cit= cit.isCit();
        this.vat= vat.isVat();
        return this;
    }

    public Period setId( String id) {
        this.id= id;//new PeriodId( id);
        return this;
    }

    public Period setMax( LocalDate max) {
        if( end.isAfter( max))
            end= max;
        return this;
    }

    public Period setMin( LocalDate min) {
        if( begin.isBefore( min))
            begin= min;
        return this;
    }

    public abstract <T> T accept( PeriodVisitor<T> visitor);

    public interface PeriodVisitor<T> {
        T visit( AnnualPeriod annualPeriod);
        T visit( QuarterPeriod quarterPeriod);
        T visit( MonthPeriod monthPeriod);

    }


}
