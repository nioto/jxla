/**
 * 
 */
package org.novadeck.jxla.data;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author nioto
 *
 */
public class MyDate implements Serializable, Cloneable, Comparable<MyDate> {


	private static final long serialVersionUID = 5506153888213548691L;

	Calendar date;
	/**
	 * 
	 */
	public MyDate() {
		this.date = Calendar.getInstance();
	}
	public MyDate(long millis) {
		this.date = Calendar.getInstance();
		this.date.setTimeInMillis(millis);
	}

	public MyDate(Calendar c) {
		this.date = Calendar.getInstance();
		this.date.setTimeInMillis( c.getTimeInMillis() );
	}

	public MyDate(MyDate m) {
		this.date = Calendar.getInstance();
		this.date.setTimeInMillis( m.date.getTimeInMillis() );
	}

	public MyDate(int year, int month, int day, int hours, int minutes, int seconds) {
		this.date = Calendar.getInstance();
		this.date.set(year + 1900, month, day, hours, minutes, seconds);
	}
	
	public int getDate(){
		return this.date.get(Calendar.DAY_OF_MONTH);
	}
	public int getMonth(){
		return this.date.get(Calendar.MONTH);
	}
	public long getTime(){
		return this.date.getTimeInMillis();
	}
	/**
	 * return the real year, no more adding 1900
	 * @return
	 */
	public int getYear(){
		return this.date.get(Calendar.YEAR);
	}
	
	public MyDate setSeconds(int seconds) {
		this.date.set(Calendar.SECOND, seconds);
		return this;
	}
	public MyDate setMinutes(int minutes) {
		this.date.set(Calendar.MINUTE, minutes);
		return this;
	}
	public MyDate setHours(int hours) {
		this.date.set(Calendar.HOUR, hours);
		return this;
	}
	public MyDate setDate(int date) {
		this.date.set(Calendar.DATE, date);
		return this;
	}
	public MyDate setMonth(int month) {
		this.date.set(Calendar.MONTH, month);
		return this;
	}
	public MyDate setYear(int year) {
		if( year < 1900) {
			year += 1900;
		}
		this.date.set(Calendar.YEAR, year);
		return this;
	}

	public boolean before(MyDate m ) {
		return this.date.before( m.date );
	}
	public boolean after(MyDate m ) {
		return this.date.after( m.date );
	}
	
	@Override
	public int compareTo(MyDate o) {
		return this.date.compareTo(o.date);
	}

}
