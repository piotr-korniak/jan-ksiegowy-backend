package pl.janksiegowy.backend.finances.payment;

public interface PaymentRepository {

    PaymentOld save( PaymentOld payment);
}
