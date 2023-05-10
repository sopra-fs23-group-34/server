package ch.uzh.ifi.hase.soprafs23.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {
    CodeGenerator generator = new CodeGenerator();
    String code = generator.nextCode();
    @Test
    void testNextCodeLength() {

        assertNotNull(code);
        assertEquals(6, code.length());


    }
    @Test
    void testNextCodeValidChars(){

        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (char c : code.toCharArray()) {
            assertTrue(validChars.indexOf(c) >= 0);
        }
    }
    @Test
    void testNextCodeUniqueCode(){

        String code2 = generator.nextCode();
        assertNotEquals(code, code2);
    }
}

