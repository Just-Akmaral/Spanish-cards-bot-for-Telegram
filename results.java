package main;

import java.util.*;


/**
 * Created by Akmaral on 22.01.2017.
 */
public class results extends main {
    public static final Map<String, Integer> results = createMap();

    public static Map<String, Integer> createMap() {
      Map<String, Integer> result = new HashMap<String, Integer>();
        return result;
    }

    public void setResult(String date, Integer count){
       results.put(date,count);
   }
    public static String getResults(){
      Set<Map.Entry<String, Integer>> set = results.entrySet();
      String s = "";
      for (Map.Entry<String, Integer> me : set) {
          s = s + "\n" + me.getKey() + ": " + me.getValue() + " слов(а,о)";
       }
    //  if (s.equals("-")){s = "Их пока нет";}
        return s;
    }
}
