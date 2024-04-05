package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.document.Document;

@Setter
@Accessors( chain= true)

public abstract class PaymentDocument extends Document {


}
