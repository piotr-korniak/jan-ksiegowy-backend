package pl.janksiegowy.backend.finances.payment;

public interface PaymentRepository {

    Payment save( Payment payment);

    void delete( Payment settlement);
}
