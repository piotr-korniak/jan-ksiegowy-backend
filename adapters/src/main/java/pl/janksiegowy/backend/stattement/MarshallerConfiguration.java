package pl.janksiegowy.backend.stattement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


import java.util.Map;

@Configuration
public class MarshallerConfiguration {
/*
    @Bean
    Marshaller marshaller() {

        var marshaller= new Jaxb2Marshaller();

        //marshaller.setContextPaths( "pl.gov.crd.wzor._2021._11._29._11089");//,
        // "pl.gov.crd.wzor._2021._12._27._11149");

        //marshaller.setPackagesToScan( "pl.gov.crd.wzor._2021._11._29._11089");

       // marshaller.setClassesToBeBound( Ewidencja_JPK_V7K_2_v1_0e.class);

        //String[] packagesToScan= {"<packcge which contain the department class>"};
        //marshaller.setPackagesToScan(packagesToScan);
        marshaller.setPackagesToScan( "pl.gov.crd.wzor");//._2021._12._27._11149");


//        marshaller.setPackagesToScan( "pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e");
        marshaller.setMarshallerProperties(
                Map.of("jaxb.formatted.output", true));


        for( String pack: marshaller.getPackagesToScan()) {
            System.err.println( pack);
        }
        return marshaller;
    }*/
}
