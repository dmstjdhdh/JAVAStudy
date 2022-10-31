package com.gyuwon.scheduleGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.gyuwon.schedule.*;

public class DaySchedule extends JFrame {
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JLabel jlblTitle = new JLabel("Title");
	private JLabel jlblStart = new JLabel("Start Time");
	private JLabel jlblEnd = new JLabel("End Time");
	private JLabel jlblMemo = new JLabel("Memo");
	private ArrayList<JTextField> jtfTitle = new ArrayList<JTextField>();
	private ArrayList<JTextField> jtfStart = new ArrayList<JTextField>();
	private ArrayList<JTextField> jtfEnd = new ArrayList<JTextField>();
	private ArrayList<JTextField> jtfMemo = new ArrayList<JTextField>();
	private ScheduleList list;
	private ArrayList<Integer> arr;
	private Schedule temp_s;
	private String day;
	JButton jbtCancel = new JButton("Cancel");
	JButton jbtAdd = new JButton("Add");
	JButton jbtSave = new JButton("Save");
	private ListenForEdit lForEdit = new ListenForEdit();
	private String tempStr;//schedulelist에 다시 넣을 때 schedule(tempStr)로 넣을 것임
	
	public DaySchedule(LocalDate LD) {
		super();
		day = LD.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		super.setTitle(day + " Schedule");
		this.setLayout(new BorderLayout());
		
		panel1.setLayout(new GridLayout(1, 4));
		panel2.setLayout(new GridLayout(0, 4));
		panel3.setLayout(new GridLayout(1, 3));

		jlblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jlblStart.setHorizontalAlignment(SwingConstants.CENTER);
		jlblEnd.setHorizontalAlignment(SwingConstants.CENTER);
		jlblMemo.setHorizontalAlignment(SwingConstants.CENTER);

		panel1.add(jlblTitle);
		panel1.add(jlblStart);
		panel1.add(jlblEnd); 
		panel1.add(jlblMemo);
		
		list = TestGUI.getScheduleList();
		arr = list.findSchedule(LD);
		if(arr.size() > 0) {
			for (int i = 0; i < arr.size(); i++) {
				temp_s = list.getSchedule(arr.get(i));
				jtfTitle.add(new JTextField(temp_s.getName()));
				jtfStart.add(new JTextField(temp_s.getStartStr()));
				jtfEnd.add(new JTextField(temp_s.getEndStr()));
				if(temp_s.getHasMemo()) jtfMemo.add(new JTextField(temp_s.getMemo()));
				else jtfMemo.add(new JTextField());
			}
		}
		else {
			addTextField();
		}
 
		for (int i = 0; i < jtfTitle.size(); i++) {
			addTF2Pannel(i);
		}

		
		jbtCancel.addActionListener(lForEdit);
		jbtAdd.addActionListener(lForEdit);
		jbtSave.addActionListener(lForEdit);
		
		panel3.add(jbtCancel);
		panel3.add(jbtAdd);
		panel3.add(jbtSave);

		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(panel3, BorderLayout.SOUTH);
		this.setLocation(0, 220);
		this.pack();
		this.setVisible(true);
		//this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	private void addTextField() {
		jtfTitle.add(new JTextField());
		jtfStart.add(new JTextField());
		jtfEnd.add(new JTextField());
		jtfMemo.add(new JTextField());
	}
	
	private void addTF2Pannel(int i) {
		panel2.add(jtfTitle.get(i));
		panel2.add(jtfStart.get(i));
		panel2.add(jtfEnd.get(i));
		panel2.add(jtfMemo.get(i));
	}
	
	private class ListenForEdit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == jbtCancel) {
				dispose();
			}
			else if(e.getSource() == jbtAdd) {
				addTextField();
				addTF2Pannel(jtfTitle.size()-1);
				revalidate();
				repaint();
			}
			else if(e.getSource() == jbtSave) {
				for (int i = arr.size() - 1; i >= 0; i--) {
					list.deleteSchedule(arr.get(i));
				}
				for (int i = 0; i < jtfTitle.size(); i++) {
					tempStr = jtfTitle.get(i).getText() + "//" + jtfStart.get(i).getText() + "//" + jtfEnd.get(i).getText();
					if(!jtfMemo.get(i).getText().isEmpty()) tempStr = tempStr + "//" + jtfMemo.get(i).getText();
					list.appendSchedule(tempStr);
				}
				list.bakcupFile();
				list.saveFile();
			}
		}
	}
	
}
