package pl.janksiegowy.backend.shared.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.time.LocalDateTime;
import java.util.List;

@JsonDeserialize( as= ErrorResponse.Proxy.class)
@JsonIgnoreProperties( ignoreUnknown= true)
@JsonInclude( JsonInclude.Include.NON_NULL)
public interface ErrorResponse {

    static Proxy create() {
        return new Proxy();
    }

    String getCode();
    String getError();
    List<ErrorField> getFields();
    String getMessage();
    String getPath();
    int getStatus();
    LocalDateTime getTimestamp();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ErrorResponse {

        @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp;

        private int status;
        private String error;
        private String code;
        private String message;
        private String path;
        private List<ErrorField> fields;

        @Override public String getCode() {
            return code;
        }

        @Override
        public int getStatus() {
            return status;
        }

        @Override
        public String getError() {
            return error;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public List<ErrorField> getFields() {
            return fields;
        }

        @Override
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
