package pl.janksiegowy.backend.finances.payment;

import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;

import java.time.LocalDate;
import java.util.Optional;

public interface PaymentQueryRepository {

    Optional<PaymentDto> findByEntityTaxNumberAndDateAndRegisterCode(
            String taxNumber, LocalDate date, String registerCode);
}
