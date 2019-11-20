package fr.themsou.methodes;

import java.util.Date;

public class realDate {
	
	public Date date;
	
	@SuppressWarnings("deprecation")
	public realDate(){
		
		date = new Date();
		
		int minutes = date.getMinutes(); // + 0;
		int hours = date.getHours(); // + 1;
		int day = date.getDate();
		int mounth = date.getMonth();

		date.setMinutes(minutes);
		date.setHours(hours);
		date.setDate(day);
		date.setMonth(mounth);
		
	}
	
	public Date getRealDate(){
		
		return date;
		
	}
	
	@SuppressWarnings("deprecation")
	public int getMinutesSpace(String date){
		
		date = date.split("-")[1];
		
		Date realDate = getRealDate();
		int totalMinutes = 0;
		
		int minutes = realDate.getMinutes() - Integer.parseInt(date.split(" ")[1].split(":")[1]);
		int hours = realDate.getHours() - Integer.parseInt(date.split(" ")[1].split(":")[0]);
		int day = realDate.getDate() - Integer.parseInt(date.split(" ")[0].split("/")[0]);
		int mounth = realDate.getMonth() - Integer.parseInt(date.split(" ")[0].split("/")[1]);
		
		
		if(mounth < 0){
			mounth = mounth + 12;
		}
		
		totalMinutes += minutes;
		totalMinutes += hours * 60;
		totalMinutes += day * 24 * 60;
		totalMinutes += mounth * 31 * 24 * 60;
		
		return totalMinutes;
		
	}
	
	

}
