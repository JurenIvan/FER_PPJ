package semanal;

public enum VariableType {
    INT("int"), CHAR("char");

    private String typeSourceName;

    VariableType(String typeSourceName) {
        this.typeSourceName = typeSourceName;
    }

    public String getTypeSourceName() {
        return typeSourceName;
    }
}
