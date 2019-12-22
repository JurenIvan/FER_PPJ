package semanal;

import semanal.node.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class NodeFactory {

    private static final Map<String, Function<Node, Node>> nodes = new HashMap<>();;

    static {

        nodes.put("<primarni_izraz>", PrimarniIzraz::new);
        nodes.put("<postfiks_izraz>", PostfiksIzraz::new);
        nodes.put("<lista_argumenata>", ListaDeklaracija::new);
        nodes.put("<unarni_izraz>", UnarniIzraz::new);
        nodes.put("<unarni_operator>", UnarniOperator::new);
        nodes.put("<cast_izraz>", CastIzraz::new);
        nodes.put("<ime_tipa>", ImeTipa::new);
        nodes.put("<specifikator_tipa>", SpecifikatorTipa::new);
        nodes.put("<multiplikativni_izraz>", MultiplikativniIzraz::new);
        nodes.put("<aditivni_izraz>", AditivniIzraz::new);
        nodes.put("<odnosni_izraz>", OdnosniIzraz::new);
        nodes.put("<jednakosni_izraz>", JednakosniIzraz::new);
        nodes.put("<bin_i_izraz>", BinIIzraz::new);
        nodes.put("<bin_xili_izraz>", BinXiliIzraz::new);
        nodes.put("<bin_ili_izraz>", BinIliIzraz::new);
        nodes.put("<log_i_izraz>", LogIIzraz::new);
        nodes.put("<log_ili_izraz>", LogIliIzraz::new);
        nodes.put("<izraz_pridruzivanja>", IzrazNaredba::new);
        nodes.put("<izraz>", Izraz::new);
        nodes.put("<slozena_naredba>", SlozenaNaredba::new);
        nodes.put("<lista_naredbi>", ListaNaredbi::new);
        nodes.put("<naredba>", Naredba::new);
        nodes.put("<izraz_naredba>", IzrazNaredba::new);
        nodes.put("<naredba_grananja>", NaredbaGrananja::new);
        nodes.put("<naredba_petlje>", NaredbaPetlje::new);
        nodes.put("<naredba_skoka>", NaredbaSkoka::new);
        nodes.put("<prijevodna_jedinica>", PrijevodnaJedinica::new);
        nodes.put("<vanjska_deklaracija>", VanjskaDeklaracija::new);
        nodes.put("<definicija_funkcije>", DefinicijaFunkcije::new);
        nodes.put("<lista_parametara>", ListaParametara::new);
        nodes.put("<deklaracija_parametra>", DeklaracijaParametra::new);
        nodes.put("<lista_deklaracija>", ListaDeklaracija::new);
        nodes.put("<deklaracija>", Deklaracija::new);
        nodes.put("<lista_init_deklaratora>", ListaInitDeklaratora::new);
        nodes.put("<init_deklarator>", InitDeklarator::new);
        nodes.put("<izravni_deklarator>", IzravniDeklarator::new);
        nodes.put("<inicijalizator>", Inicijalizator::new);
        nodes.put("<lista_izraza_pridruzivanja>", ListaIzrazaPridruzivanja::new);
    }

    static Node create(String input, Node parent) {
//        if (input.startsWith("<")) {
//            input.replaceAll(" ", "");
            return nodes.get(input).apply(parent);
       // }
//        else {
//            String[] parts = input.split(" ");
//            Boolean lIzraz = parts[0].equals("IDN");
//            if (parts.length > 3) {
//                parts[2] = input.substring(parts[0].length() + parts[1].length() + 2);
//            }
//            //Krivo, unisti ispis
//            //if (parts[0].equals("ZNAK")){ parts[2] = parts[2].replaceAll("'", "");}
//            //if (parts[0].equals("NIZ_ZNAKOVA")){ parts[2] = parts[2].replaceAll("\"", "");}
//            return new UniformniZnak(parts[0], lIzraz, null, Integer.parseInt(parts[1]), 0, parts[2]);
//        }
    }
}


