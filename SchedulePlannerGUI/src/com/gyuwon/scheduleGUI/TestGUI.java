package com.gyuwon.scheduleGUI;

import java.time.LocalDate;
import com.gyuwon.schedule.ScheduleList;

public class TestGUI {
	private static ScheduleList slist;
	public static void main(String[] args) {
		slist = new ScheduleList("D:\\javaprojects\\SchedulePlannerGUI\\src\\com\\gyuwon\\schedule\\schedule.data");
		new MonthSchedule();
		
		new DaySchedule(LocalDate.of(2020, 6, 20));
	}
	
	public static ScheduleList getScheduleList() {
		return slist;
	}
}
