package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.statement.PayableStatement;
import pl.janksiegowy.backend.statement.StatementItemCode;
import pl.janksiegowy.backend.statement.StatementLine;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DecreeFactoryStatement implements StatementTypeVisitor<TemplateType> {

    private final TemplateRepository templates;

    public DecreeDto to( PayableStatement statement ) {

        var lines= statement.getLines().stream()
                .collect( Collectors.toMap( StatementLine::getItemCode, StatementLine::getAmount));

        return templates.findByDocumentTypeAndDate( statement.getType().accept( this), statement.getDate())
                .map( template-> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((StatementTemplateLine)line).getFunction()
                                .accept( new StatementFunction.StatementFunctionVisitor<BigDecimal>() {

                                    private final BigDecimal korNz= lines
                                            .getOrDefault( StatementItemCode.KOR_NZ, BigDecimal.ZERO);
                                    private final BigDecimal korNc= lines
                                            .getOrDefault( StatementItemCode.KOR_NC, BigDecimal.ZERO);

                                    @Override public BigDecimal visitPodatekNalezny() {
                                        return lines.getOrDefault( StatementItemCode.VAT_NZ, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitPodatekNaliczony() {
                                        return lines.getOrDefault( StatementItemCode.VAT_NC, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitKorektaNaleznegoPlus() {
                                        return korNz.signum()>0? korNz: BigDecimal.ZERO;
                                    }
                                    @Override public BigDecimal visitKorektaNaleznegoMinus() {
                                        return korNz.signum()<0? korNz.negate(): BigDecimal.ZERO;
                                    }

                                    @Override public BigDecimal visitKorektaNaliczonegoPlus() {
                                        return korNc.signum()>0? korNc: BigDecimal.ZERO;
                                    }
                                    @Override public BigDecimal visitKorektaNaliczonegoMinus() {
                                        return korNc.signum()<0? korNc.negate(): BigDecimal.ZERO;
                                    }

                                    @Override public BigDecimal visitZobowiazanie() {
                                        return statement.getLiability();
                                    }

                                    @Override
                                    public BigDecimal visitZaliczkaCIT() {
                                        return statement.getLiability();
                                    }
                                });
                    }
                    @Override public Optional<AccountDto> getAccount( TemplateLine line) {
                        return Optional.of(
                                switch( line.getAccount().getNumber().replaceAll("[^A-Z]+", "")) {
                                    case "U"-> AccountDto.create()
                                            .name( statement.getEntity().getName())
                                            .parent( line.getAccount().getNumber())
                                            .number( line.getAccount().getNumber().replaceAll( "\\[P\\]",
                                                    statement.getEntity().getAccountNumber()));
                                    default -> AccountDto.create()
                                            .name( line.getAccount().getName())
                                            .number( line.getAccount().getNumber());
                                });
                    }
                }.build( template, statement.getPeriod().getEnd(), statement.getNumber(), statement.getStatementId()))
                .map( decreeMap-> Optional.ofNullable( statement.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    @Override public TemplateType visitVatStatement() {
        return TemplateType.SV;
    }

    @Override public TemplateType visitJpkStatement() {
        return null; // fixme - throw exception
    }

    @Override public TemplateType visitCitStatement() {
        return TemplateType.SC;
    }

    @Override public TemplateType visitPitStatement() {
        return TemplateType.SP;
    }

    @Override public TemplateType visitZusStatement() {
        return TemplateType.SN;
    }
}
