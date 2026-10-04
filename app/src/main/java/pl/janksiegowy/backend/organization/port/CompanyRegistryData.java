package pl.janksiegowy.backend.organization.port;

public record CompanyRegistryData(
        String nip,
        String regon,
        String companyName,
        String street,
//        String buildingNo,
        String city,
        String postalCode
) {
}
