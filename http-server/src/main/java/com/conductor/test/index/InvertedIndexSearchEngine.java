package com.conductor.test.index;

import com.conductor.test.domain.Document;
import com.conductor.test.utils.CollectionUtils;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
@Named
public class InvertedIndexSearchEngine implements SearchEngine {

    private final Map<String, Document> documents = new ConcurrentHashMap<>();  // key -> content
    private final Map<String, Set<String>> index = new ConcurrentHashMap<>();   // normalized word -> document keys


    @Override
    public boolean put(final String key, final Document document) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(document);

        if (documents.putIfAbsent(key, document) != null) {
            return false;
        }
        final Set<String> words = document.getSetOfDistinctWords();
        words.stream().map(this::normalizeWord).filter(this::isNotStopWord)
                .forEach(word -> {
                    final Set<String> keys = new HashSet<>();
                    keys.add(key);
                    index.merge(word, keys, (oldKeys, newKeys) -> {
                        newKeys.addAll(oldKeys);
                        return newKeys;
                    });
                });
        return true;
    }

    @Override
    public Document get(final String key) {
        Objects.requireNonNull(key);

        return documents.get(key);
    }

    @Override
    public Set<String> search(final List<String> words) {
        final Set<String> uniqueWords = new HashSet<>(words);
        final Set<Set<String>> setOfKeys =
                uniqueWords.stream()
                        .filter(Objects::nonNull)
                        .map(this::normalizeWord)
                        .filter(this::isNotStopWord)
                        .map(word -> index.getOrDefault(word, new HashSet<>()))
                        .collect(Collectors.toSet());
        return new HashSet<>(CollectionUtils.intersect(setOfKeys.stream()));
    }

    // TODO: this function would need to be extended to more complex rules, and needs to be made locale-specific
    private String normalizeWord(final String word) {
        return word.toLowerCase();
    }

    //TODO: this function should support a list of stop words which wouldn't be indexed like "it", "the", "a", "and"
    private boolean isNotStopWord(final String word) {
        return true;
    }
}
