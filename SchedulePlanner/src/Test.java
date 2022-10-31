//import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Test {
	public static void main(String[] args) {
		//Scanner stdIn = new Scanner(System.in);
		//System.out.println("ÀÔ·ÂÇØ : ");
		//String data = stdIn.nextLine();
		
		Schedule s1 = new Schedule();
		 
		s1.setScheduleName("setName");
		//System.out.println(s1.getSchedule());
		s1.print();
		s1.setScheduleMemo("setMemo");
		s1.print();
		s1.setScheduleTime("2021-02-12 12:05", 0);
		s1.print();
		s1.setScheduleTime("2020-06-12 12:05", 1);
		s1.print();
		
		ScheduleList slist = new ScheduleList("D:\\javaprojects\\SchedulePlanner\\src\\schedule.data");
		
		slist.appendSchedule(s1);
		
		System.out.println("list.length : " + slist.numSchedules());
		//slist.getSchedule(2).print();
		
		for (int i=0;i<slist.numSchedules();i++) {
			slist.getSchedule(i).print();
		}
		
		
		//slist.bakcupFile();
		//slist.saveFile();
		 
		//LocalDate jan = LocalDate.of(2020, 2, 1);
		//LocalDate feb = LocalDate.of(2020, 3, 1);
		//System.out.println(ChronoUnit.DAYS.between(jan, feb));
		
	}

}
