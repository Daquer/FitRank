package br.com.fitrank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateConversor {
	
	private static final String FORMATO_DATA = "dd/MM/yyyy";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(FORMATO_DATA);
	private static final String FORMATTER_DATA_HORA = "dd/MM/yyyy HH:mm:ss";
	private static final SimpleDateFormat FORMATTER_DATE_HOUR = new SimpleDateFormat(FORMATTER_DATA_HORA);
	private static GregorianCalendar gc;
	
	public static String DateToString(Date data) {
		try {
			return FORMATTER.format(data);
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public static String DateHourToString(Date data) {
		try {
			return FORMATTER_DATE_HOUR.format(data);
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public static Date StringToDate(String data){
		try {
			return FORMATTER.parse(data);
		} catch (ParseException e ) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
			
	public static Date getPreviousYear(){
		gc = new GregorianCalendar();
		gc.add(Calendar.YEAR, -1);
		return gc.getTime();
	}
	
	public static Date getPreviousMonth(){
		gc = new GregorianCalendar();
		gc.add(Calendar.MONTH, -1);
		return gc.getTime();
	}
	
	public static Date getPreviousWeek(){
		gc = new GregorianCalendar();
		gc.add(Calendar.WEEK_OF_YEAR, -1);
		return gc.getTime();
	}
	
	public static Date getPreviousDay(){
		gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_YEAR, -1);
		return gc.getTime();
	}
	
	public static String getPreviousYearString(){
		gc = new GregorianCalendar();
		gc.add(Calendar.YEAR, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static String getPreviousYearStringFromStringDate(String date){
		gc = convertDateToGregorian(StringToDate(date));
		gc.add(Calendar.YEAR, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static String getPreviousMonthString(){
		gc = new GregorianCalendar();
		gc.add(Calendar.MONTH, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static String getPreviousMonthStringFromStringDate(String date){
		gc = convertDateToGregorian(StringToDate(date));
		gc.add(Calendar.MONTH, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static String getPreviousWeekString(){
		gc = new GregorianCalendar();
		gc.add(Calendar.WEEK_OF_YEAR, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static String getPreviousWeekStringFromStringDate(String date){
		gc = convertDateToGregorian(StringToDate(date));
		gc.add(Calendar.WEEK_OF_YEAR, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static String getPreviousDayString(){
		gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_YEAR, -1);
		return FORMATTER.format(gc.getTime());
	}
	
	public static GregorianCalendar convertDateToGregorian(Date date) {
		gc = new GregorianCalendar();
		gc.setTime(date);
		return gc;
	}
	
	public static int getHourFromDate(Date date) {
		gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getDayFromDate(Date date) {
		gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getDaysDifference(Date date1, Date date2) {
		
		
		long daysDifference =  date1.getTime()/1000 - date2.getTime()/1000;
		 
		return (int) TimeUnit.DAYS.convert(daysDifference, TimeUnit.SECONDS);
	}
	
}