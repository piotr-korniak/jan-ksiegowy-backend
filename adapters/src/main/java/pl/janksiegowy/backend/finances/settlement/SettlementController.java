package pl.janksiegowy.backend.finances.settlement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.document.DocumentConverter;
import pl.janksiegowy.backend.finances.settlement.dto.EntityReport;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementListDto;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementReportLine;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.report.Summary;
import pl.janksiegowy.backend.subdomain.TenantController;

import java.lang.constant.Constable;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@TenantController
@RequestMapping( value= {"/v2/settlement", "/v2/settlement/{accountNumber}"})
public class SettlementController extends DocumentConverter {

    private final SettlementQueryRepository settlements;
    private final String rowFormat= "%2s %-30s %10s %10s %8s %12s %12s %12s\n";
    private final String sumFormat= "%64s %12s %12s %12s\n";
    private final String dashes=  String.format( rowFormat,
            String.join("", Collections.nCopies(2, "-")),
            String.join("", Collections.nCopies(30, "-")),
            String.join("", Collections.nCopies(10, "-")),
            String.join("", Collections.nCopies(10, "-")),
            String.join("", Collections.nCopies(8, "-")),
            String.join("", Collections.nCopies(12, "-")),
            String.join("", Collections.nCopies(12, "-")),
            String.join("", Collections.nCopies(12, "-")));

    public SettlementController( final SettlementQueryRepository settlements) {
        this.settlements = settlements;
    }

