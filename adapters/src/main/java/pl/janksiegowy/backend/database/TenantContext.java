package pl.janksiegowy.backend.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.period.dto.PeriodDto;

import java.util.Optional;

public final class TenantContext {
    private static final ThreadLocal<Context> currentContext= new ThreadLocal<>();

    public TenantContext(){}

    public static void setCurrentTenant( Context context) {
        currentContext.set( context);
    }

    public static Context getCurrentTenant() {
        return Optional.ofNullable( currentContext.get())
                .orElse( Context.create()
                        .tenant( "bootstrap")
                        .company( "public"));
    }

    public static void clear() {
        currentContext.remove();
    }


    public interface Context {
        static Proxy create() {
            return new Context.Proxy();
        }

        String getTenant();
        String getCompany();

        @Setter
        @Accessors( fluent= true, chain= true)
        class Proxy implements Context {

            private String tenant;
            private String company;

            @Override public String getTenant() {
                return tenant;
            }

            @Override public String getCompany() {
                return company;
            }
        }
    }

}
