package main;


        import java.text.SimpleDateFormat;
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

    public static int countWord = 0;
    public static boolean isStart = false;
    public static boolean isRemember = false;
    public static HashMap<String, Integer> results = new HashMap<String, Integer>();

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        //    boolean isStart = false;
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    onMainMenu(message, "Добро пожаловать!\n"
                            + "Комманды: \n"
                            + "Результаты" + "\n"
                            + "\n" //
                            + "Для удобства, используйте клавиатуру.\n");
                    break;
                case "Начать":
                    onMainMenu(message, "Давайте начнем!");
                    runTest(message,"espano", "Начнем с простого");
                    isStart();
                    break;
                case "Ваши результаты":
                    onMainMenu(message, getResult());
                    break;
                case "Помню":
                    if (isStart){
                        onTestMenu(message, "Очень хорошо!");
                        countWord();
                        isRemember();
                        runTest(message,"espano", "А как насчет этого?");
                    }
                    else {
                        wrongCommand(message);
                    }
                    break;
                case "Не помню":
                    if (isStart) {
                        onTestMenu(message, "Плохо :(\n");
                        runTest(message, "english", "Это было слово");
                        runTest(message,"espano","Поехали дальше!");
                        isRemember();
                    }
                    else{
                        wrongCommand(message);
                    }
                    break;
                case "Закончить":
                    if (isRemember){
                        onMainMenu(message, "Ну что ж, пора закругляться. Вы помните " + countWord + " слов(а,о)");
                        long curTime = System.currentTimeMillis();
                        Date curDate = new Date(curTime);
                        String curStringDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(curTime);
                        setResult(curStringDate, countWord);
                        isStart = false;
                        isRemember = false;
                        countWord = 0;
                    }
                    else{
                        onMainMenu(message, "Но мы ведь даже не начали! А вы уже закончить, закончить... :(");
                    }
                    break;
                default:
                    onMainMenu(message, "Да-да?");
                    break;
            }
        }
    }

    //занесение результатов
    public void setResult(String date, Integer count){
        results.put(date,count);
    }

    //получение результатов
    private static String getResult(){
        // Получаем набор элементов
        Set<Map.Entry<String, Integer>> set = results.entrySet();
        String s = "Ваши результаты:";
        for (Map.Entry<String, Integer> me : set) {
            s = s + "\n" + me.getKey() + ": " + me.getValue() + " слов(а,о)";
        }
       if (s.equals("Ваши результаты:")){s = "Их нет";}
        return s;
    }

    //получение рандомного слова и отправка сообщения
    private void runTest(Message message, String smthword, String text){
        words word = new words();
        word.randomWord();
        onTestMenu(message, text + " " + word.getWord(smthword));
    }

    private void wrongCommand(Message message){
        onMainMenu(message, "Сбой матрицы");
    }

    //подсчет верных слов
    private static Integer countWord(){
        countWord++;
        return countWord;
    }

    //установка старта
    private static Boolean isStart(){
        isStart = true;
        return isStart;
    }
    private static Boolean isRemember(){
        isRemember = true;
        return isRemember;
    }


    //вовращает рандомное испанское слово
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

    //клавиатуры
    private void onMainMenu(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиатуру
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

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры

        keyboardSecondRow.add("Ваши результаты");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
       // sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void onTestMenu(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиатуру
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
        keyboardFirstRow.add("Помню");
        keyboardFirstRow.add("Не помню");
        keyboardFirstRow.add("Закончить");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
      //  sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
