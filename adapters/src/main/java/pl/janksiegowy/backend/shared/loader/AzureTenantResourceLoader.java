package pl.janksiegowy.backend.shared.loader;

import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareFileClientBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import pl.janksiegowy.backend.database.TenantContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Profile( "azure")
public class AzureTenantResourceLoader extends ResourceLoaderFake {

    @Override public Resource getResource( String location) {
        TenantContext.Context context = TenantContext.getCurrentTenant();

        // Ustal nazwę pliku i ścieżkę z uwzględnieniem tenant i company
        String resourcePath = String.format( "%s/%s/%s",
                context.getTenant(), context.getCompany(), location);

        System.err.println( "ResourcePath: "+ resourcePath);

        ShareFileClient shareFileClient = new ShareFileClientBuilder()
                .shareName( "janksiegowy")
                .resourcePath(resourcePath)
                .buildFileClient();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        shareFileClient.download( outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource( inputStream);

    }

}
