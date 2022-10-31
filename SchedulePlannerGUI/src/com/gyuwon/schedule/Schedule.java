package com.gyuwon.schedule;

import java.time.LocalDateTime;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;;

public class Schedule {
	private String[] info;// 정보를 String 배열로 저장
	private LocalDateTime start;// 시작 시간
	private LocalDateTime end;// 종료 시간
	private String str;// 처음 받은 String
	private String name;// 제목
	private String memo;// 메모
	private static String p = "(^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$)";
	private boolean flag;// Test에 넘길 flag : ScheduleList에 저장 여부 결정
	private boolean isComment = false;// comment인지 확인하는 변수
	private int error_code;// 에러 코드

	Schedule() {
		name = null;
		memo = null;
		start = LocalDateTime.now();
		end = LocalDateTime.now().plusHours(1);
		setInfo();
		flag = true;
	}

	Schedule(String str) {
		this.str = str;
		parse();
		if (!isComment) {
			if (isLegal())
				flag = true;
			else
				flag = false;
		}
	}

	private void parse() {
		str = str.trim();
		if (str.isEmpty()) {
			flag = false;
			isComment = true;
			return;
		} else if (str.charAt(0) == ';') {
			flag = false;
			isComment = true;
			return;
		}
		info = str.split("//");
		for (int i = 0; i < info.length; i++)
			info[i] = info[i].trim();
	}

	private boolean check_time(String time) {
		return Pattern.matches(p, time);
	}

	private boolean isLegal() {
		if (info.length > 2) {
			if ((!info[0].isEmpty()) && check_time(info[1]) && check_time(info[2])) {
				try {
					setSchedule();
				} catch (Exception e) {
					return false;
				}
				if (start.isBefore(end)) {
					return true;
				} else {
					error_code = 3;// Time Conflict error
					error_message(error_code);
					return false;
				}
			} else if (info[0].isEmpty()) {
				error_code = 1;// No schedule Title
				error_message(error_code);
				return false;
			} else if (!(check_time(info[1]) && check_time(info[2]))) {
				error_code = 2;// Date format error
				error_message(error_code);
				return false;
			}
		}
		return false;
	}

	private boolean hasMemo() {
		if (info.length == 4) {
			if (info[3] == null)
				return false;
			else return true;
		} else
			return false;
	}

	private void error_message(int code) {
		switch (code) {
		case 1:
			System.out.println("error code1 : No schedule Title ; Skip the input line : " + str);
			break;
		case 2:
			System.out.println("error code2 : Date format error ; Skip the input line : " + str);
			break;
		case 3:
			System.out.println("error code3 : Time Conflict error ; Skip the input line : " + str);
			break;
		default:
			System.out.println("invalid error code ; Skip the input line : " + str);
		}
	}

	public void print() {
		int i;
		if (flag) {
			for (i = 0; i < info.length - 1; i++) {
				if (info[i] == null)
					System.out.print("[blank]//");
				else
					System.out.print(info[i] + "//");
			}
			if (info[i] == null)
				System.out.println("[blank]");
			else
				System.out.println(info[i]);
		}
	}

	private void setSchedule() throws ParseException {
		name = info[0];
		try {
			start = LocalDateTime.parse(info[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			end = LocalDateTime.parse(info[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		} catch (Exception e) {
			flag = false;
			error_code = 2;
			error_message(error_code);
			// e.printStackTrace();
			throw e;
		}
		if (info.length == 4)
			memo = info[3];
	}

	private void setInfo() {
		info = new String[4];
		info[0] = name;
		info[1] = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		info[2] = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		if (memo != null)
			info[3] = memo;
		else
			info[3] = null;
	}

	public void setScheduleName(String name) {
		this.name = name;
		str = this.name + "//" + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "//"
				+ end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "//" + memo;
		setInfo();
	}

	public void setScheduleMemo(String memo) {
		this.memo = memo;
		str = name + "//" + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "//"
				+ end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "//" + this.memo;
		setInfo();
	}

	public void setScheduleTime(String time, int n) {
		LocalDateTime t_time;
		if (check_time(time)) {
			try {
				t_time = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				if (n == 0 && t_time.isBefore(end))// setStartTime
					start = t_time;
				else if (n == 1 && t_time.isAfter(start))// setEndTime
					end = t_time;
				else
					System.out.println("Time Conflict Error");
			} catch (Exception e1) {
				System.out.println("Time Format Error");
			} finally {
				str = name + "//" + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "//"
						+ end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "//" + memo;
				setInfo();
			}
		} else
			System.out.println("Time Format Error");
	}

	public boolean getFlag() {
		return flag;
	}

	public String getSchedule() {
		return str;
	}

	public String getName() {
		return info[0];
	}

	public LocalDateTime getStart() {
		return start;
	}

	public String getStartStr() {
		return info[1];
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public String getEndStr() {
		return info[2];
	}

	public Boolean getHasMemo() {
		return hasMemo();
	}

	public String getMemo() {
		if (hasMemo())
			return info[3];
		else
			return null;
	}
}
