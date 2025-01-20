package pl.janksiegowy.backend.shared;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface DataLoader {

    public List<String[]> readData( String resource);

    public <T> List<T> loadYml( String filePath, Class<? extends T> clazz);

    public <T> List<T> loadCsv( String filePath, Class<? extends T> clazz);
    default public <T> List<T> loadCsv( String filePath, Class<? extends T> clazz, Predicate<T> filter){
        return loadCsv( filePath, clazz).stream()
                .filter( filter)
                .collect( Collectors.toList());
    }
}
