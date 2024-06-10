package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.note.Note;
import pl.janksiegowy.backend.finances.note.NoteType.NoteTypeVisitor;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class DecreeFactoryNote implements NoteTypeVisitor<TemplateType> {

    private final TemplateRepository templates;
    public DecreeDto to( Note note) {
        return templates.findByDocumentTypeAndDate( note.getType().accept( this), note.getIssueDate())
                .map( template -> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((FinanceTemplateLine)line).getFunction().accept( new FinanceFunction.NoteFunctionVisitor<BigDecimal>() {
                            @Override public BigDecimal visitWartoscZobowiazania() {
                                return note.getCt();
                            }
                            @Override public BigDecimal visitWartoscNaleznosci() {
                                return note.getDt();
                            }
                        });
                    }
                    @Override public AccountDto getAccount( AccountDto.Proxy account) {
                        return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                            case "P"-> account.name( note.getEntity().getName())
                                    .number( account.getNumber().replaceAll( "\\[P\\]",
                                            note.getEntity().getAccountNumber()));
                            default -> account;
                        };
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
