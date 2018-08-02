package com.conductor.test.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;

class CollectionUtilsTest {

    @Test
    void intersect() {
        final Set<String> set1 = new HashSet<>();
        set1.addAll(Arrays.asList(new String[] {"w1", "w2"}));
        final Set<String> set2 = new HashSet<>();
        set2.addAll(Arrays.asList(new String[] {"w1", "w2", "w3"}));

        final Set<Set<String>> set = new HashSet<>();
        set.add(set1);
        set.add(set2);

        assertThat(CollectionUtils.intersect(set.stream()), containsInAnyOrder("w1", "w2"));

        final Set<Set<String>> emptySet = new HashSet<>();
        assertThat(CollectionUtils.intersect(emptySet.stream()), is(empty()));

        final Set<Set<String>> singleSet = new HashSet<>();
        singleSet.add(set1);
        assertThat(CollectionUtils.intersect(singleSet.stream()), containsInAnyOrder("w1", "w2"));

        final Set<Set<String>> oneSetIsEmpty = new HashSet<>();
        oneSetIsEmpty.add(set1);
        oneSetIsEmpty.add(new HashSet<>());
        assertThat(CollectionUtils.intersect(oneSetIsEmpty.stream()), is(empty()));

        final Set<String> set3 = new HashSet<>();
        set3.addAll(Arrays.asList(new String[] {"w3", "w4"}));
        final Set<Set<String>> noIntersectionSet = new HashSet<>();
        noIntersectionSet.add(set1);
        noIntersectionSet.add(set3);
        assertThat(CollectionUtils.intersect(oneSetIsEmpty.stream()), is(empty()));
    }
}