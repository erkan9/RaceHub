package org.erkamber.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Expertise {
    BEGINNER,
    INTERMEDIATE,
    EXPERT,
    RACER;

    public static Expertise[] getAllExpertiseLevels() {
        return values();
    }

    public static List<String> getAllExpertiseLevelsAsList() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}