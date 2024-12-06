package pl.janksiegowy.backend.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DataLoader {

    public BufferedReader getReader( String resource);
    public List<String[]> readData( String resource);
}
