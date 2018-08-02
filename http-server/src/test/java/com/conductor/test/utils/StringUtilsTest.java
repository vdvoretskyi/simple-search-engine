package com.conductor.test.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.conductor.test.utils.StringUtils.combine;
import static com.conductor.test.utils.StringUtils.splitByWords;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class StringUtilsTest {

    @Test
    void testSplitByWords() {
        assertThat(splitByWords(""), containsInAnyOrder(""));
        assertThat(splitByWords("word"), containsInAnyOrder("word"));
        assertThat(splitByWords("word1 word2"), containsInAnyOrder("word1", "word2"));
        assertThat(splitByWords("word1 \n word2"), containsInAnyOrder("word1", "word2"));
        assertThat(splitByWords("\tword1\t\nword2\n"), containsInAnyOrder("word1", "word2"));
    }

    @Test
    void testCombine() {
        assertEquals(combine(Arrays.asList(new String[] {"word"}), " "), "word");
        assertEquals(combine(Arrays.asList(new String[] {""}), " "), "");
        assertEquals(combine(Arrays.asList(new String[] {"word1", "word2"}), " "), "word1 word2");
    }
}