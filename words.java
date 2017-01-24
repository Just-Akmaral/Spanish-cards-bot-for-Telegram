package main;

import java.util.HashMap;

/**
 * Created by Akmaral on 10.01.2017.
 */
public class words extends main{

    public static String englishWord = "";
    public static String espanoWord = "";

    public void randomWord() {

        HashMap<String, String> cards = new HashMap<String, String>();
        cards.put("hello","hola");
        cards.put("goodbye","adiós");
        cards.put("thank you","gracias");
        cards.put("good","bueno");
        cards.put("always","siempre");
        HashMap<Integer, String>  cardsNumber = new HashMap<Integer, String>();
        int i=0;
        for (String keyCards : cards.keySet()) {
            cardsNumber.put(i, keyCards);
            i++;
        }
        int wordNumber = (int) (Math.random()*cards.size());

        englishWord = cardsNumber.get(wordNumber);
        espanoWord = cards.get(englishWord);

    }
    public static String getWord(String word){
        switch (word){
            case "espano":
                return espanoWord;

            case "english":
                return englishWord;

            default:
                return "smth wrong";

        }
    }
    public String getEnglishWord(){
        return englishWord;
    }
    public static String getEspanoWord(){
        return espanoWord;
    }
}
