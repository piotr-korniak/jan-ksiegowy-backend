package pl.janksiegowy.backend.register;

import com.nimbusds.jose.util.IntegerOverflowException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegister;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.util.Optional;
import java.util.UUID;

public interface SqlRegisterRepository extends JpaRepository<Register, UUID> {


    Register findByTypeAndCode( RegisterType registerType, String code);
}

interface SqlQueryRegisterRepository extends RegisterQueryRepository, Repository<InvoiceRegister, UUID> {

}


@org.springframework.stereotype.Repository
@AllArgsConstructor
class RegisterRepositoryImpl implements RegisterRepository {

    private SqlRegisterRepository repository;

    @Override
    public Optional<SalesRegister> findVatSalesRegisterByCode( String code) {
        return Optional.ofNullable(
                (SalesRegister)repository.findByTypeAndCode( RegisterType.S, code));

    }

    @Override
    public Optional<PurchaseRegister> findVatPurchaseRegisterByCode( String code) {
        return Optional.ofNullable(
                (PurchaseRegister)repository.findByTypeAndCode( RegisterType.P, code));
    }

    @Override public Register save(Register register) {
        return repository.save( register);
    }

}