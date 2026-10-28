package main;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/** WordNet synsets and their directed hyponym relationships. */
public class WordNet {
    private final Map<Integer, List<String>> synsets = new HashMap<>();
    private final Map<String, Set<Integer>> wordIds = new HashMap<>();
    private final Map<Integer, Set<Integer>> edges = new HashMap<>();

    public WordNet(String synsetFile, String hyponymFile) {
        try (Stream<String> lines = Files.lines(Path.of(synsetFile))) {
            lines.forEach(line -> {
                String[] fields = line.split(",", 3);
                int id = Integer.parseInt(fields[0]);
                List<String> words = List.of(fields[1].split(" "));
                synsets.put(id, words);
                for (String word : words) {
                    wordIds.computeIfAbsent(word, ignored -> new HashSet<>()).add(id);
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        try (Stream<String> lines = Files.lines(Path.of(hyponymFile))) {
            lines.forEach(line -> {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                Set<Integer> children = edges.computeIfAbsent(id, ignored -> new HashSet<>());
                for (int i = 1; i < fields.length; i++) {
                    children.add(Integer.parseInt(fields[i]));
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Returns all words in synsets reachable from any sense of word. */
    public Set<String> hyponyms(String word) {
        Set<String> result = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        ArrayDeque<Integer> pending = new ArrayDeque<>(wordIds.getOrDefault(word, Set.of()));
        while (!pending.isEmpty()) {
            int id = pending.removeFirst();
            if (visited.add(id)) {
                result.addAll(synsets.getOrDefault(id, List.of()));
                pending.addAll(edges.getOrDefault(id, Set.of()));
            }
        }
        return result;
    }
}
