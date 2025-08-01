package pl.janksiegowy.backend.shared.fop;

import jakarta.annotation.PostConstruct;
import org.apache.fop.apps.FopFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FopService {

    private FopFactory fopFactory;

    @PostConstruct
    public void initFopFactory() throws Exception {
        // Utwórz tymczasowy folder na fonty
        Path tempFontsDir = Files.createTempDirectory("fop-fonts");
        tempFontsDir.toFile().deleteOnExit();

        // Lista fontów do wyciągnięcia z JAR-a
        String[] fontFiles = {
                "Lato-Black.ttf",
                "Lato-BlackItalic.ttf",
                "Lato-Bold.ttf",
                "Lato-BoldItalic.ttf",
                "Lato-Italic.ttf",
                "Lato-Light.ttf",
                "Lato-LightItalic.ttf",
                "Lato-Regular.ttf",
                "Lato-Thin.ttf",
                "Lato-ThinItalic.ttf"
        };

        // Skopiuj każdy font z classpath do temp foldera
        for (String fontFile : fontFiles) {
            try (InputStream fontStream = getClass().getClassLoader()
                    .getResourceAsStream("fop/fonts/" + fontFile)) {

                if (fontStream != null) {
                    Path tempFontFile = tempFontsDir.resolve(fontFile);
                    Files.copy(fontStream, tempFontFile);
                    tempFontFile.toFile().deleteOnExit();
                }
            }
        }

        // Utwórz konfigurację FOP z temp ścieżką do fontów
        String configXml = String.format("""
                <?xml version="1.0" encoding="utf-8" ?>
                <fop version="1.0">
                    <renderers>
                        <renderer mime="application/pdf">
                            <fonts>
                                <directory>%s</directory>
                            </fonts>
                        </renderer>
                    </renderers>
                </fop>
                """, tempFontsDir.toString());

        // Zapisz config do temp pliku
        Path tempConfigFile = Files.createTempFile("fop-config", ".xml");
        Files.writeString(tempConfigFile, configXml);
        tempConfigFile.toFile().deleteOnExit();

        // Utwórz FopFactory
        this.fopFactory= FopFactory.newInstance(tempConfigFile.toFile());
    }

    public FopFactory getFopFactory() {
        return fopFactory;
    }
}