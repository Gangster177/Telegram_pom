package Telegram;

import Telegram.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.TimeUnit;

public class MyTelegramBot extends TelegramLongPollingCommandBot {
    public static final String BOT_TOKEN = "5423056571:AAFhxS-t77C9qjddIX_kAbfK46rWqCUBAro";
    public static final String BOT_USERNAME = "PomodoroNetBot";

    public static final int WORK = 2;
    public static final int BREAK = 1;

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(this);
            register(new StartCommand("start", "Старт"));
            register(new HelpCommand("help", "Помощь","help"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && isNumeric(update.getMessage().getText()) && StartCommand.isStart) {
            String message;
            StartCommand.isStart = false;
            int count = Integer.parseInt(update.getMessage().getText());

            for (int i = 1; i <= count; i++) {

                message = "Задача № " + i + " началась";
                sendMessage(message);
                try {
                    TimeUnit.MINUTES.sleep(WORK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                message = "Задача № " + i + " завершилась";
                sendMessage(message);
                if (count - i != 0) {
                    message = "Время отдыхать!";
                    sendMessage(message);
                    try {
                        TimeUnit.MINUTES.sleep(BREAK);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    message = "Время работать!";
                    sendMessage(message);
                }
            }
        } else {
            String message = "Я не понимаю";
            sendMessage(message);
        }

    }
    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId("1138423024");
       // message.setChatId("5417907054");
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
