package com.ht.dao;

import com.ht.pojo.Holiday;

public interface HolidayDao {

	public Holiday getIsHoliday();

	public void saveHoliday(Holiday h);

	public void delete();
}
