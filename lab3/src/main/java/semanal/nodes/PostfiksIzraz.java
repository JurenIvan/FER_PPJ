package semanal.nodes;

import semanal.Node;
import semanal.types.NumberType;
import semanal.types.SubType;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.IZRAZ;
import static semanal.NodeType.POSTFIKS_IZRAZ;

public class PostfiksIzraz extends Node {

    public Type type; // TODO should it be private and accesed by getter?
    public boolean leftAssignableExpression; // TODO should it be private and accesed by getter?

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
                type = primarniIzraz.type;
                leftAssignableExpression = primarniIzraz.leftAssignableExpression;
            });

        } else if (hasNChildren(2)) {
            PostfiksIzraz postfiksIzraz = getChild(0);
            // TerminalNode terminalNode = getChild(1); // TODO can be removed..

            // 1.
            addNodeCheckToTasks(postfiksIzraz);
            // 2.
            addErrorCheckToTasks(
                    postfiksIzraz.leftAssignableExpression == true && NumberType.implicitConvertInto(postfiksIzraz.type, NumberType.INT));

            // final step
            addProcedureToTasks(() -> {
                type = Type.createNumber(NumberType.INT);
                leftAssignableExpression = false;
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
            addErrorCheckToTasks(postfiksIzraz.type.getSubType() == SubType.FUNCTION && postfiksIzraz.type.getFunction().acceptsVoid());

            // final step
            addProcedureToTasks(() -> {
                type = postfiksIzraz.type.getFunction().getReturnValueType();
                leftAssignableExpression = false;
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
                addErrorCheckToTasks(postfiksIzraz.type.getSubType() == SubType.ARRAY);
                // 3.
                addNodeCheckToTasks(izraz);
                // 4.
                addErrorCheckToTasks(NumberType.implicitConvertInto(postfiksIzraz.type, NumberType.INT));

                // final step, after 1-4 tests
                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type.getArray().getElementType();
                    leftAssignableExpression = !type.getNumber().isConst();
                });
            } else {
                ListaArgumenata listaArgumenata = getChild(2);

                // 1.
                addNodeCheckToTasks(postfiksIzraz);
                // 2.
                addNodeCheckToTasks(listaArgumenata);
                // 3.
                addErrorCheckToTasks(postfiksIzraz.type.getSubType() == SubType.FUNCTION);
                addProcedureToTasks(() -> {
                    // TODO ... "argTip ~ paramTip" check..
                });

                // final step
                addProcedureToTasks(() -> {
                    type = postfiksIzraz.type.getFunction().getReturnValueType();
                    leftAssignableExpression = false;
                });
            }
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }

    }
}
