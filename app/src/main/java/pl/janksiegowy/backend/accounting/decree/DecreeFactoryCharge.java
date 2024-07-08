package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.charge.ChargeType;
import pl.janksiegowy.backend.finances.note.Note;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class DecreeFactoryCharge implements ChargeType.ChargeTypeVisitor<TemplateType> {

    private final TemplateRepository templates;
    public DecreeDto to( Charge charge) {
        return templates.findByDocumentTypeAndDate( charge.getType().accept( this), charge.getIssueDate())
                .map( template-> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue(TemplateLine line) {
                        return ((FinanceTemplateLine)line).getFunction()
                                .accept( new SettlementFunction.SettlementFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitWartoscZobowiazania() {
                                        return charge.getCt();
                                    }
                                    @Override public BigDecimal visitWartoscNaleznosci() {
                                        return charge.getDt();
                                    }
                                });
                    }

                    @Override public Optional<AccountDto> getAccount( TemplateLine line) {
                        return Optional.ofNullable(
                                switch( line.getAccount().getNumber().replaceAll("[^A-Z]+", "")) {
                                    case "P"-> createAccount( line, EntityType.C);
                                    case "W"-> createAccount( line, EntityType.S);
                                    default -> AccountDto.create()
                                            .name( charge.getEntity().getName())
                                            .number( line.getAccount().getNumber());
                                });
                    }

                    private AccountDto createAccount( TemplateLine line, EntityType type) {
                        return type== charge.getEntity().getType()?
                                AccountDto.create()
                                        .name( charge.getEntity().getName())
                                        .parent( line.getAccount().getNumber())
                                        .number( line.getAccount().getNumber().replaceAll( "\\[[A-Z]\\]+",
                                                charge.getEntity().getAccountNumber())): null;
                    }
                }.build( template, charge.getIssueDate(), charge.getNumber(), charge.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( charge.getDecree())
                        .map( decree-> decreeMap.setNumer( decreeMap.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    @Override public TemplateType visitFeeCharge() {
        return TemplateType.CF;
    }

    @Override public TemplateType visitCharge() {
        return TemplateType.CL;
    }
}
