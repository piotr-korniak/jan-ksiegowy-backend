package pl.janksiegowy.backend.shared.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import pl.janksiegowy.backend.database.TenantContext;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@Profile( "aws")
public class AwsTenantResourceLoader extends ResourceLoaderFake {

    private final String bucketName;
    private final S3Client s3Client;

    public AwsTenantResourceLoader( String bucketName) {
        super();
        this.bucketName= bucketName;
        this.s3Client = S3Client.builder().build();
    }

    @Override
    public Resource getResource( String location) {
        TenantContext.Context context= TenantContext.getCurrentTenant();

        // Ustal nazwę pliku i ścieżkę z uwzględnieniem tenant i company
        String resourcePath = String.format( "%s/%s/%s",
                context.getTenant(), context.getCompany(), location);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket( bucketName)
                .key( resourcePath)
                .build();

        return new InputStreamResource( s3Client.getObject( getObjectRequest));
    }

}
