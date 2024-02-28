package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.accounting.decree.DecreeType.DecreeTypeVisitor;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DecreeFactory {

    private final TemplateRepository templates;
    private final AccountingRegisterRepository registers;
    private final DecreeLineFactory line;

    public Decree from( DecreeDto source) {

        return update( source, Optional.ofNullable( source.getDecreeId())
                .map( id-> create( source).setDecreeId( id))
                .orElse( create( source).setDecreeId( UUID.randomUUID())));

    }

    public Decree update( DecreeDto source, Decree decree) {
        Optional.ofNullable( source.getLines())
                .ifPresent( lines-> decree.setLines( lines.stream()
                        .map( decreeLineDto-> line.from( decreeLineDto).setDecree( decree ))
                        .collect( Collectors.toList())));

        return registers.findById( source.getRegister().getRegisterId())
                .map( register-> decree.setDate( source.getDate())
                        .setRegister( register))
                .orElseThrow();
    }

    private Decree create( DecreeDto source) {
        return source.getType().accept( new DecreeTypeVisitor<Decree>() {
            @Override public Decree visitBasicDecree() {
                return new BasicDecree();
            }

            @Override public Decree visitSettlementDecree() {
                return new SettlementDecree();
            }
        });
    }

    public DecreeDto to( Payment payment) {

        return templates.findByTypeAndKindAndDate( TemplateType.valueOf( payment.getRegister().getType().name()),
                        payment.getType().name(), payment.getDate())
            .map( template-> new Builder() {
                @Override public BigDecimal getValue( TemplateLine item) {
                    return ((PaymentTemplateLine) item).getFunction()
                        .accept( new PaymentFunction.PaymentFunctionVisitor<BigDecimal>() {
                            @Override public BigDecimal visitSplataNaleznosci() {
                                return payment.getSettlement().getCt();
                            }
                            @Override public BigDecimal visitSplataZobowiazania() {
                                return payment.getSettlement().getDt();
                            }
                        });
                }

                @Override public List<? extends TemplateLine> getItems() {
                    return ((PaymentTemplate)template).getItems();
                }

                @Override public String getAccount( String accountNumber) {
                    return accountNumber.replace( "[K]", "01" )
                            .replace( "[P]", payment.getSettlement().getEntity().getAccountNumber());

                }
            }.build( template, payment.getDate()))
                .orElseThrow();

    }



    abstract static class Builder {

        public abstract BigDecimal getValue( TemplateLine item);
        public abstract List<? extends TemplateLine> getItems();
        abstract public String getAccount( String accountNumber);

        public DecreeDto build( Template template, LocalDate date) {
            var decree= new DecreeMap( DecreeDto.create()
                    .type( DecreeType.S)
                    .date( date)
                    .register( RegisterDto.create()
                            .registerId( template.getRegister().getRegisterId())));

            getItems().forEach( templateItem-> {
                var value= getValue( templateItem);
                if( value.signum()!=0) {
                    var account= AccountDto.create().number( templateItem.getAccount().getNumber());
                    if( account.getNumber().matches( ".*[KP].*" )) {
                        account.parent( account.getNumber());
                        account.number( getAccount( account.getNumber()));
                    }

                    decree.add( DecreeLineDto.create()
                            .account( account)
                            .page( templateItem.getPage())
                            .value( value));
                }
            });

            return decree;
        }
    }

}
