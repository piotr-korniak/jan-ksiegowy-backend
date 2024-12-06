package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;

import java.util.Optional;

@AllArgsConstructor
public class TemplateLineFactory {

    private final AccountRepository accounts;

    public TemplateLine from( TemplateLineDto source, TemplateLine line) {

        return accounts.findByNumber( source.getAccount().getNumber())
                .map( account-> Optional.ofNullable( source.getId())
                    .map( line::setId)
                    .orElseGet(()-> line)
                        .setSide( source.getSide())
                        .setAccount( account)
                        .setSettlementType( source.getSettlementType())
                        .setDescription( source.getDescription()))
                .orElseThrow();
    }

}
