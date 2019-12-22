package semanal;

import semanal.node.*;

import java.util.List;
import java.util.function.Function;

public class SemantickiAnalizator {

	public Inputter inputter;
	private Node rootNode;

	public SemantickiAnalizator() {
		this.inputter = new Inputter();
	}

	public static void main(String[] args) {
		System.out.print(new SemantickiAnalizator().run());
	}

	public static Node nextTask(Node node) {
		if (node.tasks == null || node.tasks.isEmpty()) {
			return null;
		}

		if (node.currentTask == node.tasks.size()) {
			return node.parent;
		}

		node = node.tasks.get(node.currentTask).get();
		node.currentTask++;
		return node;
	}

	public static Node pogreska(Node node) {
		System.out.println("Pogreska na produkciji kod ovog cvora: " + node);
		return null;
	}

	private static String insideBraces(String s) {
		return s.substring(1, s.length() - 1);
	}

	public String run() {
		StringBuilder sb = new StringBuilder();
		List<String> lines = inputter.outputListString();

		rootNode = NodeType.createNode(lines.get(0), null);

		int lastDepth = 0;
		Node currentNode = rootNode;
		for (int i = 1, nlines = lines.size(); i < nlines; i++) {
			String line = lines.get(i);
			String ltrim = line.replaceAll("^\\s+", "");
			int currentDepth = line.length() - ltrim.length();

			int deltaDepth = currentDepth - lastDepth;
			lastDepth = currentDepth;
			if (deltaDepth == 1 || deltaDepth == 0) {
				if (ltrim.startsWith("<")) {
					Node newNode = NodeType.createNode(ltrim, currentNode);
					currentNode.addChild(newNode);
					currentNode = newNode;
				} else {
					System.out.println("terminal --> " + ltrim); // TODO terminal
				}
			} else if (deltaDepth < 0) {
				for (; deltaDepth < 0; deltaDepth++) {
					currentNode = currentNode.parent;
				}
			} else {
				throw new IllegalStateException("Depth can only increase by one step, not " + deltaDepth);
			}

		}

		currentNode = rootNode;

		while (currentNode != null) {
			currentNode = nextTask(currentNode);
		}
		// provjera ima li maina, ako je uspjesno do sada
		// jos neka provjera posle svega, ne sicam se koja

		return sb.toString();
	}

	public static enum TerminalType {
		IDN, BROJ, ZNAK, NIZ_ZNAKOVA, KR_BREAK, KR_CHAR, KR_CONST, KR_CONTINUE, KR_ELSE, KR_FOR, KR_IF, KR_INT, KR_RETURN, KR_VOID, KR_WHILE, PLUS, OP_INC, MINUS, OP_DEC, OP_PUTA, OP_DIJELI, OP_MOD, OP_PRIDRUZI, OP_LT, OP_LTE, OP_GT, OP_GTE, OP_EQ, OP_NEQ, OP_NEG, OP_TILDA, OP_I, OP_ILI, OP_BIN_I, OP_BIN_ILI, OP_BIN_XILI, ZAREZ, TOCKAZAREZ, L_ZAGRADA, D_ZAGRADA, L_UGL_ZAGRADA, D_UGL_ZAGRADA, L_VIT_ZAGRADA, D_VIT_ZAGRADA
	}

	public static enum NodeType {
		ADITIVNI_IZRAZ(AditivniIzraz::new), BIN_I_IZRAZ(BinIIzraz::new), BIN_ILI_IZRAZ(BinIliIzraz::new), BIN_XILI_IZRAZ(
				BinXiliIzraz::new), CAST_IZRAZ(CastIzraz::new), DEFINICIJA_FUNKCIJE(DefinicijaFunkcije::new), DEKLARACIJA(
				Deklaracija::new), DEKLARACIJA_PARAMETRA(DeklaracijaParametra::new), IME_TIPA(ImeTipa::new), INICIJALIZATOR(
				Inicijalizator::new), INIT_DEKLARATOR(InitDeklarator::new), IZRAVNI_DEKLARATOR(IzravniDeklarator::new), IZRAZ(
				Izraz::new), IZRAZ_NAREDBA(IzrazNaredba::new), IZRAZ_PRIDRUZIVANJA(IzrazPridruzivanja::new), JEDNAKOSNI_IZRAZ(
				JednakosniIzraz::new), LISTA_ARGUMENATA(ListaArgumenata::new), LISTA_DEKLARACIJA(
				ListaDeklaracija::new), LISTA_INIT_DEKLARATORA(ListaInitDeklaratora::new), LISTA_IZRAZA_PRIDRUZIVANJA(
				ListaIzrazaPridruzivanja::new), LISTA_NAREDBI(ListaNaredbi::new), LISTA_PARAMETARA(ListaParametara::new), LOG_I_IZRAZ(
				LogIIzraz::new), LOG_ILI_IZRAZ(LogIliIzraz::new), MULTIPLIKATIVNI_IZRAZ(MultiplikativniIzraz::new), NAREDBA(
				Naredba::new), NAREDBA_GRANANJA(NaredbaGrananja::new), NAREDBA_PETLJE(NaredbaPetlje::new), NAREDBA_SKOKA(
				NaredbaSkoka::new), ODNOSNI_IZRAZ(OdnosniIzraz::new), POSTFIKS_IZRAZ(PostfiksIzraz::new), PRIJEVODNA_JEDINICA(
				PrijevodnaJedinica::new), PRIMARNI_IZRAZ(PrimarniIzraz::new), SLOZENA_NAREDBA(SlozenaNaredba::new), SPECIFIKATOR_TIPA(
				SpecifikatorTipa::new), UNARNI_IZRAZ(UnarniIzraz::new), UNARNI_OPERATOR(UnarniOperator::new), VANJSKA_DEKLARACIJA(
				VanjskaDeklaracija::new);
		final Function<Node, Node> nodeConstructorFunction;

		NodeType(Function<Node, Node> nodeConstructorFunction) {
			this.nodeConstructorFunction = nodeConstructorFunction;
		}

		public static Node createNode(String nodeTypeString, Node parent) {
			return valueOf(insideBraces(nodeTypeString.toUpperCase())).nodeConstructorFunction.apply(parent);
		}
	}

}