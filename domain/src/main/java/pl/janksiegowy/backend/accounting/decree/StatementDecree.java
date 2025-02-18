package pl.janksiegowy.backend.accounting.decree;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import pl.janksiegowy.backend.declaration.PayableDeclaration;

@Entity
@DiscriminatorValue( "S")
public class StatementDecree extends Decree {

    @OneToOne
    @JoinColumn( name= "ID", nullable= false)
    private PayableDeclaration decree;

}
