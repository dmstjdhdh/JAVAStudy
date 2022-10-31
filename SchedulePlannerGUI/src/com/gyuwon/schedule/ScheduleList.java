package com.gyuwon.schedule;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduleList {

	//private Schedule[] list = new Schedule[100];
	private ArrayList<Schedule> list = new ArrayList<Schedule>();
	private Schedule temp;
	private String file_name;
	private String backup;
	//private int index = 0;

	public ScheduleList(String name) {
		try {
			// 파일 객체 생성
			File file = new File(name);
			file_name = name;
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				if (line.charAt(0) != ';' || !line.isEmpty()) {
					temp = new Schedule(line);
					if (temp.getFlag()) {
						list.add(temp);
					}
				}
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unknown file");
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public Schedule getSchedule(int i) {
		return list.get(i);
	}

	public int numSchedules() {
		return list.size();
	}

	public void bakcupFile() {
		backup = new String(file_name);
		backup = backup.replace(".data", "_backup.data");
		System.out.println(backup);
		try {

			FileInputStream fis = new FileInputStream(file_name); // 읽을파일
			FileOutputStream fos = new FileOutputStream(backup); // 복사할파일

			int fileByte = 0;
			// fis.read()가 -1 이면 파일을 다 읽은것
			while ((fileByte = fis.read()) != -1) {
				fos.write(fileByte);
			}
			// 자원사용종료
			fis.close();
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveFile() {
		String line;
		try {
			File file = new File(file_name);
			FileWriter filewriter = new FileWriter(file);
			BufferedWriter bufWriter = new BufferedWriter(filewriter);
			for (int i = 0; i < list.size(); i++) {
				line = list.get(i).getSchedule();
				bufWriter.write(line);
				bufWriter.flush();
				bufWriter.write("\r\n");
				bufWriter.flush();
			}
			bufWriter.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unknown file");
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	public void appendSchedule(String str) {
		temp = new Schedule(str);
		if (temp.getFlag()) {
			list.add(temp);
		}
	}

	public void appendSchedule(Schedule s) {
		temp = s;
		if (temp.getFlag()) {
			list.add(temp);
		}
	}
	
	public void deleteSchedule(int i) {
		list.remove(i);
	}
	
	public ArrayList<Integer> findSchedule(LocalDate LD) {
		Schedule temp;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			if(!(LD.isBefore(LocalDate.from(temp.getStart())) || (LD.isAfter(LocalDate.from(temp.getEnd()))))) {
				arr.add(i);
			}
		}
		return arr;
	}
}
