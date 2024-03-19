package pl.janksiegowy.backend.accounting.decree;

public interface DecreeQueryRepository {

    boolean existsByDocument( String document);
}
