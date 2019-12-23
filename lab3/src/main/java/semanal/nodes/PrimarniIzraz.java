package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.TerminalType;
import semanal.Utils;

import java.util.ArrayList;

import static semanal.NodeType.PRIMARNI_IZRAZ;

public class PrimarniIzraz extends Node {

    public boolean lIzraz;
    public String tip;

    public PrimarniIzraz(Node parent) {
        super(parent, PRIMARNI_IZRAZ);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 51. str <--o
        o---------------o

        <primarni_izraz> ::=
            IDN
            | BROJ
            | ZNAK
            | NIZ_ZNAKOVA
            | L_ZAGRADA <izraz> D_ZAGRADA
         */

        if (hasNChildren(1)) {
            TerminalNode firstChild = getChild(0);
            if (firstChild.getTerminalType() == TerminalType.IDN) {

                tasks.add(() -> {
                    // firstChild.getSourceCode() is a variable name TODO check scope
                    tip = "int";
                    lIzraz = false;
                    return TaskResult.success(this);
                });
                //same as:
                // addErrorCheckToTasks(check_if_variable_in_scope);
                // addProcedureToTasks(()->{ tip = "int"; lIzraz = false; });

            } else if (firstChild.getTerminalType() == TerminalType.BROJ) {

                tasks.add(() -> {
                    if (!Utils.checkIfInt(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    tip = "int";
                    lIzraz = false;
                    return TaskResult.success(this);
                });

                //same as:
                // addErrorCheckToTasks(Utils.checkIfInt(firstChild.getSourceCode()));
                // addProcedureToTasks(()->{ tip = "int"; lIzraz = false; });

            } else if (firstChild.getTerminalType() == TerminalType.ZNAK) {

                tasks.add(() -> {
                    if (!Utils.isValidChar(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    tip = "char";
                    lIzraz = false;
                    return TaskResult.success(this);
                });

                //same as:
                // addErrorCheckToTasks(Utils.isValidChar(firstChild.getSourceCode()));
                // addProcedureToTasks(()->{ tip = "int"; lIzraz = false; });

            } else if (firstChild.getTerminalType() == TerminalType.NIZ_ZNAKOVA) {

                tasks.add(() -> {
                    if (!Utils.isValidCharArray(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    tip = "niz (const(char))";
                    lIzraz = false;
                    return TaskResult.success(this);
                });
                // same as:
                // addErrorCheckToTasks(Utils.isValidCharArray(firstChild.getSourceCode()));
                // addProcedureToTasks(()->{tip = "niz (const(char))"; lIzraz = false;});
            }
        } else if (hasNChildren(3)) {
            Izraz izraz = getChild(1);

            tasks.add(() -> {
                return TaskResult.success(izraz);
            }); // same as: addNodeCheckToTasks(izraz);

            tasks.add(() -> {
                tip = izraz.tip;
                lIzraz = izraz.lIzraz;
                return TaskResult.success(this);
            }); // same as: addProcedureToTasks(()->{ tip = izraz.tip; lIzraz = izraz.lIzraz; });
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
