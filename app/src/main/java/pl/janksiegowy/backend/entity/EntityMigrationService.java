package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.shared.DataLoader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2

@Service
@AllArgsConstructor
public class EntityMigrationService {

    private final EntityQueryRepository entities;
    private final EntityFacade facade;
    private final DataLoader loader;
    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    public String init() {
        var history= new Object(){ LocalDate date= LocalDate.EPOCH;};
        var total= 0;
        var added= 0;

        for( String[] fields: loader.readData( "entities.txt")) {
            if( fields[0].startsWith( "--- ")) {    // only set date
                history.date= LocalDate.parse( fields[0], formatter);
                continue;
            }
            total++;

            var type= EntityType.valueOf( fields[0]);
            var taxNumber= fields[2].replaceAll( "[^a-zA-Z0-9]", "");
            var country= fields.length>7? Country.valueOf( fields[7]): Country.PL; // PL default

            var entity= entities.findByCountryAndTypeAndTaxNumber( country, type, taxNumber);

            if( entity.map( i-> !i.getDate().isBefore( history.date)).orElse( false))
                continue;   // optimization, omit if Entity already exists
            added++;

            var role= EntityRole.values()[Integer.parseInt( fields[6])];
            facade.save( entity
                    .map( entityDto-> EntityDto.create()    // new Entity history
                            .entityId( entityDto.getEntityId())
                            .accountNumber( entityDto.getAccountNumber()))
                    .orElse( EntityDto.create())            // new Entity
                            .date( history.date)            // both of the above!
                            .type( type)
                            .name( fields[1])
                            .taxNumber( taxNumber)
                            .address( fields[3])
                            .postalCode( fields[4])
                            .city( fields[5])
                            .supplier( role.isSupplier())
                            .customer( role.isCustomer())
                            .country( country));
        }
        log.warn( "Entities migration complete!");
        return String.format( "%-40s %16s", "Entities migration complete, added: ", added+ "/"+ total);
    }

    private enum EntityRole {   // decode entities Role
        NONE,
        SUPPLIER {
            @Override public boolean isSupplier() {
                return true;
            }
        },
        CUSTOMER {
            @Override public boolean isCustomer() {
                return true;
            }
        },
        BOTH {
            @Override public boolean isSupplier() {
                return true;
            }

            @Override public boolean isCustomer() {
                return true;
            }
        };

        public boolean isSupplier() {
            return false;
        };
        public boolean isCustomer() {
            return false;
        };
    }
}
