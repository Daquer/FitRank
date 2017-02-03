package br.com.fitrank.util;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class PostFitnessUtil {
	
	public static double getNikeDistance(String courseTitle) throws NumberFormatException {
		
		String miles = courseTitle.split("miles")[0].trim();
		
		if ( miles.charAt(0) == '.' ) {
			miles = "0".concat(miles);
		}
		
		double kms = Double.parseDouble(miles) * ConstantesFitRank.MILHA_EM_KM;
		
		return kms;
	}
	
	public static double getDuration(Date startTime, Date endTime) {
		
		GregorianCalendar gcStartTime = DateConversor.convertDateToGregorian(startTime);
		
		GregorianCalendar gcEndTime = DateConversor.convertDateToGregorian(endTime);
		
		long diffMillis = gcEndTime.getTimeInMillis() - gcStartTime.getTimeInMillis();
		
		return (double) diffMillis/3600000;
		
	}
	
	public static double getEndomondoDistance(String courseTitle) throws NumberFormatException {
		
		String km = courseTitle.split("km")[0].trim();
		
		if ( km.charAt(0) == '.' ) {
			km = "0".concat(km);
		}
		
		double kms = Double.parseDouble(km);
		
		return kms;
	}
	
	public static double getRuntasticDistance(String courseTitle)  throws NumberFormatException {
	
		courseTitle = courseTitle.replace("a ", "");
		
		String kms = courseTitle.split("km")[0].trim();
		
		return Double.parseDouble(kms);
	}
	
	public static double getRuntasticDuration(String courseTitle)  throws NumberFormatException {
		
		courseTitle = courseTitle.split(" in")[1].trim();
		
		String strHour = courseTitle.split("h")[0].trim();
		
		String strMinutes = courseTitle.split("h")[1].split("m")[0].trim();
		
		String strSeconds = courseTitle.split("m")[1].split("s")[0].trim();
		
		double doubleHour = Double.parseDouble(strHour);
		
		double doubleMinutesInHour = Double.parseDouble(strMinutes) / 60;
		
		double doubleSecondsInHour = Double.parseDouble(strSeconds) / 3600;
		
		return doubleHour + doubleMinutesInHour + doubleSecondsInHour;
		
	}
	
	public static double getRunKeeperDistance(String courseTitle) throws NumberFormatException {
//		double kms = 0.0;
		String kms = "";

//		if (courseTitle.contains("km")) {
			kms = courseTitle.split("km")[0].trim();
//			kms = Double.parseDouble(kmStr);
//		}
		
//		if (courseTitle.contains("mi.")) {
//			String mi = courseTitle.split("mi")[0].trim();
//			kms = Double.parseDouble(mi) * ConstantesFitRank.MILHA_EM_KM;
//		}
		
		return Double.parseDouble(kms);
	}
	
	public static double getRunKeeperDuration(String courseTitle) throws NumberFormatException {
		
		int countColons = StringUtils.countMatches(courseTitle, ":");
		
		
		courseTitle = courseTitle.split("in")[1].trim();
		
		String strHour = "";
		String strMinutes = "";
		String strSeconds = "";
		
		if (countColons == 1) {
			
			strMinutes = courseTitle.split(":")[0].trim();
			
			strSeconds = courseTitle.split(":")[1].trim();
			
		} else if (countColons == 2) {
			strHour = courseTitle.split(":")[0].trim();
			
			strMinutes = courseTitle.split(":")[1].trim();
			
			strSeconds = courseTitle.split(":")[2].trim();
		}
		
		
		double doubleHour = 0.0;
		double doubleMinutesInHour = 0.0;
		double doubleSecondsInHour = 0.0;
		
		if (strHour.equals("")){
			doubleMinutesInHour = Double.parseDouble(strMinutes) / 60;
			
			doubleSecondsInHour = Double.parseDouble(strSeconds) / 3600;
		} else {
			doubleHour = Double.parseDouble(strHour);
		}
		
		return doubleHour + doubleMinutesInHour + doubleSecondsInHour;
		
	}
	//Strava e MapMyRun
	public static double getStravaCourseDistance(String distanceInKm) throws NumberFormatException {
		
		return Double.parseDouble(distanceInKm);
	}
	
	public static double getStravaCourseDuration(String durationInSeconds)  throws NumberFormatException {
		
		return Double.parseDouble(durationInSeconds) / 3600;
	}
}
