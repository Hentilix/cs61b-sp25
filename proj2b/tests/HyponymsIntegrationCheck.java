import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.AutograderBuddy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Small end-to-end checks using a self-contained WordNet and NGram dataset. */
public class HyponymsIntegrationCheck {
    private static void check(NgordnetQueryHandler handler, List<String> words,
                              int start, int end, int k, String expected) {
        String actual = handler.handle(new NgordnetQuery(words, start, end, k));
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("hyponyms-check");
        try {
            Path synsets = dir.resolve("synsets.txt");
            Path hyponyms = dir.resolve("hyponyms.txt");
            Path words = dir.resolve("words.csv");
            Path counts = dir.resolve("counts.csv");
            Files.writeString(synsets, "0,animal creature,definition, with comma\n"
                    + "1,dog canine,definition\n2,cat feline,definition\n"
                    + "3,animal beast,another sense\n4,bat,definition\n"
                    + "5,club,definition\n6,bat,another sense\n7,sport,definition\n"
                    + "8,puppy,definition\n");
            Files.writeString(hyponyms, "0,1\n0,2\n1,8\n3,4\n5,6\n7,5\n");
            Files.writeString(words, "animal\t2000\t3\t1\ncanine\t2000\t8\t1\n"
                    + "dog\t2000\t5\t1\ndog\t2001\t6\t1\ncat\t2000\t2\t1\n"
                    + "puppy\t1999\t100\t1\nbat\t2000\t4\t1\n");
            Files.writeString(counts, "1999,1000,1,1\n2000,1000,1,1\n2001,1000,1,1\n");
            NgordnetQueryHandler h = AutograderBuddy.getHyponymsHandler(
                    words.toString(), counts.toString(), synsets.toString(), hyponyms.toString());
            check(h, List.of("animal"), 0, 0, 0,
                    "[animal, bat, beast, canine, cat, creature, dog, feline, puppy]");
            check(h, List.of("animal", "sport"), 0, 0, 0, "[bat]");
            check(h, List.of("animal"), 2000, 2001, 2, "[canine, dog]");
            check(h, List.of("animal"), 2000, 2000, 10, "[animal, bat, canine, cat, dog]");
            check(h, List.of("animal", "sport"), 2000, 2000, 1, "[bat]");
            check(h, List.of("animal"), 2010, 2020, 2, "[]");
            check(h, List.of("animal"), 2001, 2000, 2, "[]");
            check(h, List.of(), 0, 0, 0, "[]");
            check(h, List.of("missing"), 0, 0, 0, "[]");
            check(h, List.of("animal", "missing"), 0, 0, 0, "[]");
            check(h, List.of("dog"), 0, 0, 0, "[canine, dog, puppy]");
            System.out.println("Hyponyms integration checks passed.");
        } finally {
            try (var files = Files.list(dir)) {
                for (Path file : files.toList()) {
                    Files.delete(file);
                }
            }
            Files.delete(dir);
        }
    }
}
