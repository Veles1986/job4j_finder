package ru.job4j.io;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileFinder {
    private static void printToLog(List<Path> listOfPaths, String logFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile))) {
            for (Path path : listOfPaths) {
                writer.write(path.toAbsolutePath().toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Path> searchByName(String dir, String nameOfFile) throws IOException {
        SearchFilesByName searcher = new SearchFilesByName((a) -> a.equals(nameOfFile));
        Files.walkFileTree(Path.of(dir), searcher);
        if (searcher.getListOfPaths() == null) {
            throw new IllegalArgumentException("Matches not found");
        }
        return searcher.getListOfPaths();
    }

    private static List<Path> searchByMask(String dir, String mask) {
        FileFilter fileFilter = new WildcardFileFilter(mask);
        File[] txtFiles = Path.of(dir).toFile().listFiles(fileFilter);
        if (txtFiles == null) {
            throw new IllegalStateException("Matches no found");
        }
        List<Path> listOfPaths = new ArrayList<>();
        for (File file : txtFiles) {
            listOfPaths.add(file.toPath());
        }
        return listOfPaths;
    }

    private static List<Path> searchByRegex(String dir, String regex) throws IOException {
        Pattern pattern = Pattern.compile(regex);
        SearchFilesByName searcher = new SearchFilesByName(str -> {
            Matcher matcher = pattern.matcher(str.toString());
            return matcher.matches();
        });
        Files.walkFileTree(Path.of(dir), searcher);
        if (searcher.getListOfPaths() == null) {
            throw new IllegalArgumentException("Matches not found");
        }
        return searcher.getListOfPaths();
    }

    private static void find(Map<String, String> map) throws IOException {
        List<Path> result = new ArrayList<>();
        String typeOfSearch = map.get("t");
        if ("mask".equals(typeOfSearch)) {
            result = searchByMask(map.get("d"), map.get("n"));
        }
        if ("regex".equals(typeOfSearch)) {
            result = searchByRegex(map.get("d"), map.get("n"));
        }
        if ("name".equals(typeOfSearch)) {
            result = searchByName(map.get("d"), map.get("n"));
        }
        printToLog(result, map.get("o"));
    }

    private static void validate(Map<String, String> map) {
        List<String> keys = List.of("d", "n", "t", "o");
        List<String> values = List.of("mask", "name", "regex");
        if (map.size() != 4) {
            throw new IllegalArgumentException("There must be 4 input arguments, but was " + map.size() + " arguments");
        }
        for (String key : map.keySet()) {
            if (!keys.contains(key)) {
                throw new IllegalArgumentException("Incorrect key in -" + key + "=" + map.get(key));
            }
        }
        if (!values.contains(map.get("t"))) {
            throw new IllegalArgumentException("Incorrect argument for search, there must be 'mask', 'name' or 'regex', but was '" + map.get("t") + "'");
        }
    }

    public static void main(String[] args) throws IOException {
        ArgsName listOfArgs = new ArgsName(args);
        validate(listOfArgs.getMap());
        find(listOfArgs.getMap());
    }
}
