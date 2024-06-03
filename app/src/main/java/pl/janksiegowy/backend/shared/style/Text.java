package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlValue;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors( chain= true)

public class Text {

    @XmlValue
    protected String value;
}
