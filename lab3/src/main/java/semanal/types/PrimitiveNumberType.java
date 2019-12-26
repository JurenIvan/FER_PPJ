package semanal.types;

public enum PrimitiveNumberType {
    INT("int"), CHAR("char");

    private String name;

    private PrimitiveNumberType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}



