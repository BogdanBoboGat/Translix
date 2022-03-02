import java.util.ArrayList;
import java.util.List;

public class Word {
    String word;
    String word_en;
    String type;
    List <String> singular;
    List <String> plural;
    List <Definition> definitions;

    public Word(String word, String word_en, String type, List <String> singular, List <String> plural, List <Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = new ArrayList<>();
        this.singular.addAll(singular);
        this.plural = new ArrayList<>();
        this.plural.addAll(plural);
        this.definitions = new ArrayList<>();
        this.definitions.addAll(definitions);
    }
}
