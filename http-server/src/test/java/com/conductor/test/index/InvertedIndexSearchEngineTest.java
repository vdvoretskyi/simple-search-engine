package com.conductor.test.index;

import com.conductor.test.domain.Document;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexSearchEngineTest {

    @Test
    void testPutAndGetDocumentFailed() {
        final SearchEngine searchEngine = new InvertedIndexSearchEngine();
        assertThrows(NullPointerException.class, () -> searchEngine.put(null, null));
        assertThrows(NullPointerException.class, () -> searchEngine.put(null, new Document("")));
        assertThrows(NullPointerException.class, () -> searchEngine.put("", null));
    }

    @Test
    void testPutAndGetDocumentSuccess() {
        final SearchEngine searchEngine = new InvertedIndexSearchEngine();
        assertNull(searchEngine.get(""));

        assertNull(searchEngine.get("key1"));
        final Document document1 = new Document("doc1");
        assertTrue(searchEngine.put("key1", document1));
        assertEquals(searchEngine.get("key1"), document1);

        assertNull(searchEngine.get("key2"));
        final Document document2 = new Document("doc1 doc2");
        assertTrue(searchEngine.put("key2", document2));
        assertEquals(searchEngine.get("key1"), document1);
        assertEquals(searchEngine.get("key2"), document2);

        assertNull(searchEngine.get("key3"));
        assertFalse(searchEngine.put("key1", document1));
        assertFalse(searchEngine.put("key1", document2));
        assertFalse(searchEngine.put("key2", document1));
        assertFalse(searchEngine.put("key2", document2));
        assertEquals(searchEngine.get("key1"), document1);
        assertEquals(searchEngine.get("key2"), document2);
    }

    @Test
    void search() {
        final SearchEngine searchEngine = new InvertedIndexSearchEngine();
        assertThat(searchEngine.search(), is(empty()));
        assertThat(searchEngine.search(""), is(empty()));
        assertThat(searchEngine.search("word"), is(empty()));
        assertThat(searchEngine.search("word1", "word2"), is(empty()));

        searchEngine.put("key1", new Document("doc1-word"));
        assertThat(searchEngine.search("doc1-word"), containsInAnyOrder("key1"));
        assertThat(searchEngine.search("word"), is(empty()));

        searchEngine.put("key2", new Document("doc2-word1 doc2-word2"));
        assertThat(searchEngine.search("doc1-word"), containsInAnyOrder("key1"));
        assertThat(searchEngine.search("doc2-word1"), containsInAnyOrder("key2"));
        assertThat(searchEngine.search(getListOfStrings("doc2-word1", "doc2-word2")), containsInAnyOrder("key2"));
        assertThat(searchEngine.search(getListOfStrings("doc2-word1", "word")), is(empty()));

        searchEngine.put("key3", new Document("doc2-word1 doc3-word doc1-word"));
        assertThat(searchEngine.search(getListOfStrings("doc2-word1", "doc1-word")), containsInAnyOrder("key3"));
        assertThat(searchEngine.search(getListOfStrings("doc1-word")), containsInAnyOrder("key3", "key1"));
        assertThat(searchEngine.search(getListOfStrings("doc2-word1", "doc2-word2")), containsInAnyOrder("key2"));
        assertThat(searchEngine.search(getListOfStrings("doc3-word")), containsInAnyOrder("key3"));

        searchEngine.put("key4", new Document("doc1-word doc2-word1 doc2-word2 doc3-word"));
        assertThat(searchEngine.search(getListOfStrings("doc1-word", "doc2-word1", "doc2-word2", "doc3-word")), containsInAnyOrder("key4"));
        assertThat(searchEngine.search("doc1-word"), containsInAnyOrder("key4", "key3", "key1"));
        assertThat(searchEngine.search(getListOfStrings("doc2-word1", "doc2-word2")), containsInAnyOrder("key4", "key2"));

        assertThat(searchEngine.search("word"), is(empty()));
    }

    private static List<String> getListOfStrings(final String... words) {
        return Arrays.asList(words);
    }
}