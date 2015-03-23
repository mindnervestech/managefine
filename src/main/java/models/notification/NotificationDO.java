package models.notification;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

public class NotificationDO{
	
	
	public String d; // Valid value 0 to 6
	public String f = ""; // Valid value 0,1
	public Integer h = -1; // Valid value 0 to 23
	public Integer m = -1; // Valid value 0 to 59
	
	@JsonIgnore
	public DateTime dateTime; 
	
	public NotificationDO() {
	}
	
	public NotificationDO(Day day) {
		this.d = day.name();
	}
	public NotificationDO(Day day, String h, String m) {
		this.d = day.name();
		if(h.length() == 0 && m.length() == 0){
			this.f = "";
			this.h = -1;
			this.m = -1;
			this.dateTime = null;
		}else{
			this.h = Integer.valueOf(h);
			this.m = Integer.valueOf(m);
			this.dateTime = new DateTime().withHourOfDay(this.h).withMinuteOfHour(this.m).withDayOfWeek(day.day);
			this.f = "checked";
		}
	}
	
	public DateTime getDateTime(){
		Day day = Day.valueOf(this.d);
		return new DateTime().withHourOfDay(this.h).withMinuteOfHour(this.m).withDayOfWeek(day.day);
		
	}
	
	public static enum Day{
		Sunday(7),
		Monday(1),
		Tuesday(2),
		Wednesday(3),
		Thursday(4),
		Friday(5),
		Saturday(6);
		
		public int day;
		Day(int day){
			this.day =day;
		}
	}
	
	
}