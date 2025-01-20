package pl.janksiegowy.backend.statement.formatter.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.pattern.PatternId;

@Getter
@Setter
@Accessors( fluent= true, chain= true)
public class FormatterDto {

    static public FormatterDto create() {
        return new FormatterDto();
    }

    private String content;
    private PatternId version;
}
