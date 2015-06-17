package com.sbs.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SpecialCalendar {
	private int daysOfMonth = 0; // ĳ�µ�����
	private int dayOfWeek = 0; // ����ĳһ�������ڼ�

	// �ж��Ƿ�Ϊ����
	public boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 == 0) {
			return true;
		} else if (year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	// �õ�ĳ���ж�������
	public int getDaysOfMonth(boolean isLeapyear, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			daysOfMonth = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			daysOfMonth = 30;
			break;
		case 2:
			if (isLeapyear) {
				daysOfMonth = 29;
			} else {
				daysOfMonth = 28;
			}

		}
		return daysOfMonth;
	}

	// ָ��ĳ���е�ĳ�µĵ�һ�������ڼ�
	public int getWeekdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}
	
	//�����ϸ������һ��
	public int getlastday(int year, int month)
	{
		boolean isleap = false;
		int day = 0;
		
		if(month == 1)
		{
			return getDaysOfMonth(isLeapYear(year - 1), 12);
		}else
		{
			return getDaysOfMonth(isLeapYear(year), month - 1);
		}
	}
	
	//����ĳ�����һ���ַ���
	public  String getlastdaystr()
	{
		String time = "";
		Date now = new Date();
		Date temp;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		//����ĩ���һ��
		int day = getlastday(now.getYear()+1900, now.getMonth()+1);
		int year = now.getYear();
		if(now.getMonth() == 0)
		{
			temp = new Date(year-1, 12-1, day);
			
		}else
		{
			int month = now.getMonth() -1 ;
			temp = new Date(year, month, day);
		}
		time = df.format(temp).substring(0, 10);
		return time;
	}
	
	//����ĳ��ĳ��ĳ�ն�Ӧ�����ڼ�
	public static String getWeekdayofYear(int year, int month, int day){
		String week = "";
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
		switch(weekday)
		{
		case 0:
			week = "����";break;
		case 1:
			week = "��һ";break;
		case 2:
			week = "�ܶ�";break;
		case 3:
			week = "����";break;
		case 4:
			week = "����";break;
		case 5:
			week = "����";break;	
		case 6:
			week = "����";break;
		}
		return week;
	}
}
