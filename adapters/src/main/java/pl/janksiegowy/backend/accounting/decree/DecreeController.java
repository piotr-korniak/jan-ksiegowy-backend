package pl.janksiegowy.backend.accounting.decree;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.account.AccountQueryRepository;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.finances.payment.PaymentRepository;
import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.time.LocalDate;

@TenantController
@RequestMapping( "/v2/decree")
public class DecreeController {

    private final DecreeInitializer decrees;
    private final DecreeFacade decreeFacade;
    protected final AccountingRegisterRepository registers;
    private final AccountQueryRepository accountQueryRepository;
    private final DecreeLineQueryRepository decreeLines;

    public DecreeController(final PaymentRepository payments,
                            final DecreeFacade decreeFacade,
                            final AccountingRegisterRepository registers,
                            final AccountQueryRepository accountQueryRepository,
                            final DecreeLineQueryRepository decreeLines) {
        this.decrees= new DecreeInitializer( payments);
        this.decreeFacade = decreeFacade;
        this.registers = registers;
        this.accountQueryRepository = accountQueryRepository;
        this.decreeLines = decreeLines;
    }

    @PostMapping
    public ResponseEntity migrate() {

        decrees.init();

        return ResponseEntity.ok(  "Migrate Decree complete!");
    }

    @PostMapping( "/openingBalance")
    public ResponseEntity openingBalance( @RequestBody String year) {
        LocalDate startDate = LocalDate.of( 2017, 1, 1);
        LocalDate endDate = LocalDate.of( 2017, 12, 31);

        var dupa= registers.findByCode( "PK")
                .map( register -> new DecreeMap( DecreeDto.create()
                        .type( DecreeType.D)
                        .number( "BO")
                        .date( endDate.plusDays( 1))
                        .register( RegisterDto.create()
                                .registerId( register.getRegisterId()))))
                .orElseThrow();


        accountQueryRepository.findAllAnalyticalBlanceAccountsByType().forEach(account -> {

                    var dt= decreeLines.balanceDtLike( account.getNumber(), startDate, endDate);
                    if( dt.signum()!= 0) {
                        dupa.add(DecreeLineDto.create()
                                .page(AccountPage.D)
                                .account(account)
                                .value(dt));

                    }
                    var ct= decreeLines.balanceCtLike( account.getNumber(), startDate, endDate);
                    if( ct.signum()!= 0) {
                        dupa.add(DecreeLineDto.create()
                                .page(AccountPage.C)
                                .account(account)
                                .value(ct));

                    }
                }
        );

        dupa.getLines().forEach( account -> {
            if( account.getAccount()!= null)
            System.err.println( "Konto "+ account.getPage()+ " " + account.getAccount().getNumber()
                    + " - " + account.getAccount().getName() + ": " + account.getValue().toString());
        });

        decreeFacade.save( dupa);

        return ResponseEntity.ok(  "Opening balance for year " + year);
    }
}
