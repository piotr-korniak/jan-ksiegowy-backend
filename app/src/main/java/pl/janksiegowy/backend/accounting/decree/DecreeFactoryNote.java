package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.notice.Note;
import pl.janksiegowy.backend.finances.notice.NoteType.NoteTypeVisitor;
import pl.janksiegowy.backend.accounting.template.SettlementFunction.SettlementFunctionVisitor;

import java.math.BigDecimal;
import java.util.Optional;

import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandEntityAccount;

@AllArgsConstructor
public class DecreeFactoryNote implements NoteTypeVisitor<TemplateType> {

    private final TemplateRepository templates;
    public DecreeDto to( Note note) {

        var documentType= note.getType().accept( this);

        return templates.findByDocumentTypeAndDateAndPaymentType( documentType, note.getIssueDate(), note.getEntity().getType())
                .or(()-> templates.findByDocumentTypeAndDate( documentType, note.getIssueDate()))
                .map( template -> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((FinanceTemplateLine)line).getFunction()
                                .accept( new SettlementFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitWartoscZobowiazania() {
                                        return note.getCt();
                                    }
                                    @Override public BigDecimal visitWartoscNaleznosci() {
                                        return note.getDt();
                                    }
                                });
                    }

                    @Override public Optional<AccountDto> getAccount(TemplateLine line) {
                        var account= line.getAccount();

                        if( account.getNumber().matches(".*\\[[CR]].*"))
                            return expandEntityAccount( account.getNumber(), note.getEntity());

                        return Optional.of( AccountDto.create()
                                .name( account.getName())
                                .number( account.getNumber()));

                    }
                }.build( template, note.getIssueDate(), note.getNumber(), note.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( note.getDecree())
                        .map( decree-> decreeMap.setNumer( decreeMap.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    @Override public TemplateType visitIssuedNote() {
        return TemplateType.NI;
    }

    @Override public TemplateType visitReceiveNote() {
        return TemplateType.NR;
    }
}
