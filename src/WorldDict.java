import java.util.HashMap;

public class WorldDict {
    static HashMap <String, Word> map = new HashMap<>();

    public static String HashCode(String word, String language) {
        return language + "_" + word;
    }
}
