package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private Map<String, String> map = new HashMap<>();

    public ArgsName(String[] arguments) {
        for (String argument : arguments) {
            validate(argument);
        }
    }

    private void validate(String argument) {
        String[] splited = argument.split("=", 2);
        if (splited.length != 2) {
            throw new IllegalArgumentException("The \"=\" sign is missing in '" + argument + "'");
        }
        if ("".equals(splited[1])) {
            throw new IllegalArgumentException("Value is missing in '" + argument + "'");
        }
        if (!splited[0].startsWith("-")) {
            throw new IllegalArgumentException("Key must be start with \"-\" in '" + argument + "'");
        }
        if (splited[0].split("-", 2)[1].isEmpty()) {
            throw new IllegalArgumentException("Key is missing in '" + argument + "'");
        }
        if (splited[0].split("-", 2)[1].contains("-")) {
            throw new IllegalArgumentException("Key is incorrect in '" + argument + "'");
        }
        map.put(splited[0].split("-")[1], splited[1]);
    }

    public Map<String, String> getMap() {
        return map;
    }
}
