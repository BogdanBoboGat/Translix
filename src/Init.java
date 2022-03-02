import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Init {
    static File init = new File("src/init");
    static File[] files = init.listFiles();

    public static void read(){
        for(File f : files){
            // citire fisier cu fisier
            StringBuilder obj = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(f))){
                String line;
                while ((line = br.readLine()) != null) {
                    obj.append(line); // construire string care contine toate obiectele Word in format json
                }
            }
            catch (FileNotFoundException FileNotFound){System.out.println("File error!");}
            catch (IOException e) {e.printStackTrace();}

            Gson gson = new Gson();
            Type type = new TypeToken<List<Word>>(){}.getType();
            List <Word> words = gson.fromJson(obj.toString(), type); // transformare string obj in lista de obiecte Word

            for(Word w : words){
                WorldDict.map.put(WorldDict.HashCode(w.word, f.getName().split("_")[0]), w); // adaugarea obiectului Word in colectie
            }
        }
    }
}
