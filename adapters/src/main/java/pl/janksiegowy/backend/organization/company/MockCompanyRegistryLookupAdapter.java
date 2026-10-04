package pl.janksiegowy.backend.organization.company;

import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.port.CompanyRegistryData;
import pl.janksiegowy.backend.organization.port.CompanyRegistryLookupPort;

@Service
public class MockCompanyRegistryLookupAdapter implements CompanyRegistryLookupPort {

    @Override
    public CompanyRegistryData findByNip( String nip) {
        return switch (nip) {
            case "5862321911" -> new CompanyRegistryData(
                    "5862321911",
                    "368141215",
                    "Eleutheria Usługi Informatyczne Sp. z o.o.",
                    "ul. Wiejska 24a",
                    "Rumia",
                    "84-230"
            );
            case "5882534461" -> new CompanyRegistryData(
                    "5882534461",
                    "542493862",
                    "Eleutheria Rachunkowość Sp. z o.o.",
                    "ul. Wiejska 24a",
                    "Rumia",
                    "84-230"
            );
            default -> throw new IllegalStateException("Unexpected value: " + nip);
        };
    };
}