package pl.janksiegowy.backend.shared.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class StringTrimModule extends SimpleModule {

    public StringTrimModule() {
        addDeserializer( String.class, new StdScalarDeserializer<String>( String.class) {


            @Override
            public String deserialize( JsonParser jsonParser, DeserializationContext ctx) throws IOException {
                return Optional.ofNullable( jsonParser.getValueAsString())
                        .map( String::trim)
                        .filter(value -> !value.isEmpty()) // Filters out empty strings
                        .orElseGet(()-> null);
            }
        });
    }
}