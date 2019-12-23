package semanal.nodes;

import semanal.Node;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ;
import static semanal.NodeType.POSTFIKS_IZRAZ;

public class PostfiksIzraz extends Node {

    public String tip;
    public boolean lIzraz;

    public PostfiksIzraz(Node parent) {
        super(parent, POSTFIKS_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 52. str <--o
        o---------------o

        <postfiks_izraz> ::=
            <primarni_izraz>
            | <postfiks_izraz> OP_INC
            | <postfiks_izraz> OP_DEC
            | <postfiks_izraz> L_ZAGRADA D_ZAGRADA
            | <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
            | <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA

         */

        if (hasNChildren(1)) {
            PrimarniIzraz primarniIzraz = getChild(0);

            // 1.
            addNodeCheckToTasks(primarniIzraz);

            // final step
            addProcedureToTasks(() -> {
                tip = primarniIzraz.tip;
                lIzraz = primarniIzraz.lIzraz;
            });

        } else if (hasNChildren(2)) {
            PostfiksIzraz postfiksIzraz = getChild(0);
            // TerminalNode terminalNode = getChild(1); // TODO can be removed..

            // 1.
            addNodeCheckToTasks(postfiksIzraz);
            // 2.
            addErrorCheckToTasks(postfiksIzraz.lIzraz == true && postfiksIzraz.tip == " ~ int ( ~ aka impl. svodiv)");  // TODO real type

            // final step
            addProcedureToTasks(() -> {
                tip = "int"; // TODO real type
                lIzraz = false;
            });
        } else if (hasNChildren(3)) {
            PostfiksIzraz postfiksIzraz = getChild(0);
            // TerminalNode firstTerminal = getChild(1); // TODO can be removed..
            // TerminalNode secondTerminal = getChild(2); // TODO can be removed..

            // It is not necessary to check whether first and second terminals are of
            // L_ZAGRADA and D_ZAGRADA types, because parser makes sure that is satisfied
            // and therefore it (TODO) can be removed..

            // 1.
            addNodeCheckToTasks(postfiksIzraz);
            // 2.
            addErrorCheckToTasks(postfiksIzraz.tip == "function(void->POVRATNA_KOJA_SE_IZVADI_IZ_TIPA)"); // TODO real type

            // final step
            addProcedureToTasks(() -> {
                tip = "pov --- povratna vrijednost od funkcije"; // TODO real type
                lIzraz = false;
            });
        } else if (hasNChildren(4)) {
            PostfiksIzraz postfiksIzraz = getChild(0);
            // TerminalNode second = getChild(1); // TODO can be removed..
            // TerminalNode fourth = getChild(3); // TODO can be removed..

            if (isChildOfType(2, IZRAZ)) {
                Izraz izraz = getChild(2);

                // 1.
                addNodeCheckToTasks(postfiksIzraz);
                // 2.
                addErrorCheckToTasks(postfiksIzraz.tip.contains("niz")); // TODO real type
                // 3.
                addNodeCheckToTasks(izraz);
                // 4.
                addErrorCheckToTasks(izraz.tip == "~ int (~ znaci da je implicitno svodiv)"); // TODO real type

                // final step, after 1-4 tests
                addProcedureToTasks(() -> {
                    tip = "X iz postfiksIzraz.tip"; // TODO real type
                    lIzraz = "X iz postfiksIzraz.tip" != "const(T)";  // TODO real type
                });
            } else {
                ListaArgumenata listaArgumenata = getChild(2);

                // 1.
                addNodeCheckToTasks(postfiksIzraz);
                // 2.
                addNodeCheckToTasks(listaArgumenata);
                // 3.
                // TODO function types not implemented, so the .tip check is just dummy lines of code
                addErrorCheckToTasks(postfiksIzraz.tip == "funkcija(params->POVRATNA_OD_TIPA)");
                for (String functionParameter : postfiksIzraz.tip.split("bezveze pisem, tip necemo modelirati stringom..")) {
                    addErrorCheckToTasks(functionParameter == "istog tipa kao respektivni parametar od postfiksniIzraz.tip");
                }

                // final step
                addProcedureToTasks(() -> {
                    tip = "POVRATNA"; // TODO real type
                    lIzraz = false;
                });
            }
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
