package main;


import java.util.*;
import java.io.*;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

public class main extends TelegramLongPollingBot {

    public static final String commmand_1 = "Начать";
    public static final String commmand_2 = "Закончить";
    public static final String commmand_3 = "Результаты";
    public static final  String commmand_4 = "Помощь";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new main());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public String getBotUsername() {
        return "SpanishCardsBot";
    }

    @Override
    public String getBotToken() {
        return "272029468:AAFQ3Tm0YlM68hIOLHRsiSCW-hmBswxFNvA";
    }


    public void onUpdateReceived(Update update) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                boolean isStart = false;
                switch (message.getText()) {
                    case "/start":
                        sendMsg(message,  "Добро пожаловать!\n" //
                                + "Комманды: \n" //
                                + commmand_1+"\n" //
                                + commmand_2+"\n"//
                                + commmand_3+"\n" //
                                + commmand_4+"\n"//
                                + "\n" //
                                + "Для удобства, используйте клавиатуру.\n"//
                                + "Cделано с любовью и болью для экзамена.");
                        break;
                    case commmand_1:
                        isStart = true;
                        sendMsg(message,  "Давайте начнем!");
                        runTest(message);
                        break;
                    case commmand_2:
                        sendMsg(message, "Ну что ж, пора закругляться.");
                        isStart = false;
                        break;
                    case commmand_3:
                        sendMsg(message,"Ваш результат:");
                        break;
                    case commmand_4:
                        sendMsg(message, "Пока ничем не могу помочь.");
                        break;
                    default:
                        sendMsg(message, "Не могу разобрать! Сейчас сбегаю за очками!");
                        break;
                }
            }
    }
    private void runTest(Message message) {
        int count = 5;
        while (count >0) {
            sendMsg(message, randomEspanoWord());
            count--;
        }
    }

    private static String randomEspanoWord() {

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

            String englishWord = "";
            String espanoWord = "";

            englishWord = cardsNumber.get(wordNumber);
            espanoWord = cards.get(englishWord);

            return espanoWord;
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList <>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(commmand_1);
        keyboardFirstRow.add(commmand_2);

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(commmand_3);
        keyboardSecondRow.add(commmand_4);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

