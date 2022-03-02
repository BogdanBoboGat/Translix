import java.util.ArrayList;
import java.util.List;

public class Definition {
    String dict;
    String dictType;
    String year;
    List <String> text;

    public Definition(String dict, String dictType, String year, List<String> text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = new ArrayList<>();
        this.text.addAll(text);
    }
}
