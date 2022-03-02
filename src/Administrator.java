import com.google.gson.*;

import java.io.*;
import java.util.*;

// clasa pentru sortarea obiectelor Definition dupa an
class YearSorter implements Comparator<Definition>{

    @Override
    public int compare(Definition o1, Definition o2) {
        return o1.year.compareTo(o2.year);
    }
}

// clasa pentru sortarea obiectelor Word alfabetic
class WordSorter implements Comparator <Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return o1.word.compareTo(o2.word);
    }
}

public class Administrator {
    public boolean addWord(Word word, String language){
        String check = language + "_" + word.word; // codificare Hash

        if(WorldDict.map.get(check) != null) return false;

        else{
            WorldDict.map.put(check, word);
            return true;
        }
    }

    public boolean removeWord(String word, String language){
        String check = language + "_" + word; // codificare Hash

        if(WorldDict.map.get(check) == null) return false;

        else{
            WorldDict.map.remove(check);
            return true;
        }

    }

    public boolean addDefinitionForWord(String word, String language, Definition definition){
        String check = language + "_" + word; // codificare Hash

        if(WorldDict.map.get(check) == null) return false;

        for (Definition d : WorldDict.map.get(check).definitions)
            if(definition.dict.equals(d.dict)) return false; // verificare nume dictionar

        WorldDict.map.get(check).definitions.add(definition);
        return true;
    }

    public boolean removeDefinition(String word, String language, String dictionary){
        String check = language + "_" + word; // codificare Hash

        if(WorldDict.map.get(check) == null) return false;

        for (Definition d : WorldDict.map.get(check).definitions) {
            if (d.dict.equals(dictionary) && d.dictType.equals("definitions")) {
                WorldDict.map.get(check).definitions.remove(d);
                return true;
            }
        }
        return false;
    }

    public String translateWord(String word, String fromLanguage, String toLanguage){
        String word_en = "";

        for (Map.Entry<String, Word> set : WorldDict.map.entrySet()) {
            int singCheck = 0;
            int plurCheck = 0; // pozitiile din listele de singular si plural
            int singPos = 0;
            int plurPos = 0; // alegerea listei de singular sau plural

            if (set.getKey().startsWith(fromLanguage + "_")) {
                for (String singular : set.getValue().singular) {
                    if (word.equals(singular)) {
                        singCheck = 1;
                        word_en = set.getValue().word_en;
                        break;
                    }
                    singPos += 1;
                } // iterare si cautare in lista de singular

                for (String plural : set.getValue().plural) {
                    if (word.equals(plural)) {
                        plurCheck = 1;
                        word_en = set.getValue().word_en;
                        break;
                    }
                    plurPos += 1;
                } // iterare si cautare in lista de plural

                if (singCheck == 1) {
                    for (Map.Entry<String, Word> set2 : WorldDict.map.entrySet())
                        if (set2.getKey().startsWith(toLanguage + "_") && set2.getValue().word_en.equals(word_en)) // conditie pentru traducere
                            return set2.getValue().singular.get(singPos);
                }

                else if (plurCheck == 1) {
                    for (Map.Entry<String, Word> set2 : WorldDict.map.entrySet())
                        if (set2.getKey().startsWith(toLanguage + "_") && set2.getValue().word_en.equals(word_en)) // conditie pentru traducere
                            return set2.getValue().plural.get(plurPos);
                }
            }
        }

        return word;
    }

    public String translateSentence(String sentence, String fromLanguage, String toLanguage){
        StringBuilder translate = new StringBuilder();
        String[] words = sentence.split(" ");

        for(String word : words){
            translate.append(translateWord(word, fromLanguage, toLanguage)).append(" "); // aplicare translateWord pentru fiecare cuvant
        }

        return translate.toString();
    }

    public ArrayList <String> translateSentences(String sentence, String fromLanguage, String toLanguage){
        ArrayList <String> list = new ArrayList<>();
        String[] words = sentence.split(" ");

        for(int i = 0; i < 3; i++){ // parcurgere de 3 ori
            StringBuilder translate = new StringBuilder();

            for(String word : words){
                String check = fromLanguage + "_" + word; // hash-ul cuvantului de tradus
                int checkPut = 0; // verificator in cazul in care cuvantul nu exista in dictionarul toLanguage, dar exista in dictionarul fromLanguage

                if(WorldDict.map.get(check) == null){ // cazul in care cuvantul nu exista in dicitonarul fromLanguage
                    translate.append(word).append(" ");
                    break;
                }

                String word_en = WorldDict.map.get(check).word_en;

                for(Map.Entry <String, Word> set : WorldDict.map.entrySet()){
                    if(set.getValue().word_en.equals(word_en) && set.getKey().startsWith(toLanguage + "_")){ // conditie pentru gasire cuvant in dictionarul toLanguage
                        for (Definition d : set.getValue().definitions) { // parcurgere dictionare
                            if (d.dictType.equals("synonyms")) {
                                if (i < d.text.size()) {
                                    translate.append(d.text.get(i)).append(" ");
                                    checkPut = 1;
                                }
                                else{
                                    translate.append(set.getValue().word).append(" ");
                                    checkPut = 1;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                if(checkPut == 0)
                    translate.append(word).append(" ");
            }
            list.add(translate.toString());
        }

        return list;
    }

    public ArrayList <Definition> getDefinitionsForWord(String word, String language){
        String check = language + "_" + word; // codificare Hash

        if(WorldDict.map.get(check) == null) {
            System.out.println("Word '" + word + "' doesn't exist in dictinary '" + language + "'.");
            return new ArrayList<>();
        }
        ArrayList <Definition> list = new ArrayList<>(WorldDict.map.get(check).definitions); // creare lista si adaugare toate elementele de tip Definition
        list.sort(new YearSorter()); // sortare dupa anul aparitiei

        return list;
    }

    public void exportDictionary(String language) throws FileNotFoundException {
        ArrayList <Word> list = new ArrayList<>();

        // adaugare in dictionar
        for (Map.Entry <String, Word> set : WorldDict.map.entrySet()){
            if(set.getKey().startsWith(language + "_")) {
                list.add(set.getValue());
            }
        }

        // verificare lista vida
        if(list.isEmpty()){
            System.out.println("Dictionary '" + language + "' is empty.");
            return;
        }

        // sortare obiecte Word si obiecte Definition
        list.sort(new WordSorter());
        for (Word w : list)
            w.definitions.sort(new YearSorter());

        // creare uglyJSON
        Gson ugly = new Gson();
        String uglyJSONString = ugly.toJson(list);

        // transformare din uglyJSON in prettyJSON
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJSONString);
        String prettyJSONString = pretty.toJson(je);

        // construire si scriere in fisier
        File file = new File(language + "_dict.json");
        PrintWriter pw = new PrintWriter(file);
        pw.print(prettyJSONString);
        pw.close();

        System.out.println("Dictionary '" + language + "' exported.");
    }
}
