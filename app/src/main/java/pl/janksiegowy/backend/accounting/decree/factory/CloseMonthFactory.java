package pl.janksiegowy.backend.accounting.decree.factory;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.DecreeQueryRepository;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.declaration.StatementQueryRepository;
import pl.janksiegowy.backend.entity.Entity;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.payslip.PayslipQueryRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
public class CloseMonthFactory extends DecreeBuilder<Map<WageIndicatorCode, BigDecimal>>{

    private final PayslipQueryRepository payslips;
    private final StatementQueryRepository statements;
    private final DecreeQueryRepository decrees;


    public CloseMonthFactory( final TemplateRepository templates,
                              final PayslipQueryRepository payslips,
                              final StatementQueryRepository statements,
                              final DecreeQueryRepository decrees) {
        super( templates);
        this.payslips= payslips;
        this.statements= statements;
        this.decrees= decrees;
    }

    public DecreeDto create( MonthPeriod month) {
/*
        var indicators= Map.of();
                WageIndicatorCode.KW_NET,
                payslips.sumByTypeAndPeriodAndDueDate(
                        EmploymentPayslip.class, month, month.getEnd().plusDays( 10)),

                WageIndicatorCode.UB_ZUS,
                statements.sumByTypeAndPeriodAndDueDate(
                        Declaration_DRA.class, month, month.getEnd().plusDays( 15)));

        return build( null, indicators, decrees.findByDocument( "ZM "+ month.getId())
                .map( decreeDto -> DecreeDto.create()
                        .degreeId( decreeDto.getDecreeId()))
                .orElseGet( DecreeDto::create)
                .type( DecreeType.D)
                .document( "ZM "+ month.getId())
                .date( month.getEnd()));
  */
        return null;
    }


    @Override
    BigDecimal getValue( TemplateLine line, Map<WageIndicatorCode, BigDecimal> indicators) {
        return ((CloseMonthTemplateLine)line).getFunction()
                .accept( new CloseMonthFunction.CloseMonthFunctionVisitor<>() {

                    @Override public BigDecimal visitWynagrodzenieNetto() {
                        return "-".equals( line.getParameter())
                                ? indicators.getOrDefault( WageIndicatorCode.KW_NET, BigDecimal.ZERO).negate()
                                : indicators.getOrDefault( WageIndicatorCode.KW_NET, BigDecimal.ZERO);
                    }

                    @Override public BigDecimal visitUbezpieczeniaZUS() {
                        return indicators.getOrDefault( WageIndicatorCode.UB_ZUS, BigDecimal.ZERO);
                    }
                });
    }

    @Override
    Optional<AccountDto> getAccount( TemplateLine line, Entity entity) {
        return Optional.of( AccountDto.create()
                .name( line.getAccount().getName())
                .number( line.getAccount().getNumber()));
    }

    @Override TemplateType getTemplateType() {
        return TemplateType.MC;
    }


}
