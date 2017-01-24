package main;

/**
 * Created by Akmaral on 23.01.2017.
 */
import java.io.*;

public class FilesApp {

    public static void setText(String record) {

        try(FileWriter writer = new FileWriter("C:\\Study\\3 course\\5 semester\\java_applet\\bot_for_telegram\\notes3.txt", true))
        {
            // запись всей строки
            String text = "\n" + record;
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.append('\n');

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public static String getText() {
        String s="Это было:";
        try(FileReader reader = new FileReader("C:\\Study\\3 course\\5 semester\\java_applet\\bot_for_telegram\\notes3.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){
                s = s + (char)c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return s;
    }
    public static void clearText() {

        try (FileWriter writer = new FileWriter("C:\\Study\\3 course\\5 semester\\java_applet\\bot_for_telegram\\notes3.txt", false)) {
            // запись всей строки
            String text = "";
            writer.write(text);
            // запись по символам
          writer.append(' ');
            writer.append(' ');

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

}

