package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface SqlPaymentRepository extends JpaRepository<Payment, UUID> {
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
class PaymentRepositoryImpl implements PaymentRepository {

    private final SqlPaymentRepository repository;
    @Override public Payment save( Payment payment) {
        return repository.save( payment);
    }
}