    @GetMapping
    public ResponseEntity settlement( @PathVariable Optional<String> accountNumber,
              @RequestParam( required= false)
              @DateTimeFormat( iso= DateTimeFormat.ISO.DATE) Optional<LocalDate> accountDate,
              @RequestParam( required= false)  String zeroBalance) {

        var total = new Summary();
        List<EntityReport> entityReports = new ArrayList<>();

        accountNumber.map(EntityInfo::new)
                .map(entityInfo -> accountDate
                        .map(date -> settlements
                                .findByEntityTypeAndEntityAccountNumerAsAtDate(
                                        entityInfo.type, entityInfo.number, date, zeroBalance != null))
                        .orElseGet(() -> settlements
                                .findByEntityTypeAndEntityAccountNumberOrderByEntityAccountNumberDesc(
                                        entityInfo.type, entityInfo.number, zeroBalance != null)))
                .orElseGet(() -> accountDate
                        .map(data -> settlements.findByAllAsAtDate(data, zeroBalance != null))
                        .orElseGet(() -> settlements.findByDtNotEqualCtOrderByDateDesc(zeroBalance != null)))
                .stream().collect(Collectors.groupingBy(
                        dto -> Arrays.asList(dto.getEntityType(), dto.getEntityAccountNumber()),
                        LinkedHashMap::new,
                        Collectors.toList()))
                .forEach((key, lines) -> {
                    var entityReport= new EntityReport() {
                        private final Summary sum= new Summary();

                        @Override public String getEntityType() {
                            return ((EntityType)key.get(0)).name();
                        }
                        @Override public String getEntityAccountNumber() {
                            return ((EntityType)key.get(0)).name()+ lines.get(0).getEntityAccountNumber();
                        }
                        @Override public String getEntityName() {
                            return lines.get(0).getEntityName();
                        }

                        @Override
                        public List<SettlementReportLine> getSettlements() {
                            return lines.stream().map(settlement -> {
                                return new SettlementReportLine(){
                                    @Override public String getDocumentNumber() {
                                        return settlement.getNumber();
                                    }
                                };
                            }).collect( Collectors.toList());
                        }

                        @Override
                        public List<String> getHeaders() {
                            return Arrays.asList( "Type", "Number", "Date", "Due", "Dt", "Ct");
                        }

                        @Override
                        public List<List<Object>> getRows() {
                            return lines.stream().map(settlement -> {
                                return(List<Object>) (List<?>)Arrays.asList(
                                        settlement.getType().accept( SettlementController.this).name(),
                                        settlement.getNumber(),
                                        settlement.getDate(),
                                        settlement.getDue(),
                                        settlement.getDt(),
                                        settlement.getCt()
                                );
                            }).collect( Collectors.toList());
                        }
                        /*
                        public List<SettlementReportLine> getSettlements() {
                            return lines.stream().map(settlement -> {
                                sum.addDt( settlement.getDt()).addCt( settlement.getCt());
                                //total.addDt(settlement.getDt()).addCt(settlement.getCt());
                                return new SettlementReportLine(){};
                                        /*settlement.getDocumentType(),
                                        settlement.getDocumentNumber(),
                                        settlement.getDate(),
                                        settlement.getDueDate(),
                                        settlement.getDt(),
                                        settlement.getCt(),
                                        settlement.getSaldo()*/

                            //}).collect( Collectors.toList());

                        @Override public Summary getSum() {
                            return sum;
                        }
                    };

                    entityReports.add( entityReport);
                });

//        SettlementReport report = new SettlementReport();
  //      report.setEntities(entityReports);
    //    report.setTotal(total);

        return ResponseEntity.ok( entityReports);
        /*
        var bufor= new StringBuilder();

        var total= new Sum();
        List<EntityInfo> entityInfoList = new ArrayList<>();

        accountNumber.map( EntityInfo::new)
            .map( entityInfo-> accountDate
                .map( date-> settlements
                    .findByEntityTypeAndEntityAccountNumerAsAtDate(
                            entityInfo.type, entityInfo.number, date, zeroBalance!=null))
                .orElseGet(()-> settlements
                    .findByEntityTypeAndEntityAccountNumberOrderByEntityAccountNumberDesc(
                            entityInfo.type, entityInfo.number, zeroBalance!= null)))
            .orElseGet(()-> accountDate
                .map( data-> settlements.findByAllAsAtDate( data, zeroBalance!=null))
                .orElseGet(()-> settlements.findByDtNotEqualCtOrderByDateDesc( zeroBalance!=null)))
        .stream().collect( Collectors.groupingBy( dto-> Arrays.asList( dto.getEntityType(), dto.getEntityAccountNumber()),
                        LinkedHashMap::new,
                        Collectors.toList()))
                .forEach( (key, lines) -> lines.stream()
                        .findFirst()
                        .ifPresent( entity-> {
                            //entityInfoList.add( entity)
                            bufor.append( key.get(0)+ entity.getEntityAccountNumber()+
                                    " > "+ entity.getEntityName()+ "\n");
                            bufor.append( String.format( rowFormat,
                                    "Tp", "Document", "Date   ", "Due    ", "Variance", "Dt   ", "Ct   ", "Saldo   "));
                            bufor.append( dashes);

                            var sum= new Sum();
                            lines.stream()
                                    .forEach( settlement-> {
                                        sum.addDt( settlement.getDt()).addCt( settlement.getCt());
                                        total.addDt( settlement.getDt()).addCt( settlement.getCt());
                                        bufor.append( toPrint( settlement));
                                    });
                            bufor.append( dashes);
                            if( sum.getCount()>2)
                                bufor.append( String.format( sumFormat, "Sum:",
                                        Util.toString( sum.getDt()),
                                        Util.toString( sum.getCt()),
                                        Util.toString( sum.getSaldo())));
                            bufor.append( "\n");
                        })
                );

        bufor.append( dashes);
        if( total.getCount()>2)
            bufor.append( String.format( sumFormat, "Total:",
                    Util.toString( total.getDt()), Util.toString( total.getCt()), Util.toString( total.getSaldo())));

        return ResponseEntity.ok(  bufor.toString());

         */
    }

    private String toPrint( SettlementListDto settlement) {

        var saldo= settlement.getDt().subtract( settlement.getCt());

        return String.format( rowFormat,
                settlement.getType().accept( this).name(),
                settlement.getNumber(),
                Util.toString( settlement.getDate()),
                Util.toString( settlement.getDue()),
                saldo.signum()== 0? "-": ChronoUnit.DAYS.between( LocalDate.now(), settlement.getDue()),
                Util.toString( settlement.getDt()),
                Util.toString( settlement.getCt()),
                Util.toString( saldo)
        );
    }


    static class EntityInfo {
        private final EntityType type;
        private final String number;

        public EntityInfo( String accountNumber) {
            type= EntityType.valueOf( accountNumber.substring( 0, 1));
            number= accountNumber.substring( 1);
        }
    };
}
