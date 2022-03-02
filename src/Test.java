import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void printPretty(String function, String word, boolean check, String language){
        System.out.println(function + " has returned <" + check + "> for word <" + word + "> in language <" + language + ">.");
    }

    public static void testAddWord(boolean resetDict){
        Administrator ad = new Administrator();
        boolean check;

        Init.read();

        List <String> singular = new ArrayList<>();
        List <String> plural = new ArrayList<>();
        List <String> text = new ArrayList<>();
        List <Definition> d = new ArrayList<>();

        text.add("Verbul a alerga, a fugi");
        Definition def = new Definition("DEX", "definitions", "2019", text);
        d.add(def);

        singular.add("alerg");
        singular.add("alergi");
        singular.add("alearga");

        plural.add("alergam");
        plural.add("alergati");
        plural.add("alearga");

        Word w = new Word("alerga", "run", "verb", singular, plural, d);

        check = ad.addWord(w, "ro");
        printPretty("addWord", w.word, check, "ro");

        w = new Word("manger", "eat", "verb", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        check = ad.addWord(w, "fr");
        printPretty("addWord", w.word, check, "fr");

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testRemoveWord(boolean resetDict){
        Administrator ad = new Administrator();
        boolean check;

        Init.read();

        check = ad.removeWord("Katze", "gr");
        printPretty("removeWord", "Katze", check, "gr");

        check = ad.removeWord("programare", "ro");
        printPretty("removeWord", "programare", check, "ro");

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testAddDefinitionForWord(boolean resetDict){
        Administrator ad = new Administrator();
        boolean check;

        Init.read();

        List <String> text = new ArrayList<>();

        text.add("Descriere faina");
        Definition def = new Definition("Un Dictionar", "definitions", "2019", text);

        check = ad.addDefinitionForWord("cal", "ro", def);
        printPretty("addDefinitionForWord", "cal", check, "ro");

        check = ad.addDefinitionForWord("chat", "fr", def);
        printPretty("addDefinitionForWord", "chat", check, "fr");

        def = new Definition("Larousse", "definitions", "2019", text);

        check = ad.addDefinitionForWord("jeu", "fr", def);
        printPretty("addDefinitionForWord", "jeu", check, "fr");

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testRemoveDefinition(boolean resetDict){
        Administrator ad = new Administrator();
        boolean check;

        Init.read();

        check = ad.removeDefinition("tort", "ro", "DEX");
        printPretty("removeDefinition", "tort", check, "ro");

        check = ad.removeDefinition("Katze", "gr", "Wörterbuch");
        printPretty("removeDefinition", "Katze", check, "gr");

        check = ad.removeDefinition("Katze", "gr", "Wörterbuch der deutschen Sprache");
        printPretty("removeDefinition", "Katze", check, "gr");

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testTranslateWord(boolean resetDict){
        Administrator ad = new Administrator();
        String translatedWord;

        Init.read();

        translatedWord = ad.translateWord("mănânc", "ro", "fr");
        System.out.println("Word to translate from 'ro': mănânc -> " +
                           "Translated Word to 'fr': " + translatedWord);

        translatedWord = ad.translateWord("portrait", "fr", "gr");
        System.out.println("Word to translate from 'fr': portrait -> " +
                           "Translated Word to 'gr': " + translatedWord);

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testTranslateSentence(boolean resetDict){
        Administrator ad = new Administrator();
        String translatedSentence;

        Init.read();

        translatedSentence = ad.translateSentence("Katze geht", "gr", "ro");
        System.out.println("Sentence to translate from 'gr': Katze geht -> " +
                "Translated Sentence to 'ro': " + translatedSentence);

        translatedSentence = ad.translateSentence("Katze miaut", "gr", "ro");
        System.out.println("Sentence to translate from 'gr': Katze miaut -> " +
                "Translated Sentence to 'ro': " + translatedSentence);

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testTranslateSentences(boolean resetDict){
        Administrator ad = new Administrator();

        Init.read();

        List <String> translate = ad.translateSentences("câine mânca", "ro", "gr");
        for(String s : translate){
            System.out.println("Sentence to translate from 'ro': câine mânca -> Translated Sentence to 'gr': " + s);
        }
        System.out.println();
        translate = ad.translateSentences("Katze gehen", "gr", "fr");
        for(String s : translate){
            System.out.println("Sentence to translate from 'gr': Katze gehen -> Translated Sentence to 'fr': " + s);
        }

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testGetDefinitionsForWord(boolean resetDict){
        Administrator ad = new Administrator();

        Init.read();

        List <Definition> list = new ArrayList<>(ad.getDefinitionsForWord("câine", "ro"));
        System.out.print("Definitions for word 'câine' in 'ro': ");
        for(Definition d : list)
            System.out.print(d.dict + "->" + d.year + ", ");

        System.out.println();

        list = new ArrayList<>(ad.getDefinitionsForWord("pâine", "ro"));
        System.out.print("Definitions for word 'pâine' in 'ro': ");
        for(Definition d : list)
            System.out.print(d.dict + "->" + d.year + ", ");

        if(resetDict)
            WorldDict.map.clear();
    }

    public static void testExportDictionary() throws FileNotFoundException {
        Administrator ad = new Administrator();

        Init.read();

        System.out.println();
        ad.exportDictionary("gr");
        ad.exportDictionary("it");
    }

    public static void main(String[] args) throws FileNotFoundException {
        testAddWord(true);
        System.out.println();

        testRemoveWord(true);
        System.out.println();

        testAddDefinitionForWord(true);
        System.out.println();

        testRemoveDefinition(true);
        System.out.println();

        testTranslateWord(true);
        System.out.println();

        testTranslateSentence(true);
        System.out.println();

        testTranslateSentences(true);
        System.out.println();

        testGetDefinitionsForWord(true);
        System.out.println();

        testExportDictionary();
        System.out.println();
    }
}