package com.telegram.bot;

import org.fluentd.logger.FluentLogger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class Main {

    private static FluentLogger LOG = FluentLogger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot());
            LOG.log("info", Map.of("message", "Bot is started"));
        } catch (TelegramApiException e) {
            LOG.log("error", Map.of("message", e.getMessage(), "type", e.getClass().getName()));
        }
    }
}
