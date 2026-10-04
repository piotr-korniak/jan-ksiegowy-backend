package pl.janksiegowy.backend.organization.port;

public interface CompanyRegistryLookupPort {
    CompanyRegistryData findByNip( String nip);
}
