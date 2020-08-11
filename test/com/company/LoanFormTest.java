package com.company;

import static org.junit.jupiter.api.Assertions.*;

class LoanFormTest {
    LoanForm form = new LoanForm();

    @org.junit.jupiter.api.Test
    void isDoubleNum() {
        String[] invalidCases = {"se8", "asjdaas", "34ggt5", "FGSGREG"};
        String[] validCases = {"23", "7", "2.5", "2.75"};

        for (String code: invalidCases) {
            assertFalse(form.isDoubleNum(code));
        }

        for (String code: validCases) {
            assertTrue(form.isDoubleNum(code));
        }
    }

    @org.junit.jupiter.api.Test
    void isIntegerNum() {
        String[] invalidCases = {"se8", "asjdaas", "2.5", "34.0", "34ggt5", "FGSGREG"};
        String[] validCases = {"23", "7"};

        for (String code: invalidCases) {
            assertFalse(form.isIntegerNum(code));
        }

        for (String code: validCases) {
            assertTrue(form.isIntegerNum(code));
        }
    }
}