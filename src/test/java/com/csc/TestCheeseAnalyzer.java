package com.csc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCheeseAnalyzer {

    @Test
    void testMainRunsWithoutError() {
        System.out.println("Running CheeseAnalyzer main...");
        assertDoesNotThrow(() -> CheeseAnalyzer.main(null));
    }
}
