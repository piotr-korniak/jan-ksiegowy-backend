package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface SqlClearingRepository extends JpaRepository<Clearing, ClearingId> {
    List<Clearing> findByReceivable( Settlement settlement);

    List<Clearing> findByPayable( Settlement settlement);
}

interface SqlClearingQueryRepository extends ClearingQueryRepository, Repository<Clearing, ClearingId> {

    @Override
    @Query( value= "SELECT COUNT( C) > 0 FROM Clearing C "+
            "WHERE C.receivable.entity.taxNumber= :taxNumber "+
            "AND C.receivable.number= :number AND C.date= :date")
    boolean existReceivable( String number, String taxNumber, LocalDate date );

    @Override
    @Query( value= "SELECT COUNT( C) > 0 FROM Clearing C "+
            "WHERE C.payable.entity.taxNumber= :taxNumber "+
            "AND C.payable.number= :number AND C.date= :date")
    boolean existPayable( String number, String taxNumber, LocalDate date );
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ClearingRepositoryImpl implements ClearingRepository {

    private final SqlClearingRepository repository;

    @Override public Clearing save( Clearing clearing) {
        return repository.save( clearing);
    }

    @Override public List<Clearing> receivable( Settlement settlement ) {
        return repository.findByReceivable( settlement);
    }

    @Override public List<Clearing> payable( Settlement settlement ) {
        return repository.findByPayable( settlement);
    }
}
