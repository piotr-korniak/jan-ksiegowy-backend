package pl.janksiegowy.backend.finances.settlement;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementListDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SqlSettlementRepository extends JpaRepository<Settlement, UUID> {
}

interface SqlSettlementQueryRepository extends SettlementQueryRepository, Repository<Settlement, UUID> {

    @Override
    @Query( "SELECT result.type AS type , result.number AS number, " +
            "result.dt AS dt, result.ct AS ct, result.date AS date, result.due AS due, " +
            "result.entityAccountNumber AS entityAccountNumber," +
            "result.entityName AS entityName, result.entityType AS entityType " +
            "FROM (" +
            "   SELECT s.type AS type, s.number AS number, s.dt AS dt, s.ct AS ct, s.date AS date, s.due AS due, " +
            "      e.accountNumber AS entityAccountNumber, " +
            "      e.type AS entityType, e.name AS entityName " +
            "   FROM Settlement s JOIN s.entity e " +
            //"   WHERE s.dt <> s.ct AND (s.kind='D' OR s.kind='C') " +
            ") AS result " +
            "WHERE :zeroBalance= true OR result.dt <> result.ct " +
            "ORDER BY result.entityAccountNumber ASC, result.date DESC")
    List<SettlementListDto> findByDtNotEqualCtOrderByDateDesc( boolean zeroBalance);

    @Override
    @Query( "SELECT result.type AS type , result.number AS number, " +
            "result.dt AS dt, result.ct AS ct, result.date AS date, result.due AS due, " +
            "result.entityAccountNumber AS entityAccountNumber," +
            "result.entityName AS entityName, result.entityType AS entityType " +
            "FROM (" +
            "   SELECT s.type AS type, s.number AS number, s.dt AS dt, s.ct AS ct, s.date AS date, s.due AS due, " +
            "      e.accountNumber AS entityAccountNumber, " +
            "      e.type AS entityType, e.name AS entityName " +
            "   FROM Settlement s JOIN s.entity e " +
            "   WHERE e.type= :entityType AND e.accountNumber= :accountNumber " +
           // "   AND s.dt <> s.ct AND (s.kind='D' OR s.kind='C') " +
            ") AS result " +
            "WHERE :zeroBalance= true OR result.dt <> result.ct " +
            "ORDER BY result.entityAccountNumber ASC, result.date DESC")
    List<SettlementListDto> findByEntityTypeAndEntityAccountNumberOrderByEntityAccountNumberDesc(
            EntityType entityType, String accountNumber, boolean zeroBalance);

    @Override
    @Query( "SELECT result.type AS type , result.number AS number, " +
            "result.dt AS dt, result.ct AS ct, result.date AS date, result.due AS due, " +
            "result.entityAccountNumber AS entityAccountNumber," +
            "result.entityName AS entityName, result.entityType AS entityType " +
            "FROM (" +
            "   SELECT s.type AS type, s.number AS number, "+
            "      COALESCE( CASE WHEN s.kind= 'D' THEN s.dt ELSE SUM( c.amount) END, 0) AS dt, "+
            "      COALESCE( CASE WHEN s.kind= 'C' THEN s.ct ELSE SUM( c.amount) END, 0) AS ct, "+
            "      s.date AS date, s.due AS due, " +
            "      e.accountNumber AS entityAccountNumber, "+
            "      e.name AS entityName, e.type AS entityType "+
            "   FROM Settlement s "+
            "   LEFT JOIN Clearing c " +
            "   ON ((s.kind= 'D' AND s= c.receivable ) " +
            "   OR (s.kind = 'C' AND s= c.payable)) AND c.date <= :date " +
            "   JOIN s.entity e "+
            "   WHERE e.type= :entityType AND e.accountNumber= :accountNumber AND s.date <= :date " +
            "   GROUP BY s.type, s.number, s.kind, s.date, s.due, e.accountNumber, e.name, e.type, s.dt, s.ct "+
            ") AS result " +
            "WHERE :zeroBalance= true OR result.dt <> result.ct " +
            "ORDER BY result.date DESC ")
    List<SettlementListDto> findByEntityTypeAndEntityAccountNumerAsAtDate(
            EntityType entityType, String accountNumber, LocalDate date, boolean zeroBalance);

    @Override
    @Query( "SELECT result.type AS type , result.number AS number, " +
            "result.dt AS dt, result.ct AS ct, result.date AS date, result.due AS due, " +
            "result.entityAccountNumber AS entityAccountNumber," +
            "result.entityName AS entityName, result.entityType AS entityType " +
            "FROM (" +
            "   SELECT s.type AS type, s.number AS number, "+
            "      COALESCE( CASE WHEN s.kind= 'D' THEN s.dt ELSE SUM( c.amount) END, 0) AS dt, "+
            "      COALESCE( CASE WHEN s.kind= 'C' THEN s.ct ELSE SUM( c.amount) END, 0) AS ct, "+
            "      s.date AS date, s.due AS due, " +
            "      e.accountNumber AS entityAccountNumber, "+
            "      e.name AS entityName, e.type AS entityType " +
            "   FROM Settlement s "+
            "   LEFT JOIN Clearing c " +
            "   ON ((s.kind= 'D' AND s.dt>0 AND s= c.receivable) " +
            "   OR (s.kind= 'D' AND s.dt<0 AND s= c.payable)" +
            "   OR (s.kind= 'C' AND s.ct>0 AND s= c.payable)" +
            "   OR (s.kind= 'C' AND s.ct<0 AND s= c.receivable))" +
            " AND c.date <= :date " +
            "   JOIN s.entity e "+
            "   WHERE s.date <= :date " +
            "   GROUP BY s.type, s.number, s.kind, s.date, s.due, e.accountNumber, e.name, e.type, s.dt, s.ct "+
            ") AS result " +
            "WHERE :zeroBalance= true OR ABS( result.dt) <> ABS( result.ct) " +
            "ORDER BY result.entityAccountNumber ASC, result.date DESC")
    List<SettlementListDto> findByAllAsAtDate( LocalDate date, boolean zeroBalance);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class SettlementRepositoryImpl implements SettlementRepository {

    private final SqlSettlementRepository repository;
    @Override public Optional<Settlement> findByDocument( UUID document) {
        return repository.findById( document);
    }

    @Override public Settlement save( Settlement settlement) {
        return repository.save( settlement);
    }

    @Override
    public void delete(Settlement settlement) {
        repository.delete( settlement);
    }
}
