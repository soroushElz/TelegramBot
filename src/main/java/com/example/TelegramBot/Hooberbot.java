package com.example.TelegramBot;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

import java.util.function.BiConsumer;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component(value="Hooberbot")
public class Hooberbot extends AbilityBot {
	
	private final Responsehandler responseHandler; 

	
	public Hooberbot(Environment env) {
		super("6682385418:AAEAkOUz7WORI59N2w2S51diLc9rU-RZH0M", "Hoober_bot");
		responseHandler=new Responsehandler(silent,db);
		
	}
	
	public Ability startBot() {
		return Ability
				.builder()
				.name("start")
				.info(Constants.START_DESCRIPTION)
				.locality(USER)
				.privacy(PUBLIC)
				.action(ctx -> responseHandler.replyToStart(ctx.chatId(),ctx.user().getUserName()))
				.build();
	}
	
	public Reply replyToButton() {
		BiConsumer<BaseAbilityBot, Update> action=(abilityBot, upd) ->responseHandler.replyToButton(getChatId(upd),upd.getMessage());
		return Reply.of(action, Flag.TEXT,upd->responseHandler.userIsActive(getChatId(upd)));
	
	}

	@Override
	public long creatorId() {
		
		return 12554L;
	}

}
