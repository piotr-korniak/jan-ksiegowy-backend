package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.register.vat.VatPurchaseRegister;
import pl.janksiegowy.backend.register.vat.VatSalesRegister;

import java.util.Optional;
import java.util.UUID;

public interface SqlRegisterRepository extends JpaRepository<Register, UUID> {


    Register findByTypeAndCode( RegisterType registerType, String code);
}

interface SqlQueryRegisterRepository extends RegisterQueryRepository, Repository<Register, UUID> {

}


@org.springframework.stereotype.Repository
@AllArgsConstructor
class RegisterRepositoryImpl implements RegisterRepository {

    private SqlRegisterRepository repository;

    @Override
    public Optional<VatSalesRegister> findVatSalesRegisterByCode( String code) {
        return Optional.ofNullable(
                (VatSalesRegister)repository.findByTypeAndCode( RegisterType.S, code));

    }

    @Override
    public Optional<VatPurchaseRegister> findVatPurchaseRegisterByCode(String code) {
        return Optional.ofNullable(
                (VatPurchaseRegister)repository.findByTypeAndCode( RegisterType.P, code));
    }

    @Override public Register save(Register register) {
        return repository.save( register);
    }

}