package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.invoice.*;

import java.util.Optional;
import java.util.UUID;

public interface SqlInvoiceRegisterRepository extends JpaRepository<InvoiceRegister, UUID> {
    InvoiceRegister findByTypeAndCode( InvoiceRegisterType type, String code);
}
interface SqlInvoiceRegisterQueryRepository extends InvoiceRegisterQueryRepository,
                                                    Repository<InvoiceRegister, UUID> {}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class InvoiceRegisterRepositoryImpl implements InvoiceRegisterRepository {

    private SqlInvoiceRegisterRepository repository;
    private SqlPaymentRegisterRepository paymentRegisters;

    @Override
    public Optional<SalesRegister> findSalesRegisterByCode( String code) {
        return Optional.ofNullable( (SalesRegister)
                repository.findByTypeAndCode( InvoiceRegisterType.S, code));

    }

    @Override
    public Optional<PurchaseRegister> findPurchaseRegisterByCode( String code) {
        return Optional.ofNullable( (PurchaseRegister)
                repository.findByTypeAndCode( InvoiceRegisterType.P, code));
    }

    @Override public InvoiceRegister save( InvoiceRegister register) {
        return repository.save( register);
    }

}