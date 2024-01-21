package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;

import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.period.PeriodRepository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

@AllArgsConstructor
public class StatementApproval {

    private final PeriodRepository periods;
    private final InvoiceLineQueryRepository lines;

    public void approval( String period) {

        try {
            var version= JpkVat.JPK_V7K_2_v1_0e;

            JAXBContext context = JAXBContext.newInstance( Ewidencja_JPK_V7K_2_v1_0e.class);
            Marshaller marshaller= context.createMarshaller();
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            periods.findMonthById( period)
                    .map( monthPeriod-> {
                        try {
                            marshaller.marshal(
                                version.accept( new StatementJpkFactory( lines, monthPeriod)),
                                new StreamResult( System.err ) );
                        } catch (JAXBException e) {
                            throw new RuntimeException( e );
                        }
                        return null;
                    });

        } catch (JAXBException e) {
            throw new RuntimeException( e );
        }

    }
}
