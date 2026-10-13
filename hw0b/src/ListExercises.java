import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int ans = 0;
        for (int x : L) {
            ans += x;
        }
        return ans;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> ans = new ArrayList<>();
        for (int x : L) {
            if (x % 2 == 0) {
                ans.add(x);
            }
        }
        return ans;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> ans = new ArrayList<>();
        for (int x : L1) {
            if (L2.contains(x)) {
                ans.add(x);
            }
        }
        return ans;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int ans = 0;
        for (String x : words) {
            for (int i = 0; i < x.length(); i++) {
                if (x.charAt(i) == c) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
