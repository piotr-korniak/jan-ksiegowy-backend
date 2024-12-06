package pl.janksiegowy.backend.shared.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.shared.DataLoader;


import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class MigrationLoader {

    private final DataLoader dataLoader;

    public <T> List<T> loadSteps( String filePath, Class<T> clazz) throws IOException {
        ObjectMapper mapper= new ObjectMapper( new YAMLFactory());
        return mapper.readValue( dataLoader.getReader( filePath),
                mapper.getTypeFactory().constructCollectionType( List.class, clazz));
    }
}
