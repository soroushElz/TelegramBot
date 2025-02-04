package com.example.TelegramBot;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class KeyboardFactory {

	public static ReplyKeyboard getConfirmationKeyBoard() {
		
		KeyboardRow row=new KeyboardRow();
		row.add("Confirm");
		row.add("modify");
		return new ReplyKeyboardMarkup(List.of(row));
	}

	public static ReplyKeyboard getPeriodSelectionKeyBoard() {
		
		KeyboardRow row=new KeyboardRow();
		row.add("1332-42");
		row.add("1343-52");
		row.add("1353-62");
		row.add("1363-72");
		return new ReplyKeyboardMarkup(List.of(row));
	}

}
