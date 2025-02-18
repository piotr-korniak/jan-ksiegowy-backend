package pl.janksiegowy.backend.accounting.decree.factory;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.accounting.template.TemplateLine;
import pl.janksiegowy.backend.accounting.template.TemplateRepository;
import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.entity.Entity;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.math.BigDecimal;
import java.util.Optional;


@AllArgsConstructor
public abstract class DecreeBuilder<T> {

    private final TemplateRepository templates;

    abstract BigDecimal getValue( TemplateLine line, T source);
    abstract Optional<AccountDto> getAccount( TemplateLine line, Entity entity);
    abstract TemplateType getTemplateType();

    public DecreeMap build( Entity entity, T indicators, DecreeDto.Proxy decreeDto) {
        return templates.findByDocumentTypeAndDate( getTemplateType(), decreeDto.getDate())
            .map(template-> {
                var decree= new DecreeMap( decreeDto.register(RegisterDto.create()
                        .registerId(template.getRegister().getRegisterId())));

                template.getItems()
                    .forEach(templateItem-> getAccount( templateItem, entity)
                            .ifPresent(accountDto -> Optional.of(getValue( templateItem, indicators))
                                    .filter(value -> value.signum() != 0) // Filtrowanie wartości != 0
                                    .ifPresent(value-> decree.getLines().stream()
                                            .filter(line-> line.getSide() == (templateItem.getSide()) &&
                                                    line.getAccount().getNumber().equals( accountDto.getNumber()))
                                            .findFirst()
                                            .ifPresentOrElse(decreeLineDto ->
                                                            ((DecreeLineDto.Proxy) decreeLineDto)
                                                                    .value( decreeLineDto.getValue().add( value)),
                                                    () -> decree.add( DecreeLineDto.create()
                                                            .account( accountDto)
                                                            .value( value)
                                                            .side( templateItem.getSide())
                                                            .description( templateItem.getDescription()))))));
                return decree;
            }).orElseThrow();
    }
}
