package utils;

/** A collection of file paths to data files. Make sure your data folder is on the same
 *  level as "src".
 */
public class Utils {
    private static final String PREFIX = "./data/";
    private static final String NGRAMS_PREFIX = PREFIX + "ngrams/";
    public static final String TOP_14337_WORDS_FILE = PREFIX + "word_history_size14377.csv";
    public static final String TOP_49887_WORDS_FILE = NGRAMS_PREFIX + "top_49887_words.csv";
    public static final String Q_WORDS_FILE = NGRAMS_PREFIX + "words_that_start_with_q.csv";
    public static final String SHORT_WORDS_FILE = PREFIX + "word_history_size3.csv";
    public static final String TOTAL_COUNTS_FILE = PREFIX + "year_history.csv";
    public static final String SHORTER_WORDS_FILE = PREFIX + "word_history_size4.csv";
}

