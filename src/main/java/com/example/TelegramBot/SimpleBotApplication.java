package com.example.TelegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication
public class SimpleBotApplication {

	 public static void main(String[] args) {
	        ConfigurableApplicationContext ctx = SpringApplication.run(SimpleBotApplication.class, args);
	        try {
	            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
	            botsApi.registerBot(ctx.getBean("Hooberbot", AbilityBot.class));
	        } catch (TelegramApiException e) {
	            throw new RuntimeException(e);
	        }
	    }
}