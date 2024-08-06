package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.salary.dto.PayslipLineDto;

public class PayslipLineFactory {
    public PayslipLine from( PayslipLineDto payslipLineDto) {
        return new PayslipLine()
                .setItemCode( payslipLineDto.getItemCode())
                .setAmount( payslipLineDto.getAmount());
    }

}
