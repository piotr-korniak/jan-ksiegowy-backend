package pl.janksiegowy.backend.shared;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class DataLoader {

    private final ResourceLoader loader;

    @Value( "${dataLoader.files.path}")
    private String path;
/*
    public String[] readInvoices() {
        return readLines( "invoices.txt");
    }
    public String[] readItems() {
        return readLines("data/items.txt");
    }
    public String[] readInvoicesItems() {
        return readLines("data/lines.txt");
    }*/

    @SneakyThrows
    public List<String[]> readData( String resource) {
        return new String( loader.getResource( path+ resource)
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
