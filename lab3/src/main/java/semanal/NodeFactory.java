package semanal;

import semanal.nodes.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class NodeFactory {

    private static final Map<String, Function<Node, Node>> nodes = new HashMap<>();

    static {
        nodes.put(NodeType.ADITIVNI_IZRAZ.symbolName, AditivniIzraz::new);
        nodes.put(NodeType.BIN_I_IZRAZ.symbolName, BinIIzraz::new);
        nodes.put(NodeType.BIN_ILI_IZRAZ.symbolName, BinIliIzraz::new);
        nodes.put(NodeType.BIN_XILI_IZRAZ.symbolName, BinXiliIzraz::new);
        nodes.put(NodeType.CAST_IZRAZ.symbolName, CastIzraz::new);
        nodes.put(NodeType.DEFINICIJA_FUNKCIJE.symbolName, DefinicijaFunkcije::new);
        nodes.put(NodeType.DEKLARACIJA.symbolName, Deklaracija::new);
        nodes.put(NodeType.DEKLARACIJA_PARAMETRA.symbolName, DeklaracijaParametra::new);
        nodes.put(NodeType.IME_TIPA.symbolName, ImeTipa::new);
        nodes.put(NodeType.INICIJALIZATOR.symbolName, Inicijalizator::new);
        nodes.put(NodeType.INIT_DEKLARATOR.symbolName, InitDeklarator::new);
        nodes.put(NodeType.IZRAVNI_DEKLARATOR.symbolName, IzravniDeklarator::new);
        nodes.put(NodeType.IZRAZ.symbolName, Izraz::new);
        nodes.put(NodeType.IZRAZ_NAREDBA.symbolName, IzrazNaredba::new);
        nodes.put(NodeType.IZRAZ_PRIDRUZIVANJA.symbolName, IzrazPridruzivanja::new);
        nodes.put(NodeType.JEDNAKOSNI_IZRAZ.symbolName, JednakosniIzraz::new);
        nodes.put(NodeType.LISTA_ARGUMENATA.symbolName, ListaArgumenata::new);
        nodes.put(NodeType.LISTA_DEKLARACIJA.symbolName, ListaDeklaracija::new);
        nodes.put(NodeType.LISTA_INIT_DEKLARATORA.symbolName, ListaInitDeklaratora::new);
        nodes.put(NodeType.LISTA_IZRAZA_PRIDRUZIVANJA.symbolName, ListaIzrazaPridruzivanja::new);
        nodes.put(NodeType.LISTA_NAREDBI.symbolName, ListaNaredbi::new);
        nodes.put(NodeType.LISTA_PARAMETARA.symbolName, ListaParametara::new);
        nodes.put(NodeType.LOG_I_IZRAZ.symbolName, LogIIzraz::new);
        nodes.put(NodeType.LOG_ILI_IZRAZ.symbolName, LogIliIzraz::new);
        nodes.put(NodeType.MULTIPLIKATIVNI_IZRAZ.symbolName, MultiplikativniIzraz::new);
        nodes.put(NodeType.NAREDBA.symbolName, Naredba::new);
        nodes.put(NodeType.NAREDBA_GRANANJA.symbolName, NaredbaGrananja::new);
        nodes.put(NodeType.NAREDBA_PETLJE.symbolName, NaredbaPetlje::new);
        nodes.put(NodeType.NAREDBA_SKOKA.symbolName, NaredbaSkoka::new);
        nodes.put(NodeType.ODNOSNI_IZRAZ.symbolName, OdnosniIzraz::new);
        nodes.put(NodeType.POSTFIKS_IZRAZ.symbolName, PostfiksIzraz::new);
        nodes.put(NodeType.PRIJEVODNA_JEDINICA.symbolName, PrijevodnaJedinica::new);
        nodes.put(NodeType.PRIMARNI_IZRAZ.symbolName, PrimarniIzraz::new);
        nodes.put(NodeType.SLOZENA_NAREDBA.symbolName, SlozenaNaredba::new);
        nodes.put(NodeType.SPECIFIKATOR_TIPA.symbolName, SpecifikatorTipa::new);
        nodes.put(NodeType.UNARNI_IZRAZ.symbolName, UnarniIzraz::new);
        nodes.put(NodeType.UNARNI_OPERATOR.symbolName, UnarniOperator::new);
        nodes.put(NodeType.VANJSKA_DEKLARACIJA.symbolName, VanjskaDeklaracija::new);
    }

    static Node create(String input, Node parent) {
        return nodes.get(input).apply(parent);
    }
}


