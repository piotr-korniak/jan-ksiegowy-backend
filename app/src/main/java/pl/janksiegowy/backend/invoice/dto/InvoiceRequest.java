package pl.janksiegowy.backend.invoice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@JsonDeserialize( as= InvoiceRequest.Proxy.class)
public interface InvoiceRequest {

     UUID getRegisterId();

    @Data
    class Proxy implements InvoiceRequest {

        private UUID registerId;

    }
}
