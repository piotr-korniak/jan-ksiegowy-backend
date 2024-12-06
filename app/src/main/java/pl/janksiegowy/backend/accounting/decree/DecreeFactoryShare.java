package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.share.Share;
import pl.janksiegowy.backend.finances.share.ShareType.ShareTypeVisitor;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class DecreeFactoryShare implements ShareTypeVisitor<TemplateType> {

    private final TemplateRepository templates;

    public static DecreeFactoryShare create( TemplateRepository templates) {
        return new DecreeFactoryShare( templates);
    }

    public DecreeDto to( Share share) {
        return templates.findByDocumentTypeAndDate( share.getType().accept( this), share.getIssueDate())
                .map( template -> new DecreeFactory.Builder() {

                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((FinanceTemplateLine)line).getFunction()
                                .accept( new SettlementFunction.SettlementFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitWartoscZobowiazania() {
                                        return share.getCt();
                                    }
                                    @Override public BigDecimal visitWartoscNaleznosci() {
                                        return share.getDt();
                                    }
                                });
                    }

                    @Override public Optional<AccountDto> getAccount( TemplateLine line) {
                        return Optional.of(
                                switch( line.getAccount().getNumber().replaceAll("[^A-Z]+", "")) {
                                    case "S"-> AccountDto.create()
                                            .name( share.getEntity().getName())
                                            .parent( line.getAccount().getNumber())
                                            .number( line.getAccount().getNumber().replaceAll( "\\[S\\]",
                                                    share.getEntity().getAccountNumber()));
                                    default -> AccountDto.create()
                                            .name( line.getAccount().getName())
                                            .number( line.getAccount().getNumber());
                                });
                    }
                }.build( template, share.getIssueDate(), share.getNumber(), share.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( share.getDecree())
                        .map( decree-> decreeMap.setNumer( decreeMap.getNumber()))
                        .orElseGet(()-> decreeMap ))
                .orElseThrow();
    }

    @Override public TemplateType visitAcquireShare() {
        return TemplateType.HA;
    }

    @Override public TemplateType visitDisposedShare() {
        return TemplateType.HD;
    }
}
