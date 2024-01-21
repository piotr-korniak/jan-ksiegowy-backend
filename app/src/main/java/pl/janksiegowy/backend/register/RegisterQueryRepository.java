package pl.janksiegowy.backend.register;

import java.util.List;
import java.util.Optional;

public interface RegisterQueryRepository {

    <T> Optional<T> findByTypeInAndCode( Class<T> tClass, List<RegisterType> types, String code);

    <T> Optional<T> findByCode( Class<T> tClass, String code);

    boolean existsByTypeInAndCode( List<RegisterType> types, String code);
}
