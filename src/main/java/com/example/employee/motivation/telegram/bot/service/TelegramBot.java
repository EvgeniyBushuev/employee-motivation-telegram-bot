package com.example.employee.motivation.telegram.bot.service;

import com.example.employee.motivation.telegram.bot.configuration.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();



            switch (message) {
                case "/start" :
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default :
                    sendMessage(chatId, "Sorry. This command is not available");
            }

        }
    }

    private void startCommandReceived(long chatId, String userName) {

        String answer = "Hello, " + userName + "!";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String text) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);

        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }
    }
}
