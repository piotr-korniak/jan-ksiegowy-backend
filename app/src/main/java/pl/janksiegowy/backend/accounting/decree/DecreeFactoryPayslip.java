package pl.janksiegowy.backend.accounting.decree;


import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.salary.Payslip;
import pl.janksiegowy.backend.salary.PayslipItemCode;
import pl.janksiegowy.backend.salary.PayslipLine;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandEntityAccount;
import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandPaymentRegisterAccount;

@AllArgsConstructor
public class DecreeFactoryPayslip {

    private final TemplateRepository templates;

    public static DecreeFactoryPayslip create( TemplateRepository templates) {
        return new DecreeFactoryPayslip( templates);
    }

    public DecreeDto to( Payslip payslip) {

        var lines= payslip.getLines().stream()
                .collect( Collectors.toMap( PayslipLine::getItemCode, PayslipLine::getAmount));

        return templates.findByDocumentTypeAndDate( TemplateType.EP, payslip.getIssueDate())
                .map( template-> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line ) {
                        return ((PayslipTemplateLine)line).getFunction()
                                .accept( new PayslipFunction.PayslipFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitSkladkaPracownika() {
                                        return lines.getOrDefault( PayslipItemCode.UB_ZAT, BigDecimal.ZERO);
                                    }
                                    @Override public BigDecimal visitSkladkaPracodawcy() {
                                        return lines.getOrDefault( PayslipItemCode.UB_PRA, BigDecimal.ZERO);
                                    }
                                    @Override public BigDecimal visitWynagrodzenieBrutto() {
                                        return lines.getOrDefault( PayslipItemCode.KW_BRT, BigDecimal.ZERO);
                                    }
                                    @Override public BigDecimal visitUbezpiecznieZdrowotne() {
                                        return lines.getOrDefault( PayslipItemCode.UB_ZDR, BigDecimal.ZERO);
                                    }
                                    @Override public BigDecimal visitZaliczkaPIT() {
                                        return lines.getOrDefault( PayslipItemCode.TAX_ZA, BigDecimal.ZERO);
                                    }
                                    @Override public BigDecimal visitDoWyplaty() {
                                        return lines.getOrDefault( PayslipItemCode.KW_NET, BigDecimal.ZERO);
                                    }
                                } );
                    }

                    @Override
                    Optional<AccountDto> getAccount(TemplateLine line) {
                        var account= line.getAccount();

                        if( account.getNumber().matches(".*\\[[E]].*"))
                            return expandEntityAccount( account.getNumber(), payslip.getEntity());

                        return Optional.of( AccountDto.create()
                                .name( account.getName())
                                .number( account.getNumber()));
                    }


                }.build( template, payslip.getIssueDate(), payslip.getNumber(), payslip.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( payslip.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();



    }
}
