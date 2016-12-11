package main;


import java.util.ArrayList;
import java.util.List;

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

    public static void Main(String[] args) {
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
                switch (message.getText()) {
                    case "/start":
                        sendMsg(message, "Добро пожаловать!\n" //
                                + "Комманды: \n" //
                                + "Начать\n" //
                                + "Закончить\n"//
                                + "Результаты\n" //
                                + "Помощь\n"//
                                + "\n" //
                                + "Для удобства, используйте клавиатуру.\n"//
                                + "Cделано с любовью и болью для экзамена.");
                        break;
                    case "Начать":
                        sendMsg(message, "Давайте начнем!");
                        break;
                    case "Закончить":
                        sendMsg(message, "Ну что ж, пора закругляться.");
                        break;
                    case "Результаты":
                        sendMsg(message, "Ваши результаты:");
                        break;
                    case "Помощь":
                        sendMsg(message, "Пока ничем не могу помочь :(");
                        break;
                    default:
                        sendMsg(message, "Не могу разобрать! Сейчас сбегаю за очками!");
                        break;
                }
            }
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
        keyboardFirstRow.add("Начать");
        keyboardFirstRow.add("Закончить");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("Результаты");
        keyboardSecondRow.add("Помощь");

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

