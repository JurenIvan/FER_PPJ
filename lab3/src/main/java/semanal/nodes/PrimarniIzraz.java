package semanal.nodes;

import semanal.Node;
import semanal.TaskResult;
import semanal.TerminalType;
import semanal.types.ArrayModel;
import semanal.types.NumberType;
import semanal.types.Type;

import java.util.ArrayList;

import static semanal.NodeType.PRIMARNI_IZRAZ;

public class PrimarniIzraz extends Node {

    public boolean leftAssignableExpression; // TODO should it be private and accesed by getter?
    public Type type; // TODO should it be private and accesed by getter?

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
                    type = Type.createNumber(NumberType.INT);
                    leftAssignableExpression = false;
                    return TaskResult.success(this);
                });
                //same as:
                // addErrorCheckToTasks(check_if_variable_in_scope);
                // addProcedureToTasks(()->{ tip = "int"; lIzraz = false; });

            } else if (firstChild.getTerminalType() == TerminalType.BROJ || firstChild.getTerminalType() == TerminalType.ZNAK) {

                NumberType numberType;
                if (firstChild.getTerminalType() == TerminalType.BROJ) {
                    numberType = NumberType.INT;
                } else {
                    numberType = NumberType.CHAR;
                }
                Type number = Type.createNumber(numberType);

                tasks.add(() -> {
                    if (!number.getNumber().checkIfValidValue(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    type = number;
                    leftAssignableExpression = false;
                    return TaskResult.success(this);
                });
                //same as:
                // addErrorCheckToTasks(number.getNumber().checkIfValidValue(firstChild.getSourceCode()));
                // addProcedureToTasks(()->{ type = number; leftAssignableExpression = false; });

            } else if (firstChild.getTerminalType() == TerminalType.NIZ_ZNAKOVA) {

                Type array = Type.createArray(NumberType.CONST_CHAR);

                tasks.add(() -> {
                    if (!ArrayModel.isValidCharArray(firstChild.getSourceCode())) {
                        return TaskResult.failure(this);
                    }
                    type = array;
                    leftAssignableExpression = false;
                    return TaskResult.success(this);
                });
                // same as:
                // addErrorCheckToTasks(ArrayModel.isValidCharArray(firstChild.getSourceCode()));
                // addProcedureToTasks(()->{type = array; leftAssignableExpression = false;});
            }
        } else if (hasNChildren(3)) {
            Izraz izraz = getChild(1);

            tasks.add(() -> {
                return TaskResult.success(izraz);
            }); // same as: addNodeCheckToTasks(izraz);

            tasks.add(() -> {
                type = izraz.type;
                leftAssignableExpression = izraz.leftAssignableExpression;
                return TaskResult.success(this);
            }); // same as: addProcedureToTasks(()->{ type = izraz.type; leftAssignableExpression = izraz.leftAssignableExpression; });
        } else {
            throw new IllegalStateException("Invalid syntax tree structure.");
        }
    }
}
