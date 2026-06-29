package pl.janksiegowy.backend.billing.group;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.billing.agreement.AgreementQueryRepository;
import pl.janksiegowy.backend.billing.agreement.dto.AgreementDto;
import pl.janksiegowy.backend.billing.common.BillingType;
import pl.janksiegowy.backend.billing.group.dto.BillingGroupItemDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class BillingGroupFacade {

    private final AgreementQueryRepository agreements;

    public void completeGroup( UUID groupId) {
        for( AgreementDto agreement: agreements.findCandidates( BillingType.maskOf( BillingType.ZUS),
                LocalDate.of( 2026, 6, 1))){
            System.err.println( "Umowa: "+ agreement.getBillingTypes());
        };

        long billingMask= BillingType.maskOf( BillingType.CIT, BillingType.VAT);
        var candidates= agreements.findCandidates( billingMask, LocalDate.of( 2026, 6, 1));

        for( AgreementDto agreement: candidates){
            System.err.println( "Umowa: "+ agreement.getBillingTypes());
        };


        List<BillingGroupItemDto> toSave = candidates.stream()
                .flatMap(a-> matchingTypes( a, billingMask).stream()
                        //.filter(t -> !assigned.contains(new AssignedPair(a.getEntityId(), t)))
                        .map(t-> (BillingGroupItemDto) BillingGroupItemDto.create()
                                .billingType( t)
                                .entity( a.getEntity())
                        ))
                .toList();

        for( BillingGroupItemDto item: toSave){
            System.err.println( "Item: "+ item.getEntity().getName());
        }


/*
        // 1. kandydaci wg kryteriów biznesowych
        List<Agreement> candidates = agreementRepository
                .findCandidates(group.getBillingTypeMask());

// 2. zajęte pary dla okresu grupy (po period_id pozycji)
        Set<AssignedPair> assigned = groupItemRepository
                .findAssignedPairs(group.getBillingTypeMask(), group.getPeriodId())
                .stream()
                .map(p -> new AssignedPair(p.getAgreementId(), BillingType.valueOf(p.getBillingType())))
                .collect(Collectors.toSet());

// 3. buduj pozycje
        Set<BillingType> types = BillingType.fromMask(group.getBillingTypeMask());

        List<BillingGroupItem> toSave = candidates.stream()
                .flatMap(a -> periodsFor(a, group).stream()
                        .flatMap(period -> types.stream()
                                .filter(t -> (a.getBillingTypeMask() & t.bit()) != 0)
                                .filter(t -> !assigned.contains(new AssignedPair(a.getId(), t)))
                                .map(t -> new BillingGroupItem(a, t, group, period))))
                .toList();

        groupItemRepository.saveAll(toSave);

 */
    }

    private Set<BillingType> matchingTypes( AgreementDto agreement, long billingGroupMask){ //BillingGroup group) {
        return BillingType.getBillingTypes(agreement.getBillingTypeMask() & billingGroupMask);
    }
}
