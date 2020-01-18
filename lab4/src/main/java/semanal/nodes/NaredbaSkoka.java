package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.TerminalType;
import semanal.types.FunctionModel;
import semanal.types.Type;

import java.util.ArrayList;
import java.util.Objects;

import static semanal.NodeType.*;

public class NaredbaSkoka extends Node {

    public NaredbaSkoka(Node parent) {
        super(parent, NAREDBA_SKOKA);
    }

    @Override protected void initializeTasks() {
        tasks = new ArrayList<>();

        /*
        o---------------o
        o--> 64. str <--o
        o---------------o

		<naredba_skoka> ::=
			KR_CONTINUE TOCKAZAREZ
			| KR_BREAK TOCKAZAREZ
			| KR_RETURN TOCKAZAREZ
			| KR_RETURN <izraz> TOCKAZAREZ

         */

        switch (getChildrenNumber()) {
            case 2: {
                TerminalNode firstChild = getChild(0);
                if (firstChild.getTerminalType() == TerminalType.KR_RETURN) {
                    addErrorCheckToTasks(() -> Type.VOID_TYPE.equals(this.getFunctionIfNodeNestedInADefinition().getReturnValueType()));
                } else {
                    addErrorCheckToTasks(this::isNodeNestedInALoopNode);
                }
                break;
            }
            case 3: {
                Izraz izraz = getChild(1);

                addNodeCheckToTasks(izraz);
                addErrorCheckToTasks(() -> {
                    FunctionModel function = this.getFunctionIfNodeNestedInADefinition();
                    if (function == null)
                        return false;
                    return izraz.type.implicitConvertInto(function.getReturnValueType());
                });
                tasks.add(() -> {
                    friscCodeAppender.appendCommand("POP R6");
                    friscCodeAppender.appendCommand("PUSH R4");
                    friscCodeAppender.appendCommand("RET");
                    return TaskResult.success(this);
                });
                
                break;
            }
            default:
                throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }

    private boolean isNodeNestedInALoopNode() {
        Node node = this.getParent();
        while (node != null) {
            if (node.getNodeType() == NAREDBA_PETLJE)
                return true;
            node = node.getParent();
        }
        return false;
    }

    private FunctionModel getFunctionIfNodeNestedInADefinition() {
        Node node = this.getParent();
        while (node != null) {
            if (node.getNodeType() == DEFINICIJA_FUNKCIJE)
                return Objects.requireNonNull(((DefinicijaFunkcije) node).function);
            node = node.getParent();
        }
        return null;
    }
}
