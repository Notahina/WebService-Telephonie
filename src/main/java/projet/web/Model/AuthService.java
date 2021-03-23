package projet.web.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AuthService {
	public static String DateToString(Date date) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.format(date);
	}
	public AuthService() {
		
	}
	public Date AddHoursOfDate(Date date,int hour) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hour);
	    return calendar.getTime();
	}
}
