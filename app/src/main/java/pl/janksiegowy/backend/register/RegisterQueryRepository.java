package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.util.Optional;

public interface RegisterQueryRepository {
    Optional<RegisterDto> findByCodeAndTypeIn( String code, RegisterType... type);

    default Optional<RegisterDto> findAccountRegisterByCode( String code) {
        return findByCodeAndTypeIn( code, RegisterType.R);
    }

    default Optional<RegisterDto> findPaymentRegisterByCode( String code) {
        return findByCodeAndTypeIn( code, RegisterType.A, RegisterType.D);
    }

    default Optional<RegisterDto> findSalesRegisterByCode( String code) {
        return findByCodeAndTypeIn( code, RegisterType.S);
    }

    default Optional<RegisterDto> findPurchaseRegisterByCode( String code) {
        return findByCodeAndTypeIn( code, RegisterType.P);
    }
}
