package pl.janksiegowy.backend.billing.agreement;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pl.janksiegowy.backend.billing.agreement.dto.AgreementDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlAgreementRepository extends JpaRepository<Agreement, Long> {

    public Optional<Agreement> findAgreementByAgreementIdAndDate( UUID agreementId, LocalDate date);

    @Query( value= "SELECT A "+
            "FROM Agreements A "+
            "WHERE (A.billing_type_mask & :groupMask) > 0 "+
            "AND A.status = 'ACTIVE' "+
            "AND A.start_date <= :periodEnd "+
            "AND (A.end_date IS NULL OR A.end_date >= :periodStart)", nativeQuery= true)
    List<Agreement> findCandidates(
            @Param( "groupMask") long groupMask,
            @Param( "periodStart") LocalDate periodStart,
            @Param( "periodEnd") LocalDate periodEnd
    );
}

interface SqlAgreementQueryRepository extends AgreementQueryRepository, Repository<Agreement, Long> {

    @Override
    @Query( value= "SELECT M " +
            "FROM Agreement M " +
            "LEFT OUTER JOIN Agreement P "+
            "ON M.agreementId= P.agreementId AND M.date < P.date "+
            "WHERE M.entity.taxNumber= :taxNumber AND P.date IS NULL")
    Optional<AgreementDto> findByTaxNumber( String taxNumber);

    @Override
    @Query( value= "SELECT M " +
            "FROM Agreement M " +
            "LEFT OUTER JOIN Agreement P "+
            "ON M.agreementId= P.agreementId AND (P.date <= :date AND M.date < P.date) "+
            "WHERE FUNCTION('bitand', M.billingTypeMask, :groupMask)> 0 " +
            "AND M.status= :status "+
            "AND M.date <= :date AND P.date IS NULL ")
    List<AgreementDto> findCandidates( @Param( "groupMask") long groupMask,
                                       @Param( "date") LocalDate date,
                                       @Param( "status") String status);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class AgreementRepositoryImpl implements AgreementRepository     {

    private final SqlAgreementRepository repository;

    @Override
    public Agreement save( Agreement agreement) {
        return repository.save( agreement);
    }

    @Override
    public Optional<Agreement> findAgreementByAgreementIdAndDate( UUID agreementId, LocalDate date) {
        return repository.findAgreementByAgreementIdAndDate( agreementId, date);
    }

    @Override
    public List<Agreement> findCandidates( long groupMask, LocalDate periodStart, LocalDate periodEnd) {
        return repository.findCandidates( groupMask, periodStart, periodEnd);
    }
}