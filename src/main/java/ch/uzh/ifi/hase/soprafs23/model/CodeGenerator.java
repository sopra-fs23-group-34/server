package ch.uzh.ifi.hase.soprafs23.model;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;


public class CodeGenerator {
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String digits = "0123456789";
    private static final String alphanum = upper + digits;
    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    private CodeGenerator(int length, Random random, String symbols) {
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }
    public CodeGenerator() {
        this(6, new SecureRandom(), alphanum);
    }

    public String nextCode() {
        for (int i = 0; i < buf.length; i++) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}
