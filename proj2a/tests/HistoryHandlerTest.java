import browser.NgordnetQuery;
import main.HistoryHandler;
import ngrams.NGramMap;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static utils.Utils.SHORT_WORDS_FILE;
import static utils.Utils.TOTAL_COUNTS_FILE;

public class HistoryHandlerTest {
    @Test
    public void returnsBase64EncodedPng() {
        NGramMap map = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        HistoryHandler handler = new HistoryHandler(map);
        NgordnetQuery query = new NgordnetQuery(List.of("request", "airport"), 2006, 2007, 0);

        byte[] image = Base64.getDecoder().decode(handler.handle(query));

        assertThat(image.length).isGreaterThan(8);
        assertThat(image[0] & 0xff).isEqualTo(0x89);
        assertThat(image[1] & 0xff).isEqualTo(0x50);
        assertThat(image[2] & 0xff).isEqualTo(0x4e);
        assertThat(image[3] & 0xff).isEqualTo(0x47);
    }
}
