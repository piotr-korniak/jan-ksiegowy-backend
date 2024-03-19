package pl.janksiegowy.backend.accounting.decree;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2/decree")
public class DecreeController {

    private final DecreeInitializer decrees;

    public DecreeController( final DecreeQueryRepository decrees,
                             final DataLoader loader) {
        this.decrees= new DecreeInitializer( decrees, loader);
    }

    @PostMapping
    public ResponseEntity migrate() {

        decrees.init();

        return ResponseEntity.ok(  "Migrate Decree complete!");
    }
}
