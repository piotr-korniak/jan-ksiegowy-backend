package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class Receivable extends Settlement {

    @OneToMany( mappedBy = "receivable", cascade = CascadeType.ALL)
    private List<Rozrachowanie> receivables= new ArrayList<>();

    public void add( Rozrachowanie rozrachowanie ) {
        receivables.add( rozrachowanie);
    }
}
