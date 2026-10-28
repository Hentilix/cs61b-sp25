package main;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Handles WordNet queries, optionally filtering by historical counts. */
public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    private final NGramMap ngrams;

    public HyponymsHandler(WordNet wordNet, NGramMap ngrams) {
        this.wordNet = wordNet;
        this.ngrams = ngrams;
    }

    @Override
    public String handle(NgordnetQuery q) {
        if (q.words() == null || q.words().isEmpty() || q.k() < 0) {
            return "[]";
        }
        Set<String> matches = wordNet.hyponyms(q.words().get(0));
        for (int i = 1; i < q.words().size(); i++) {
            matches.retainAll(wordNet.hyponyms(q.words().get(i)));
        }
        if (q.k() == 0) {
            return new TreeSet<>(matches).toString();
        }
        if (q.startYear() > q.endYear()) {
            return "[]";
        }
        Map<String, Double> counts = new HashMap<>();
        for (String word : matches) {
            double total = 0;
            for (double count : ngrams.countHistory(word, q.startYear(), q.endYear()).values()) {
                total += count;
            }
            if (total > 0) {
                counts.put(word, total);
            }
        }
        List<String> ranked = new ArrayList<>(counts.keySet());
        ranked.sort(Comparator.<String, Double>comparing(counts::get).reversed()
                .thenComparing(Comparator.naturalOrder()));
        return new TreeSet<>(ranked.subList(0, Math.min(q.k(), ranked.size()))).toString();
    }
}
