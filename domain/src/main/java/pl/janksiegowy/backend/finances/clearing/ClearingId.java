package pl.janksiegowy.backend.finances.clearing;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
public class ClearingId implements Serializable {
    private UUID receivableId;
    private UUID payableId;


}
