package pl.janksiegowy.backend.salary;

import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.salary.dto.PayslipDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalaryFacade {

    void calculate( Contract contract, Period period) {

        var gross= contract.getSalary();
        var emerytalne= gross.multiply( new BigDecimal( "9.76"))
                .setScale(2, RoundingMode.HALF_UP);
        var rentowe= gross.multiply( new BigDecimal( "1.5"))
                .setScale(2, RoundingMode.HALF_UP);
        var chorobowe= gross.multiply( new BigDecimal( "2.45"))
                .setScale(2, RoundingMode.HALF_UP);

        PayslipDto.create()
                .insuranceEmployee( emerytalne.add( rentowe ).add( chorobowe));

    }
}
