package semanal;

public enum NodeType {
    ADITIVNI_IZRAZ("<aditivni_izraz>"),
    BIN_I_IZRAZ("<bin_i_izraz>"),
    BIN_ILI_IZRAZ("<bin_ili_izraz>"),
    BIN_XILI_IZRAZ("<bin_xili_izraz>"),
    CAST_IZRAZ("<cast_izraz>"),
    DEFINICIJA_FUNKCIJE("<definicija_funkcije>"),
    DEKLARACIJA("<deklaracija_parametra>"),
    DEKLARACIJA_PARAMETRA("<deklaracija>"),
    IME_TIPA("<ime_tipa>"),
    INICIJALIZATOR("<inicijalizator>"),
    INIT_DEKLARATOR("<init_deklarator>"),
    IZRAVNI_DEKLARATOR("<izravni_deklarator>"),
    IZRAZ("<izraz_naredba>"),
    IZRAZ_NAREDBA("<izraz_pridruzivanja>"),
    IZRAZ_PRIDRUZIVANJA("<izraz>"),
    JEDNAKOSNI_IZRAZ("<jednakosni_izraz>"),
    LISTA_ARGUMENATA("<lista_argumenata>"),
    LISTA_DEKLARACIJA("<lista_deklaracija>"),
    LISTA_INIT_DEKLARATORA("<lista_init_deklaratora>"),
    LISTA_IZRAZA_PRIDRUZIVANJA("<lista_izraza_pridruzivanja>"),
    LISTA_NAREDBI("<lista_naredbi>"),
    LISTA_PARAMETARA("<lista_parametara>"),
    LOG_I_IZRAZ("<log_i_izraz>"),
    LOG_ILI_IZRAZ("<log_ili_izraz>"),
    MULTIPLIKATIVNI_IZRAZ("<multiplikativni_izraz>"),
    NAREDBA("<naredba_grananja>"),
    NAREDBA_GRANANJA("<naredba_petlje>"),
    NAREDBA_PETLJE("<naredba_skoka>"),
    NAREDBA_SKOKA("<naredba>"),
    ODNOSNI_IZRAZ("<odnosni_izraz>"),
    POSTFIKS_IZRAZ("<postfiks_izraz>"),
    PRIJEVODNA_JEDINICA("<prijevodna_jedinica>"),
    PRIMARNI_IZRAZ("<primarni_izraz>"),
    SLOZENA_NAREDBA("<slozena_naredba>"),
    SPECIFIKATOR_TIPA("<specifikator_tipa>"),
    UNARNI_IZRAZ("<unarni_izraz>"),
    UNARNI_OPERATOR("<unarni_operator>"),
    VANJSKA_DEKLARACIJA("<vanjska_deklaracija>"),
    TERMINAL("");

    final String symbolName;

    NodeType(String symbolName) {
        this.symbolName = symbolName;
    }
}