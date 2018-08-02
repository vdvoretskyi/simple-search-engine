package com.conductor.test.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.isEmptyString;

class DocumentTest {

    @Test
    void testGetWords() {
        Document document = new Document("");
        assertThat(document.getWords(), isEmptyString());

        document = new Document("word");
        assertThat(document.getWords(), equalTo("word"));

        document = new Document("word1 word2");
        assertThat(document.getWords(), equalTo("word1 word2"));

        Assertions.assertThrows(NullPointerException.class, () -> new Document(null));
    }

    @Test
    void testGetWordsAsList() {
        Document document = new Document("");
        assertThat(document.getSetOfDistinctWords(), containsInAnyOrder(""));

        document = new Document("word");
        assertThat(document.getSetOfDistinctWords(), containsInAnyOrder("word"));

        document = new Document("word1 word2");
        assertThat(document.getSetOfDistinctWords(), containsInAnyOrder("word1", "word2"));

        document = new Document("word1 \n\r word2");
        assertThat(document.getSetOfDistinctWords(), containsInAnyOrder("word1", "word2"));

        document = new Document("word1 word2 word1");
        assertThat(document.getSetOfDistinctWords(), containsInAnyOrder("word1", "word2"));
    }
}