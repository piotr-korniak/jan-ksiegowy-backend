package pl.janksiegowy.backend.salary;

public enum PayslipItemCode {

    /** Ubezpieczenie emerytalne (zatrudniony) */
    UE_ZAT,
    /** Ubezpieczenie rentowe (zatrudniony) */
    UR_ZAT,
    /** Ubezpieczenie chorobowe (zatrudniony) */
    UC_ZAT,
    /** Ubezpieczenie zdrowotne */
    UB_ZDR,

    /** Ubezpieczenie emerytalne (pracodawca) */
    UE_PRA,
    /** Ubezpieczenie rentowe (pracodawca) */
    UR_PRA,
    /** Ubezpieczenie wypadkowe (pracodawca) */
    UW_PRA,

    /** Kwota brutto */
    KW_BRT,
    /** Kwota netto */
    KW_NET,
    /** Kwota zaliczki */
    KW_ZAL;
}
