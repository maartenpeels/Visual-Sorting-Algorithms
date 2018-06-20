package helpers;

import enums.AccessType;

public class Action {
    private AccessType type;
    private int i;
    private int j;

    public Action(AccessType type, int i, int j) {
        this.type = type;
        this.i = i;
        this.j = j;
    }

    public AccessType getType() {
        return type;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
