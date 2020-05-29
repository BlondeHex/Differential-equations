package model;


public enum Type {
    LINEAR (10),
    HYPERBOLA (4),
    POWER (2),
    SQUARE (10),
    EXP (2),
    LOG (2);

    private int bias;

    Type(int bias) {
        this.bias = bias;
    }

    public int getBias() {
        return bias;
    }
}
