package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.declaration.DeclarationType;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.Settlement;
import pl.janksiegowy.backend.finances.settlement.SettlementKind;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlClearingRepository extends JpaRepository<Clearing, ClearingId> {
   List<Clearing> findByReceivableId( UUID settlementId);
   List<Clearing> findByPayableId( UUID settlementId);
}

interface SqlClearingQueryRepository extends ClearingQueryRepository, Repository<Clearing, ClearingId> {
    @Override
    @Query( value= "SELECT C FROM Clearing C "+
            "LEFT JOIN PayableDeclaration P ON C.payable.settlementId= P.statementId " +
            "WHERE P.type= :type ")
    List<ClearingDto> findByPayable( UUID documentId, DeclarationType type);

    @Override
    @Query( value= "SELECT C FROM Clearing C "+
            "LEFT JOIN PayableDeclaration P ON C.payable.settlementId= P.statementId " +
            "WHERE P.type= :type AND C.date> P.due")
    List<ClearingDto> findByPayableAfterDue( UUID documentId, DeclarationType type);


    @Override
    @Query( value= "SELECT COUNT( C) > 0 FROM Clearing C "+
            "LEFT OUTER JOIN Settlement S "+
            "ON C.receivable= S "+
            "WHERE S.entity.taxNumber= :taxNumber "+
            "AND S.number= :number AND C.date= :date")
    boolean existReceivable( String number, String taxNumber, LocalDate date );

    @Override
    @Query( value= "SELECT COUNT( C) > 0 FROM Clearing C "+
            "LEFT OUTER JOIN Settlement S "+
            "ON C.payable= S "+
            "WHERE S.entity.taxNumber= :taxNumber "+
            "AND S.number= :number AND C.date= :date")
    boolean existPayable( String number, String taxNumber, LocalDate date );
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ClearingRepositoryImpl implements ClearingRepository {

    private final SqlClearingRepository repository;

    @Override public Clearing save( Clearing clearing) {
        return repository.save( clearing);
    }


    @Override public List<Clearing> receivableId( UUID settlementId ) {
        return repository.findByReceivableId( settlementId);
        //return null;
    }

    @Override public List<Clearing> payableId( UUID settlementId) {
        return repository.findByPayableId( settlementId);
        //return null;
    }

    @Override public void delete(Clearing clearing) {
        System.err.println( "Delete clearing... receivable: "+ clearing.getReceivableId());
        System.err.println( "Delete clearing...    payable: "+ clearing.getPayableId());

        repository.delete( clearing);
    }

}
