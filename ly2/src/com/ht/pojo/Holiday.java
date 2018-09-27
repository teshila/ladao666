package com.ht.pojo;

import java.util.Date;

public class Holiday {
	private Date holiday;
	private String holidayName;
	private Date addDate;

	public Date getAddDate() {
		return addDate;
	}

	public Date getHoliday() {
		return holiday;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public void setHoliday(Date holiday) {
		this.holiday = holiday;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

}
