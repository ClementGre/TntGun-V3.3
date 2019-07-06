package fr.themsou.methodes;

public class math {
	
	public String getTimestampWithMinutes(int i, String séparator){
		
		int Hour = i / 60;
		int Minutes = i % 60;
		
		if(Minutes <= 9) return Hour + séparator + "0" + Minutes;
		
		else return Hour + séparator + Minutes;
		
		
	}

}
