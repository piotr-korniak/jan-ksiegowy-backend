package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.PayableStatement;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class DecreeFactoryStatement implements StatementTypeVisitor<TemplateType> {

    private final TemplateRepository templates;

    public DecreeDto to( PayableStatement statement ) {

        var pattern= PatternId.JPK_V7K_2_v1_0e;

        var jpkData= XmlConverter.unmarshal( Ewidencja_JPK_V7K_2_v1_0e.class, statement.getXml());


        return templates.findByDocumentTypeAndDate( statement.getType().accept( this), statement.getDate())
                .map( template-> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((StatementTemplateLine)line).getFunction()
                                .accept( new StatementFunction.StatementFunctionVisitor<BigDecimal>() {

                                    @Override public BigDecimal visitPodatekNalezny() {
                                        return jpkData.getVatNalezny();
                                    }

                                    @Override public BigDecimal visitPodatekNaliczony() {
                                        return jpkData.getVatNaliczony();
                                    }

                                    @Override public BigDecimal visitKorektaNaleznegoPlus() {
                                        return statement.getValue_1().signum()>0? statement.getValue_1(): BigDecimal.ZERO;
                                    }

                                    @Override public BigDecimal visitKorektaNaleznegoMinus() {
                                        return statement.getValue_1().signum()<0? statement.getValue_1().negate(): BigDecimal.ZERO;
                                    }

                                    @Override public BigDecimal visitKorektaNaliczonegoPlus() {
                                        return statement.getValue_2().signum()>0? statement.getValue_2(): BigDecimal.ZERO;
                                    }

                                    @Override public BigDecimal visitKorektaNaliczonegoMinus() {
                                        return statement.getValue_2().signum()<0? statement.getValue_2().negate(): BigDecimal.ZERO;
                                    }

                                    @Override public BigDecimal visitZobowiazanie() {
                                        return jpkData.getZobowiazanie();
                                    }
                                });
                    }

                    @Override public AccountDto getAccount( AccountDto.Proxy account) {
                        return account;
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
