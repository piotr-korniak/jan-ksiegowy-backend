package pl.janksiegowy.backend.accounting.decree;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeSumDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SqlDecreeLineRepository {
}

interface SqlDecreeLineQueryRepository extends DecreeLineQueryRepository, Repository<DecreeLine, UUID> {

    @Query( "SELECT COALESCE( SUM(dl.value), 0) FROM DecreeLine dl JOIN dl.account ac "+
            "WHERE TYPE( dl)= :type " +
            "AND dl.decree.date BETWEEN :periodStart AND :periodEnd "+
            "AND ac.number LIKE :name")
    BigDecimal sumValueByTypeAndAccountNameLike( Class<? extends DecreeLine> type, String name,
                                                 LocalDate periodStart, LocalDate periodEnd);

    @Query( "SELECT " +
            "    COALESCE( SUM( CASE WHEN TYPE( dl)= DecreeDtLine THEN dl.value ELSE 0 END), 0) AS dt, "+
            "    COALESCE( SUM( CASE WHEN TYPE( dl)= DecreeCtLine THEN dl.value ELSE 0 END), 0) AS ct "+
            "FROM DecreeLine dl JOIN dl.account ac "+
            "WHERE dl.decree.date BETWEEN :periodStart AND :periodEnd "+
            "AND ac.number LIKE :accountNumber")
    DecreeSumDto sumValueByAccountNumberLike( String accountNumber, LocalDate periodStart, LocalDate periodEnd);

    @Query( "SELECT "+
            "    COALESCE( SUM( CASE WHEN TYPE(dl)= DecreeDtLine THEN dl.value END), 0) AS dt, "+
            "    COALESCE( SUM( CASE WHEN TYPE(dl)= DecreeCtLine THEN dl.value END), 0) AS ct "+
            "FROM DecreeLine dl " +
            "JOIN dl.account ac " +
            "WHERE dl.decree.date BETWEEN :periodStart AND :periodEnd "+
            "AND ac.parent.number= :accountNumber ")
    DecreeSumDto sumValueByParentAccountNumber(String accountNumber, LocalDate periodStart, LocalDate periodEnd);

    @Query( "SELECT " +
            "    SUM( CASE WHEN subquery.roznica> 0 THEN subquery.roznica ELSE 0 END) dt, "+
            "    SUM( CASE WHEN subquery.roznica< 0 THEN ABS( subquery.roznica) ELSE 0 END) ct "+
            "FROM (" +
            "    SELECT "+
            "        COALESCE( SUM( CASE WHEN TYPE(dl)= DecreeDtLine THEN dl.value END), 0) - "+
            "        COALESCE( SUM( CASE WHEN TYPE(dl)= DecreeCtLine THEN dl.value END), 0) roznica "+
            "    FROM DecreeLine dl " +
            "    JOIN dl.account ac " +
            "WHERE dl.decree.date BETWEEN :periodStart AND :periodEnd "+
            "AND ac.parent.number= :accountNumber " +
            "    GROUP BY ac.id " +
            ") subquery")
    DecreeSumDto sumValueByParentAccountNumberGroupByAccount(
            String accountNumber, LocalDate periodStart, LocalDate periodEnd);

}
