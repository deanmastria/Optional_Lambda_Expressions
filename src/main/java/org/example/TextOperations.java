package org.example;

import java.util.List;

@FunctionalInterface
public interface TextOperations<R> {
    R apply(List<String> lines);
}

