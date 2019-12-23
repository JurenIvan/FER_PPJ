package semanal.node;

import semanal.TaskResult;
import semanal.TerminalType;
import semanal.Utils;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ;
import static semanal.NodeType.PRIMARNI_IZRAZ;

public class PrimarniIzraz extends Node {

    public boolean lIzraz;
    public String tip;

    public PrimarniIzraz(Node parent) {
        super(parent, PRIMARNI_IZRAZ);
    }

    @Override void initializeTasks() {
        tasks = new ArrayList<>();

        if (hasNChildren(1)) {
            if (isChildOfTerminalType(0, TerminalType.IDN)) {

                tasks.add(() -> {
                    TerminalNode firstChild = getChild(0);
                    // firstChild.getSourceCode() is a variable name TODO check scope
                    tip = "int";
                    lIzraz = false;
                    return TaskResult.success(this);
                });

            } else if (isChildOfTerminalType(0, TerminalType.BROJ)) {

                tasks.add(() -> {
                    TerminalNode firstChild = getChild(0);
                    if (!Utils.checkIfInt(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    tip = "int";
                    lIzraz = false;
                    return TaskResult.success(this);
                });

            } else if (isChildOfTerminalType(0, TerminalType.ZNAK) || isChildOfTerminalType(0, TerminalType.NIZ_ZNAKOVA)) {

                tasks.add(() -> {
                    TerminalNode firstChild = getChild(0);
                    if (!Utils.isValidChar(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    tip = "char";
                    lIzraz = false;
                    return TaskResult.success(this);
                });

            }
        } else if (hasNChildren(3)) {

            if (isChildOfTerminalType(0, TerminalType.L_ZAGRADA) && isChildOfType(1, IZRAZ) && isChildOfTerminalType(2,
                    TerminalType.D_ZAGRADA)) {
                Izraz izraz = getChild(1);
                tasks.add(() -> {
                    return TaskResult.success(izraz);
                });
                tasks.add(() -> {
                    tip = izraz.tip;
                    lIzraz = izraz.lIzraz;
                    return TaskResult.success(this);
                });
            }

        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
