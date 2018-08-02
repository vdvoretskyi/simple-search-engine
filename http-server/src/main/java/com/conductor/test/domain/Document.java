package com.conductor.test.domain;

import com.conductor.test.utils.StringUtils;

import java.util.*;

public final class Document {

    private final String words;

    public Document(final String words) {
        Objects.requireNonNull(words);
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    public Set<String> getSetOfDistinctWords() {
        return new HashSet<>(StringUtils.splitByWords(words));
    }

    @Override
    public String toString() {
        return "Document{" +
                "words='" + words + '\'' +
                '}';
    }
}
