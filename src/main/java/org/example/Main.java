package org.example;

import Telegram.MyTelegramBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Main {
    public static void main(String[] args) throws TelegramApiException {
        new MyTelegramBot();
    }
}