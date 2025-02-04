package com.example.TelegramBot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;
import static org.telegram.abilitybots.api.util.AbilityUtils.getUser;


public class Responsehandler {
	
	private final SilentSender sender;
    private final Map<Long, UserState> chatStates;
   
    
    private  UserInfoUtil userInfoutils;
   

	public Responsehandler(SilentSender Sender, DBContext db) {
		this.sender=Sender;
		this.chatStates=db.getMap(Constants.CHAT_STATES);
		this.userInfoutils=new UserInfoUtil();
	}


	public void replyToStart(Long chatId, String userName) {
		
		SendMessage message=new SendMessage();
		
		UserDetail userData=new UserDetail();
		userData.setChatId(chatId);
		userData.setUserName(userName);
		
		userInfoutils.getUserInfoMap().put(chatId, userData);
		message.setChatId(chatId);
		message.setText(Constants.START_TEXT);
		sender.execute(message);
		chatStates.put(chatId,UserState.AWAITING_NAME);
		
	}


	public void replyToButton(Long chatId, Message message) {
		if(message.getText().equalsIgnoreCase("/stop")) {
		   stopChat(chatId);
		}
		switch(chatStates.get(chatId)) {
		case AWAITING_NAME -> replyToName(chatId,message);
		case AWAITING_PHONENUMBER ->replyToPhoneNumber(chatId,message);
		case AWAITING_CONFIRMATION ->replytoConfirmation(chatId,message);
		case AWAITING_MARIAGE_PERIOD_SELECTION ->replyToMarriagePeriodSelection(chatId,message);
		}


	}
	
	private void replyToMarriagePeriodSelection(Long chatId, Message message) {
		
		userInfoutils.getUserInfoMap().get(chatId).setPeriod(message.getText());
		
		userInfoutils.WriteToCSV(chatId);
		stopChat(chatId);
		
	}


	private void replytoConfirmation(Long chatId, Message message) {
		
		if(message.getText().equalsIgnoreCase("modify")) {
			replyToName(chatId,message);
		}else if(message.getText().equalsIgnoreCase("Confirm")) {
			SendMessage sendmessage=new SendMessage();
			sendmessage.setChatId(chatId);
			sendmessage.setText("Please Enter your Marraige Date Period");
			sendmessage.setReplyMarkup(KeyboardFactory.getPeriodSelectionKeyBoard());
			sender.execute(sendmessage);
			
			chatStates.put(chatId,UserState.AWAITING_MARIAGE_PERIOD_SELECTION);
		}
	}


	private void replyToPhoneNumber(Long chatId, Message message) {
		
		userInfoutils.getUserInfoMap().get(chatId).setPhoneNumber(message.getText());
		SendMessage sendmessage=new SendMessage();
		sendmessage.setChatId(chatId);
		sendmessage.setText("Please Confirm or Modify your PhoneNumber");
		sendmessage.setReplyMarkup(KeyboardFactory.getConfirmationKeyBoard());
		sender.execute(sendmessage);
		
		chatStates.put(chatId,UserState.AWAITING_CONFIRMATION);
	}


	private void replyToName(Long chatId, Message message) {
		
		if(!message.getText().equalsIgnoreCase("modify")) {
		userInfoutils.getUserInfoMap().get(chatId).setName(message.getText());
		}
		
		SendMessage sendmessage=new SendMessage();
		sendmessage.setChatId(chatId);
		sendmessage.setText(String.format("hello %s Please Enter your PhoneNumber",message.getText()));
		sender.execute(sendmessage);
		chatStates.put(chatId,UserState.AWAITING_PHONENUMBER);
		
	}


	private void stopChat(long chatId) {
	    SendMessage sendMessage = new SendMessage();
	    sendMessage.setChatId(chatId);
	    sendMessage.setText("Thank you for your Request. See you soon!\nPress /start to start again");
	    chatStates.remove(chatId);
	    userInfoutils.getUserInfoMap().remove(chatId);
	    sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
	    sender.execute(sendMessage);
	}


	public boolean userIsActive(Long chatId) {
		return chatStates.containsKey(chatId);
	}



	
	

}
