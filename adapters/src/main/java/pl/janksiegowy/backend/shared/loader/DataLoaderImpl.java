package pl.janksiegowy.backend.shared.loader;

import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.shared.DataLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.List;

public class DataLoaderImpl implements DataLoader {

    private final ResourceLoader loader;

    public DataLoaderImpl( ResourceLoader loader) {
        this.loader= loader;
    }

    @SneakyThrows
    public BufferedReader getReader( String resource) {
        return new BufferedReader(
                new InputStreamReader( loader.getResource( resource).getInputStream(), StandardCharsets.UTF_8)) {
            @Override public String readLine() throws IOException {
                var line = super.readLine();
                return line== null? null:
                        line.replaceAll(",\\s*(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", ",");
            }};
    }

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
}
