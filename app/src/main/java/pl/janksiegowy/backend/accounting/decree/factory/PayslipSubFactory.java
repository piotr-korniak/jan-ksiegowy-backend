package pl.janksiegowy.backend.accounting.decree.factory;


import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.DecreeQueryRepository;
import pl.janksiegowy.backend.accounting.decree.DecreeType;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.entity.Entity;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.payslip.Payslip;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandEntityAccount;

@Component
public class PayslipSubFactory extends DecreeBuilder<Map<WageIndicatorCode, BigDecimal>> {

    private final DecreeQueryRepository decrees;

    public PayslipSubFactory( final TemplateRepository templates,
                              final DecreeQueryRepository decrees) {
        super( templates);
        this.decrees= decrees;
    }

    public DecreeDto create( Payslip payslip) {

        var decree= build( payslip.getEntity(),
                        payslip.getElements(),
                        DecreeDto.create()
                                .document( payslip.getNumber())
                                .date( payslip.getIssueDate())
                                .degreeId( payslip.getDocumentId())
                                .type( DecreeType.D));

        decrees.findProjectedByDecreeId( payslip.getDocumentId())
                .ifPresent( dto-> decree.setNumer( dto.getNumber()));

        return decree;
    }

    @Override
    BigDecimal getValue( TemplateLine line, Map<WageIndicatorCode, BigDecimal> indicators) {
        return ((PayslipTemplateLine)line).getFunction()
                .accept( new PayslipFunction.PayslipFunctionVisitor<BigDecimal>() {
                    @Override public BigDecimal visitUbezpieczenieEmerytalneZatrudniony() {
                        return indicators.getOrDefault( WageIndicatorCode.UE_ZAT, BigDecimal.ZERO);
                    }

                    @Override
                    public BigDecimal visitUbezpieczenieEmerytalnePracodawca() {
                        return indicators.getOrDefault( WageIndicatorCode.UE_PRA, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitUbezpieczenieRentoweZatrudniony() {
                        return indicators.getOrDefault( WageIndicatorCode.UR_ZAT, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitUbezpieczenieRentowePracodawca() {
                        return indicators.getOrDefault( WageIndicatorCode.UR_PRA, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitUbezpieczenieChoroboweZatrudniony() {
                        return indicators.getOrDefault( WageIndicatorCode.UC_ZAT, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitUbezpieczenieWypadkowePracodawca() {
                        return indicators.getOrDefault( WageIndicatorCode.UW_PRA, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitFunduszFPFS() {
                        return indicators.getOrDefault( WageIndicatorCode.F_FPFS, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitFunduszFGSP() {
                        return indicators.getOrDefault( WageIndicatorCode.F_FGSP, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitUbezpieczenieZdrowotne() {
                        return indicators.getOrDefault( WageIndicatorCode.UB_ZDR, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitWynagrodzenieBrutto() {
                        return indicators.getOrDefault( WageIndicatorCode.KW_BRT, BigDecimal.ZERO);
                    }
                    @Override public BigDecimal visitWynagrodzenieNetto() {
                        return indicators.getOrDefault( WageIndicatorCode.KW_NET, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitKwotaZaliczki() {
                        return indicators.getOrDefault( WageIndicatorCode.KW_ZAL, BigDecimal.ZERO);
                    }
                } );
    }

    @Override Optional<AccountDto> getAccount(TemplateLine line, Entity entity) {
        var account= line.getAccount();

        if( account.getNumber().matches(".*\\[[E]].*"))
            return expandEntityAccount( account.getNumber(), entity);

        return Optional.of( AccountDto.create()
                .name( account.getName())
                .number( account.getNumber()));
    }

    @Override
    TemplateType getTemplateType() {
        return TemplateType.EP;
    }

}
