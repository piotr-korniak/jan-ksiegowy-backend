package pl.janksiegowy.backend.finances.share;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@SecondaryTable( name= Share.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public abstract class Share extends Document {
    static final String TABLE_NAME= "SHARES";

    @Column( table= TABLE_NAME)
    private BigDecimal equity;

    public ShareType getType() {
        return ShareType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

    @Override public <T> T accept( DocumentVisitor<T> visitor) {
        return visitor.visit( this);
    }

}
