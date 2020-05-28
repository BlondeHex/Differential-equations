package utils;


public class Triple<A, B, C> {
    public final A fst;
    public final B snd;
    public final C thrd;

    public Triple(A fst, B snd, C thrd) {
        this.fst = fst;
        this.snd = snd;
        this.thrd = thrd;
    }


    public static <A, B, C> Triple<A, B, C> of(A a, B b, C c) {
        return new Triple(a, b, c);
    }
}