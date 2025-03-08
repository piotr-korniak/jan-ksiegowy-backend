package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.shared.MigrationService;
@Log4j2

@AllArgsConstructor
public class RegisterFacade {

    private final RegisterRepository registerRepository;
    private final RegisterFactory registerFactory;
    private final MigrationService migrationService;


    public Register save( RegisterDto source) {
        return registerRepository.save( registerFactory.from( source));
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadRegisters().forEach( register -> {
            counters[0]++;

            registerRepository.findByCode( register.getCode()).orElseGet(()-> {
                counters[1]++;
                    return save( register);
                });

        });

        log.warn( "Registers migration complete!");
        return "%-50s %13s".formatted( "Registers migration complete, added: ", counters[1]+ "/"+ counters[0]);

    }

}
