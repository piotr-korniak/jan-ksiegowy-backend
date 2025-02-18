package pl.janksiegowy.backend.salary;

public enum WageIndicatorCode {

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

    /** Składka na Fundusz Pracy i Fundusz Solidarnościowy */
    F_FPFS,
    /** Składka na Fundusz Gwarantowanych Świadczeń Pracowniczych */
    F_FGSP,

    /** Koszty uzyskania przychodu */
    KO_UZP,
    /** Kwota wolna od podatku */
    KW_WLN,
    /** Stawka podatku dochodowego (zatrudniony) */
    ST_PDZ,

    /** Kwota brutto */
    KW_BRT,
    /** Kwota netto */
    KW_NET,
    /** Kwota zaliczki */
    KW_ZAL,
    /** Ubezpieczenia ZUS */
    UB_ZUS;
}
