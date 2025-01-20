package pl.janksiegowy.backend.shared.loader;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.shared.DataLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.List;

public class DataLoaderImpl implements DataLoader {

    private final ResourceLoader loader;
    private final CsvMapper csvMapper;
    private final YAMLMapper yamlMapper;

    public DataLoaderImpl( ResourceLoader loader, CsvMapper csvMapper, YAMLMapper ymlMapper) {
        this.loader= loader;
        this.csvMapper= csvMapper;
        this.yamlMapper= ymlMapper;
    }

    @Override
    @SneakyThrows
    public List<String[]> readData( String resource) {
        return new String( loader.getResource( resource)
                .getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .lines()
                .map( this::getFields)
                .toList();
    }

    private String[] getFields( String row){
        String[] result= row.split( "(?<!\\\\), *");

        for( int n=0; n<result.length; n++)	// nie zmienia jak nie trzeba
            result[n]= result[n].replaceAll( "\\\\,", ",");
        return result;
    }

    @SneakyThrows
    public <T> List<T> loadYml( String filePath, Class<? extends T> clazz) {
        ObjectMapper mapper= yamlMapper;
        return mapper.readValue( getReader( filePath),
                mapper.getTypeFactory().constructCollectionType( List.class, clazz));
    }

    @SneakyThrows
    public <T> List<T> loadCsv( String filePath, Class<? extends T> clazz) {
        MappingIterator<T> iterator= csvMapper
                .readerFor( clazz)
                .with( CsvSchema.emptySchema().withHeader())
                .readValues( getReader( filePath));
        return iterator.readAll();
    }

    @SneakyThrows
    private BufferedReader getReader( String resource) {
        return new BufferedReader(
                new InputStreamReader( loader.getResource( resource).getInputStream(), StandardCharsets.UTF_8)) {
            @Override public String readLine() throws IOException {
                var line = super.readLine();
                return line== null? null:
                        line.replaceAll(",\\s*(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", ",");
            }};
    }
}
