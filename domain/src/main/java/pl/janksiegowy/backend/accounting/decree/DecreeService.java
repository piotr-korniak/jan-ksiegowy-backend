package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.finances.document.Document;

public interface DecreeService {

    Decree book( Document document);
    Decree save( Decree decree);
}
