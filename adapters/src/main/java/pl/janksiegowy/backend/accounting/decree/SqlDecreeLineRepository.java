package pl.janksiegowy.backend.accounting.decree;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;

import java.math.BigDecimal;
import java.time.LocalDate;
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
}
