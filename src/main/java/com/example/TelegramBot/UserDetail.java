package com.example.TelegramBot;

public class UserDetail {

	private String name;
	private String phoneNumber;
	private String userName;
	private String  period;
	private Long ChatId;
	
	public UserDetail() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}

	public Long getChatId() {
		return ChatId;
	}

	public void setChatId(Long chatId) {
		ChatId = chatId;
	}
	
	
}
