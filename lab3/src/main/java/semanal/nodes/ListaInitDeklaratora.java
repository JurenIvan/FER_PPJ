package semanal.nodes;

import semanal.Node;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.List;

import static semanal.NodeType.LISTA_INIT_DEKLARATORA;

public class ListaInitDeklaratora extends Node {

    public Type type;
    public List<Type> argumentTypes;

    public ListaInitDeklaratora(Node parent) {
        super(parent, LISTA_INIT_DEKLARATORA);
    }

    @Override
    protected void initializeTasks() {
        tasks = new ArrayList<>();
        /*
         * <lista_init_deklaratora> ::= <init_deklarator> 1. provjeri
         * (<init_deklarator>) uz nasljedno svojstvo <init_deklarator>.ntip ←
         * <lista_init_deklaratora>.ntip • <lista_init_deklaratora> 1 ::=
         * <lista_init_deklaratora> 2 ZAREZ <init_deklarator> 1. provjeri
         * (<lista_init_deklaratora> 2 ) uz nasljedno svojstvo <lista_init_deklaratora>
         * 2 .ntip ← <lista_init_deklaratora> 1 .ntip 2. provjeri (<init_deklarator>) uz
         * nasljedno svojstvo <init_deklarator>.ntip ← <lista_init_deklaratora> 1 .ntip
         *
         * 
         * <lista_init_deklaratora> ::= <init_deklarator> | <lista_init_deklaratora>
         * ZAREZ <init_deklarator>
         */

        if (hasNChildren(1)) {
            InitDeklarator initDeklarator = getChild(0);
            addNodeCheckToTasks(initDeklarator);
            initDeklarator.type = this.type;
        } else if (hasNChildren(2)) {
            ListaInitDeklaratora listaInitDeklaratora = getChild(0);
            InitDeklarator initDeklarator = getChild(1);

            listaInitDeklaratora.type = this.type;
            initDeklarator.type = this.type;

            addNodeCheckToTasks(listaInitDeklaratora);
            addNodeCheckToTasks(initDeklarator);
            addProcedureToTasks(() -> {
                argumentTypes.addAll(listaInitDeklaratora.argumentTypes);
                argumentTypes.add(initDeklarator.type);
            });

        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
