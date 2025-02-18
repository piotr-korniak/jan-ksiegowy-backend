package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.declaration.PayableDeclaration;
import pl.janksiegowy.backend.declaration.DeclarationElementCode;
import pl.janksiegowy.backend.declaration.StatementLine;
import pl.janksiegowy.backend.declaration.DeclarationType.DeclarationTypeVisitor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DecreeFactoryStatement implements DeclarationTypeVisitor<TemplateType> {

    private final TemplateRepository templates;

    public DecreeDto to( PayableDeclaration declaration ) {

        var lines= declaration.getElements();

        return templates.findByDocumentTypeAndDate( declaration.getType().accept( this), declaration.getDate())
                .map( template-> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((StatementTemplateLine)line).getFunction()
                                .accept( new StatementFunction.StatementFunctionVisitor<BigDecimal>() {

                                    private final BigDecimal korNz= lines
                                            .getOrDefault( DeclarationElementCode.KOR_NZ, BigDecimal.ZERO);
                                    private final BigDecimal korNc= lines
                                            .getOrDefault( DeclarationElementCode.KOR_NC, BigDecimal.ZERO);

                                    @Override public BigDecimal visitPodatekNalezny() {
                                        return lines.getOrDefault( DeclarationElementCode.VAT_NZ, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitPodatekNaliczony() {
                                        return lines.getOrDefault( DeclarationElementCode.VAT_NC, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitDoPrzeniesienia() {
                                        return lines.getOrDefault( DeclarationElementCode.DO_PRZ, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitZPrzeniesienia() {
                                        return lines.getOrDefault( DeclarationElementCode.Z_PRZ, BigDecimal.ZERO);
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

                                    @Override public BigDecimal visitUbezpieczenieEmerytalne() {
                                        return lines.getOrDefault( DeclarationElementCode.UB_EME, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitUbezpieczenieRentowe() {
                                        return lines.getOrDefault( DeclarationElementCode.UB_REN, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitUbezpieczenieChorobowe() {
                                        return lines.getOrDefault( DeclarationElementCode.UC_ZAT, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitUbezpieczenieWypadkowe() {
                                        return lines.getOrDefault( DeclarationElementCode.UW_PRA, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitUbezpieczenieZdrowotne() {
                                        return lines.getOrDefault( DeclarationElementCode.UB_ZDR, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitFunduszFGSP() {
                                        return lines.getOrDefault( DeclarationElementCode.F_FGSP, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitFunduszFPFS() {
                                        return lines.getOrDefault( DeclarationElementCode.F_FPFS, BigDecimal.ZERO);
                                    }

                                    @Override public BigDecimal visitZobowiazanie() {
                                        return declaration.getLiability();
                                    }

                                    @Override
                                    public BigDecimal visitZaliczkaCIT() {
                                        return declaration.getLiability();
                                    }
                                });
                    }
                    @Override public Optional<AccountDto> getAccount( TemplateLine line) {
                        System.err.println( "Konto (numer): "+ line.getAccount().getNumber());
                        return Optional.of(
                                switch( line.getAccount().getNumber().replaceAll("[^A-Z]+", "")) {
                                    case "R"-> AccountDto.create()
                                            .name( declaration.getEntity().getName())
                                            .parent( line.getAccount().getNumber())
                                            .number( line.getAccount().getNumber().replaceAll( "\\[R\\]",
                                                    declaration.getEntity().getAccountNumber()));
                                    default -> AccountDto.create()
                                            .name( line.getAccount().getName())
                                            .number( line.getAccount().getNumber());
                                });
                    }
                }.build( template, declaration.getPeriod().getEnd(), declaration.getNumber(), declaration.getStatementId()))
                .map( decreeMap -> decreeMap.setType( DecreeType.S))
                .map( decreeMap-> Optional.ofNullable( declaration.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    @Override public TemplateType visitVatDeclaration() {
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

    @Override public TemplateType visitDraStatement() {
        return TemplateType.SN;
    }
}
