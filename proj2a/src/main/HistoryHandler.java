package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        List<TimeSeries> histories = new ArrayList<>(words.size());
        for (String word : words) {
            histories.add(map.weightHistory(word, query.startYear(), query.endYear()));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, histories);
        return Plotter.encodeChartAsString(chart);
    }
}
