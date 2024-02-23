package pl.janksiegowy.backend.finances.clearing;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
public class ClearingId implements Serializable {
    private UUID receivable;
    private UUID payable;

}
