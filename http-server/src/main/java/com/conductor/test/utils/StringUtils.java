package com.conductor.test.utils;

import java.util.*;
import java.util.stream.Collectors;

public final class StringUtils {

    private StringUtils() {
    }

    public static List<String> splitByWords(final String text) {
        Objects.requireNonNull(text);
        return Arrays.asList(text.split(WORD_SPLIT_REGEX));
    }
    private static final String WORD_SPLIT_REGEX = "\\s+";

    public static String combine(final Collection<String> strings, final CharSequence delimiter) {
        return strings.stream().collect(Collectors.joining(delimiter));
    }
}
