package pl.janksiegowy.backend.finances.charge;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.janksiegowy.backend.finances.document.Document;

@Entity
public abstract class Charge extends Document {

    public ChargeType getType() {
        return ChargeType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

}
