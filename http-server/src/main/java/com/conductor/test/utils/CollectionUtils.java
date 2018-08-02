package com.conductor.test.utils;

import java.util.*;
import java.util.stream.Stream;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T, C extends Collection<T>> Collection<T> intersect(final Stream<C> stream) {
        final Iterator<C> allLists = stream.iterator();

        if (!allLists.hasNext()) {
            return Collections.emptySet();
        }

        final Set<T> result = new HashSet<>(allLists.next());
        while (allLists.hasNext()) {
            result.retainAll(allLists.next());
        }
        return result;
    }
}
