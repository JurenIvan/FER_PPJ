package semanal;

import semanal.nodes.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static semanal.NodeType.*;

public class NodeFactory {

    private static final Map<String, Function<Node, Node>> nodes = new HashMap<>();

    static {
        nodes.put(ADITIVNI_IZRAZ.symbolName, AditivniIzraz::new);
        nodes.put(BIN_I_IZRAZ.symbolName, BinIIzraz::new);
        nodes.put(BIN_ILI_IZRAZ.symbolName, BinIliIzraz::new);
        nodes.put(BIN_XILI_IZRAZ.symbolName, BinXiliIzraz::new);
        nodes.put(CAST_IZRAZ.symbolName, CastIzraz::new);
        nodes.put(DEFINICIJA_FUNKCIJE.symbolName, DefinicijaFunkcije::new);
        nodes.put(DEKLARACIJA.symbolName, Deklaracija::new);
        nodes.put(DEKLARACIJA_PARAMETRA.symbolName, DeklaracijaParametra::new);
        nodes.put(IME_TIPA.symbolName, ImeTipa::new);
        nodes.put(INICIJALIZATOR.symbolName, Inicijalizator::new);
        nodes.put(INIT_DEKLARATOR.symbolName, InitDeklarator::new);
        nodes.put(IZRAVNI_DEKLARATOR.symbolName, IzravniDeklarator::new);
        nodes.put(IZRAZ.symbolName, Izraz::new);
        nodes.put(IZRAZ_NAREDBA.symbolName, IzrazNaredba::new);
        nodes.put(IZRAZ_PRIDRUZIVANJA.symbolName, IzrazPridruzivanja::new);
        nodes.put(JEDNAKOSNI_IZRAZ.symbolName, JednakosniIzraz::new);
        nodes.put(LISTA_ARGUMENATA.symbolName, ListaArgumenata::new);
        nodes.put(LISTA_DEKLARACIJA.symbolName, ListaDeklaracija::new);
        nodes.put(LISTA_INIT_DEKLARATORA.symbolName, ListaInitDeklaratora::new);
        nodes.put(LISTA_IZRAZA_PRIDRUZIVANJA.symbolName, ListaIzrazaPridruzivanja::new);
        nodes.put(LISTA_NAREDBI.symbolName, ListaNaredbi::new);
        nodes.put(LISTA_PARAMETARA.symbolName, ListaParametara::new);
        nodes.put(LOG_I_IZRAZ.symbolName, LogIIzraz::new);
        nodes.put(LOG_ILI_IZRAZ.symbolName, LogIliIzraz::new);
        nodes.put(MULTIPLIKATIVNI_IZRAZ.symbolName, MultiplikativniIzraz::new);
        nodes.put(NAREDBA.symbolName, Naredba::new);
        nodes.put(NAREDBA_GRANANJA.symbolName, NaredbaGrananja::new);
        nodes.put(NAREDBA_PETLJE.symbolName, NaredbaPetlje::new);
        nodes.put(NAREDBA_SKOKA.symbolName, NaredbaSkoka::new);
        nodes.put(ODNOSNI_IZRAZ.symbolName, OdnosniIzraz::new);
        nodes.put(POSTFIKS_IZRAZ.symbolName, PostfiksIzraz::new);
        nodes.put(PRIJEVODNA_JEDINICA.symbolName, PrijevodnaJedinica::new);
        nodes.put(PRIMARNI_IZRAZ.symbolName, PrimarniIzraz::new);
        nodes.put(SLOZENA_NAREDBA.symbolName, SlozenaNaredba::new);
        nodes.put(SPECIFIKATOR_TIPA.symbolName, SpecifikatorTipa::new);
        nodes.put(UNARNI_IZRAZ.symbolName, UnarniIzraz::new);
        nodes.put(UNARNI_OPERATOR.symbolName, UnarniOperator::new);
        nodes.put(VANJSKA_DEKLARACIJA.symbolName, VanjskaDeklaracija::new);
    }

    public static Node create(String input, Node parent) {
        return nodes.get(input).apply(parent);
    }
}


