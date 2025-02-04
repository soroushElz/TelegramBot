package com.example.TelegramBot;
import java.util.*;
import java.io.*;
import com.opencsv.*;
import org.springframework.stereotype.Component;



public class UserInfoUtil {
	
	private  Map<Long,UserDetail> UserInfoMap;
	private CSVWriter writer;
	
	public UserInfoUtil() {
		this.UserInfoMap=new HashMap<>();
		
		String filepath="./Records.CSV";
		File file = new File(filepath); 
		int version=0;
		while (file.exists()) {
			file=new File("./Records".concat(String.valueOf(version++)).concat(".CSV"));
		}
		    try { 
		       
		        FileWriter outputfile = new FileWriter(file); 
		  
		      
		         this.writer = new CSVWriter(outputfile); 
		  
		        // adding header to csv 
		        String[] header = { "Id", "UserName", "fullName" ,"PhoneNumber","Period"}; 
		        writer.writeNext(header);
		        writer.flush();
	 } catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	}

	public void WriteToCSV(Long chatId) {
		 
		 String[] UserData= {chatId.toString(),
			 	              UserInfoMap.get(chatId).getUserName(),
				              UserInfoMap.get(chatId).getName(),
				              UserInfoMap.get(chatId).getPhoneNumber(),
				              UserInfoMap.get(chatId).getPeriod()};
		  writer.writeNext(UserData);
		  try {
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		       
	}


	public Map<Long,UserDetail> getUserInfoMap() {
		return UserInfoMap;
	}


	
}
