package com.gyuwon.scheduleGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MonthSchedule extends JFrame {
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JButton jbtPrev = new JButton("<");
	private JButton jbtNext = new JButton(">");
	ListenToChange lToChange = new ListenToChange();
	private String s1;//달력 상단에 표시할 yyyy.mm
	private JLabel jlblMonth;
	private JLabel jlbl7 = new JLabel("SUN");
	private JLabel jlbl1 = new JLabel("MON");
	private JLabel jlbl2 = new JLabel("TUE");
	private JLabel jlbl3 = new JLabel("WED");
	private JLabel jlbl4 = new JLabel("THR");
	private JLabel jlbl5 = new JLabel("FRI");
	private JLabel jlbl6 = new JLabel("SAT");
	private int cnt;//달력 날짜 카운트
	private LocalDateTime ldt; private LocalDate ld;
	private JButton[] jbtday = new JButton[32];//각 날짜별 버튼
	ListenForDay lForDay = new ListenForDay();
	
	long period;//달 날짜 수
	
	public MonthSchedule() {
		super("Schedule Planner");
		LocalDate nowTime = LocalDate.now().withDayOfMonth(1);
		draw(nowTime.atTime(0, 0));
	}
	
	private void draw(LocalDateTime LDT) {
		this.setLayout(new BorderLayout());
		panel1.setLayout(new GridLayout(1, 3));
		panel2.setLayout(new GridLayout(1, 7));
		panel3.setLayout(new GridLayout(0, 7));
		panel4.setLayout(new BorderLayout());
	
		ldt = LDT;
		ld = LocalDate.from(ldt);
		
		jbtPrev.addActionListener(lToChange);
		jbtNext.addActionListener(lToChange);
		
		s1 = Integer.toString(ldt.getYear()) + "." + Integer.toString(ldt.getMonthValue());
		jlblMonth = new JLabel(s1);
		jlblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(jbtPrev);
		panel1.add(jlblMonth);
		panel1.add(jbtNext);

		jlbl7.setHorizontalAlignment(SwingConstants.CENTER);
		jlbl1.setHorizontalAlignment(SwingConstants.CENTER);
		jlbl2.setHorizontalAlignment(SwingConstants.CENTER);
		jlbl3.setHorizontalAlignment(SwingConstants.CENTER);
		jlbl4.setHorizontalAlignment(SwingConstants.CENTER);
		jlbl5.setHorizontalAlignment(SwingConstants.CENTER);
		jlbl6.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel2.add(jlbl7);
		panel2.add(jlbl1); panel2.add(jlbl2); panel2.add(jlbl3);
		panel2.add(jlbl4); panel2.add(jlbl5); panel2.add(jlbl6);

		
		DayOfWeek day = ldt.getDayOfWeek();
		int dow = day.getValue();
		cnt = 1;
		for(int i = 0; i < dow; i++) {
			panel3.add(new JLabel(""));
		}
		for (int i = dow; i < 7; i++) {
			jbtday[cnt] = new JButton("" + cnt);
			jbtday[cnt].addActionListener(lForDay);
			panel3.add(jbtday[cnt++]);
		}
		
		LocalDate start = ld;
		LocalDate end = ld.plusMonths(1);
		period = ChronoUnit.DAYS.between(start, end);

		while (cnt <= period) {
			jbtday[cnt] = new JButton("" + cnt);
			jbtday[cnt].addActionListener(lForDay);
			panel3.add(jbtday[cnt++]);
		}

		panel4.add(panel2, BorderLayout.NORTH);
		panel4.add(panel3, BorderLayout.CENTER);
		
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel4, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
	}
	
	private void deleteMonth() {
		jbtPrev.removeActionListener(lToChange);
		jbtNext.removeActionListener(lToChange);
		panel1.removeAll();
		//panel2.removeAll();
		panel3.removeAll();
		panel4.removeAll();
	}
	
	private class ListenToChange implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == jbtPrev) {
				System.out.println("< button clicked");
				deleteMonth();
				ldt = ldt.minusMonths(1);
				ld = LocalDate.from(ldt);
				System.out.println(ld.format(DateTimeFormatter.BASIC_ISO_DATE));
				draw(ldt);
				revalidate();
				repaint();
			}
			else if(e.getSource() == jbtNext) {
				System.out.println("> button clicked");
				deleteMonth();
				ldt = ldt.plusMonths(1);
				ld = LocalDate.from(ldt);
				System.out.println(ld.format(DateTimeFormatter.BASIC_ISO_DATE));
				draw(ldt);
				revalidate();
				repaint();
			}
		}
	}
	
	private class ListenForDay implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("잠시만 기다려봐");
			JButton jbtTemp = (JButton) e.getSource();
			LocalDate Dday = ld.plusDays(Integer.parseInt(jbtTemp.getText()) - 1);
			new DaySchedule(Dday);
		}
		
	}
	
}
