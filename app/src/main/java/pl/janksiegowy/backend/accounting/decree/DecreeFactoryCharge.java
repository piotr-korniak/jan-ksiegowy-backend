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
import java.util.stream.Stream;

import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandEntityAccount;

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
                        var account= line.getAccount();
                        return Stream.of( account.getNumber())
                                .filter(k-> k.matches(".*\\[[CRS]].*"))
                                .findFirst()
                                .map(s-> expandEntityAccount( account.getNumber(), charge.getEntity()))
                                .orElseGet(()-> Optional.of( AccountDto.create()
                                        .name( account.getName())
                                        .number( account.getNumber() // Opcjonalnie, jeśli nic nie pasuje
                                        )));
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
