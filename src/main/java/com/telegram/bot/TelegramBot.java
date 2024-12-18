package com.telegram.bot;

import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {

    private static FluentLogger LOG = FluentLogger.getLogger(TelegramBot.class.getName());

    private static final String BOT_TOKEN = System.getenv("token");
    private static final String BOT_USERNAME = System.getenv("bot_name");

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String receivedText = update.getMessage().getText();
            long userId = update.getMessage().getFrom().getId();
            String userName = update.getMessage().getFrom().getFirstName();

            LOG.log("info", Map.of(
                    "chatId", chatId,
                    "userId", userId,
                    "userName", userName,
                    "userMessage", receivedText));

            String responseText = "You said: " + receivedText;

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(responseText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                LOG.log("error", Map.of("message", e.getMessage(), "type", e.getClass().getName()));
            }
        }
    }
}