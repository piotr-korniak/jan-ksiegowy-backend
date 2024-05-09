package pl.janksiegowy.backend.finances.note;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.payment.PaymentType;

@Entity
public abstract class Note extends Document {

    public NoteType getType() {
        return NoteType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    @Override public <T> T accept( DocumentVisitor<T> visitor) {
        return visitor.visit( this);
    }
}
