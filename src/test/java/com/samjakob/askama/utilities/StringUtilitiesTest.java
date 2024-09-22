package com.samjakob.askama.utilities;

import org.junit.jupiter.api.Test;

import static com.samjakob.askama.utilities.StringUtilities.sentenceCase;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilitiesTest {

    @Test
    void testSentenceCase() {
        assertEquals("", sentenceCase(""));
        assertEquals("         ", sentenceCase("         "));
        assertEquals("Hello, world!", sentenceCase("Hello, World!"));
        assertEquals("Go to Rust struct", sentenceCase("Go to Rust Struct"));
        assertEquals("Go to Rust struct ", sentenceCase("Go to Rust Struct "));
        assertEquals(" Go to Rust struct", sentenceCase(" Go to Rust Struct"));
        assertEquals(" Go to Rust struct ", sentenceCase(" Go to Rust Struct "));
        assertEquals("    Go to Rust struct     ", sentenceCase("    Go to Rust Struct     "));
        assertEquals("    Go to the struct     ", sentenceCase("    Go To The Struct     "));
    }

}
