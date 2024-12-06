package pl.janksiegowy.backend.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MigrationDto {
    private String url;
    private String method;
    private boolean always;
}
