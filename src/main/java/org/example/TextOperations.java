package org.example;

import java.util.List;

// Functional interface for text operations
@FunctionalInterface
public interface TextOperations<R> {
    R apply(List<String> lines); // Abstract method to apply an operation on a list of lines
}


