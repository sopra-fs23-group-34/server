package ch.uzh.ifi.hase.soprafs23.model;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

@Component
public class CodeGenerator {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUM = UPPER + DIGITS;
    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    private CodeGenerator(int length, Random random, String symbols) {
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }
    public CodeGenerator() {
        this(6, new SecureRandom(), ALPHANUM);
    }

    public String nextCode() {
        for (int i = 0; i < buf.length; i++) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }
}
