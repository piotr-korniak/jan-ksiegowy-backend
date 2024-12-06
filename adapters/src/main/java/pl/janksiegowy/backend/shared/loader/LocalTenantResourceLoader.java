package pl.janksiegowy.backend.shared.loader;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.database.TenantContext;

@Profile( "local")
public class LocalTenantResourceLoader extends ResourceLoaderFake {

    private final ResourceLoader delegate;
    private final String path;

    public LocalTenantResourceLoader( ResourceLoader delegate, String path) {
        this.delegate= delegate;
        this.path= path;
    }

    @Override public Resource getResource( String location) {
        // Pobierz aktualny kontekst tenant
        TenantContext.Context context= TenantContext.getCurrentTenant();

        // Generuj lokalną ścieżkę na podstawie tenant i company
        String path2 = String.format( "%s/%s/Migration/%s",
                path, context.getCompany().replaceAll("^([A-Za-z]+)(\\d+)$", "$1_$2"), location);

        // Pobierz zasób przy użyciu wygenerowanej ścieżki
        return delegate.getResource( path2);
    }

}
