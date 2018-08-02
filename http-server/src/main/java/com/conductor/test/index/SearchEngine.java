package com.conductor.test.index;

import com.conductor.test.domain.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface SearchEngine {

    /**
     * Put documents into the search engine by key. If the specified key is not already associated with a document,
     * associates it with the given document returns {@code true}, else returns {@code false}
     * @param key
     * @param document
     * @return {@code true} if no documents were previously associated with given key, {@code false} otherwise
     */
    boolean put(final String key, final Document document);

    /**
     * Returns the document to which the specified key is mapped,
     * or {@code null} if this engine contains no mapping for the key.
     * @param key
     * @return the document to which the specified key is mapped, or
     * {@code null} if this engine contains no mapping for the key
     */
    Document get(final String key);

    /**
     * Search on a string of tokens (words) to return keys of all documents that contain all tokens in the set
     * @param tokens
     * @return set of all documents that contain all tokens in the set or empty set
     */
    Set<String> search(final List<String> tokens);

    default Set<String> search(final String... tokens) {
        return search(Arrays.asList(tokens));
    }
}
