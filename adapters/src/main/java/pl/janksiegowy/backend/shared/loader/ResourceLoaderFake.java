package pl.janksiegowy.backend.shared.loader;

import org.springframework.core.io.ResourceLoader;

public abstract class ResourceLoaderFake implements ResourceLoader {

    @Override public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
