package com.sbs.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sbs.sbsattend.R;
import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Work;
import com.sbs.sbsattend.model.WorkHistory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	private boolean isLeapyear = false; // �Ƿ�Ϊ����
	private int daysOfMonth = 0; // ĳ�µ�����
	private int dayOfWeek = 0; // ����ĳһ�������ڼ�
	private Context context;
	private String[] dayNumber = new String[42]; // һ��gridview�е����ڴ����������
	private SpecialCalendar sc = null;
	private Resources res = null;
	private Drawable drawable = null;

	private int OpCode = 0;
	private int currentFlag = -1; // ���ڱ�ǵ���
	private String showYear = ""; // ������ͷ����ʾ�����
	private String showMonth = ""; // ������ͷ����ʾ���·�

	// ϵͳ��ǰʱ��
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private int current_day = 0;

	private List<WorkHistory> wh = null; // ֵ���¼
	private List<Work> wk = null; // ���ݼ�¼
	private List<Leave> les; // ��ټ�¼

	public CalendarAdapter() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		Date date = new Date();
		sysDate = sdf.format(date); // ��������
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];
	}

	public CalendarAdapter(Context context, Resources rs, int year, int month,
			int OpCode) {
		this();
		this.context = context;
		this.OpCode = OpCode;
		this.res = rs;
		sc = new SpecialCalendar();

		// ��ȡÿ�����ݼ�
		switch (OpCode) {
		case MySignal.GETSHIFT:
			wh = Logic.query_monthlyworkinfo();
			break;
		case MySignal.GETREST:
			wh = Logic.query_monthlyworkinfo();
			wk = Logic.query_overtimeall();			
			break;
		case MySignal.GETLEAVE:
			les = Logic.query_leavetimeall();
			break;
		}

		// ��ȡ������Ϣ
		getCalendar(year, month);
	}

	@Override
	public int getCount() {
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SpannableString sp = null;
		String dayshift = "";
		String nightshift = "";
		String temp = "";
		int ifday = 0;
		int ifnight = 0;
		int flag = 0;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.calendar_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
		String d = dayNumber[position];

		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			if (OpCode == MySignal.GETSHIFT) {
				// ��ǰ����Ϣ��ʾ
				dayshift = "��:" + wh.get(position - dayOfWeek).getDaywork();
				nightshift = "��:" + wh.get(position - dayOfWeek).getNightwork();
			} else if (OpCode == MySignal.GETREST) {
				nightshift = "����:";
				dayshift = "����:";
				if (wk != null) {
					// ���뵽�ĵ���
					for (Work w : wk) {
						if (Integer.parseInt(w.getCurrentrest()
								.substring(8, 10)) == (position - dayOfWeek + 1)) {
							if (w.getCurrentshift().equals("����")) {
								dayshift += w.getName() + ";";
							} else if (w.getCurrentshift().equals("����")) {
								nightshift += w.getName() + ";";
							}
						}
					}
					// ��������
					for (Work w : wk) {
						int mdate = Integer.parseInt(w.getOriginrest().substring(8, 10));
						if (mdate == (position - dayOfWeek + 1)) {
							if (w.getOriginshift().equals("��������")) {
								ifday = 1; // ��ǰ���粻������Ϣ
							}
						}
						if (mdate == (position - dayOfWeek) || (0 == (position - dayOfWeek) && mdate >=28)) {	//�����������Ա��µ�һ��
							if (w.getOriginshift().equals("��������")) {
								ifnight = 2; // �������粻������Ϣ
							}
						}
					}
				}

				if (ifday != 1) {
					nightshift += wh.get(position - dayOfWeek + 1).getDaywork()				//����ĩ���һ�������wh���У�����߼�1��ֹ�������ƫ��
							+ ";";
				}
				if (ifnight != 2) {
					if (position - dayOfWeek - 1 >= 0) {
						dayshift += wh.get(position - dayOfWeek)                            //����ĩ���һ�������wh���У������û�м�1��ֹ�������ƫ��
								.getNightwork() + ";";
					}
					if(position - dayOfWeek == 0)	//�³���һ������
					{
						dayshift += wh.get(position - dayOfWeek)
								.getNightwork() + ";";
					}
				}
			} else if (OpCode == MySignal.GETLEAVE) {
				dayshift = "����:";
				nightshift = "����:";

				if (les != null) {
					for (Leave l : les) {
						current_day = position - dayOfWeek + 1;
						flag = insideRange(l);
						if (flag == MySignal.INRANGE) {
							System.out.println("MySignal.INRANGE");
							dayshift += l.getName() + ";";
							nightshift += l.getName() + ";";
						} else if (flag == MySignal.ATSTART) {
							System.out.println("MySignal.ATSTART");
							if (l.getOriginshift().equals("����")) {
								dayshift += l.getName() + ";";
								nightshift += l.getName() + ";";
							} else {
								nightshift += l.getName() + ";";
							}
						} else if (flag == MySignal.ATEND) {
							System.out.println("MySignal.ATEND");
							if (l.getCurrentshift().equals("����")) {
								dayshift += l.getName() + ";";
								nightshift += l.getName() + ";";
							} else {
								dayshift += l.getName() + ";";
							}
						}else if(flag == MySignal.EXACTDAY)
						{
							System.out.println("MySignal.EXACTDAY");
							if (l.getOriginshift().equals("����")) {
								dayshift += l.getName() + ";";
								if(l.getCurrentshift().equals("����"))
								{
									nightshift += l.getName() + ";";
								}
							} else {
								nightshift += l.getName() + ";";
							}
						}
					}
				}
			}
		}
		temp = d + "\n" + dayshift + "\n" + nightshift;
		sp = new SpannableString(temp);
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.BLUE), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(0.8f), d.length() + 1, temp.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		textView.setText(sp);
		textView.setTextColor(Color.BLACK);

		if (currentFlag == position) {
			// ���õ���ı���
			drawable = res.getDrawable(R.drawable.current_day_bgc);
			// textView.setBackgroundColor(Color.BLACK);
			textView.setBackgroundDrawable(drawable);
			textView.setTextColor(Color.WHITE);
		}
		return convertView;
	}

	// �жϵ�ǰ���ڲ������ʱ�����
	public int insideRange(Leave l) {
		//����������
		int syear = Integer.parseInt(l.getStarttime().substring(0, 4));
		int smonth = Integer.parseInt(l.getStarttime().substring(5, 7));
		int sday = Integer.parseInt(l.getStarttime().substring(8, 10));
		int eyear = Integer.parseInt(l.getEndtime().substring(0, 4));
		int emonth = Integer.parseInt(l.getEndtime().substring(5, 7));
		int eday = Integer.parseInt(l.getEndtime().substring(8, 10));
		int current_year = Integer.parseInt(sys_year);
		int current_month = Integer.parseInt(sys_month);
		//int current_day = Integer.parseInt(sys_day);
		//����ĳ���Ǹ���ĵڼ���
		int scount = getYearDay(syear, smonth, sday);
		int ccount = getYearDay(current_year, current_month, current_day);
		int ecount = getYearDay(eyear, emonth, eday);
		
		if(syear<current_year && current_year < eyear)
		{
			return MySignal.INRANGE;
		}else if(syear == current_year && current_year < eyear)
		{
			if(ccount > scount)
			{
				return MySignal.INRANGE;
			}else if(ccount == scount)
			{
				return MySignal.ATSTART;
			}
		}else if(eyear == current_year && current_year > syear)
		{
			if(ccount < scount)
			{
				return MySignal.INRANGE;
			}else if(ccount == scount)
			{
				return MySignal.ATEND;
			}
		}else if(eyear == current_year && syear == current_year)
		{
			if(ccount < ecount && ccount > scount)
			{
				return MySignal.INRANGE;
			}else if(ccount == scount && ccount != ecount)
			{
				return MySignal.ATSTART;
			}else if(ccount == ecount && ccount != scount)
			{
				return MySignal.ATEND;
			}else if(ccount == ecount && scount == ccount)
			{
				return MySignal.EXACTDAY;
			}
		}
		return MySignal.OUTRANGE;
	}

	//����ĳ���Ǹ���ĵڼ���
	public int getYearDay(int y, int m, int d) {
		int count = 0;
		int feb = 28;

		if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
			feb = 29;
		}
		switch (m) {
		case 1:
			count = d;
			break;
		case 2:
			count = 31 + d;
			break;
		case 3:
			count = 31 + feb + d;
			break;
		case 4:
			count = 31 + feb + 31 + d;
			break;
		case 5:
			count = 31 + feb + 31 + 30 + d;
			break;
		case 6:
			count = 31 + feb + 31 + 30 + 31 + d;
			break;
		case 7:
			count = 31 + feb + 31 + 30 + 31 + 30 + d;
			break;
		case 8:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + d;
			break;
		case 9:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + d;
			break;
		case 10:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + 30 + d;
			break;
		case 11:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + d;
			break;
		case 12:
			count = 31 + feb + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + d;
		}
		return count;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	// �õ�ĳ���ĳ�µ����������µĵ�һ�������ڼ�
	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // �Ƿ�Ϊ����
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // ĳ�µ�������
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // ĳ�µ�һ��Ϊ���ڼ�
		getweek(year, month);// ��һ�����е�ÿһ���ֵ���������dayNumber��
	}

	private void getweek(int year, int month) {
		// �õ���ǰ�µ������ճ�����(��Щ������Ҫ���)
		for (int i = 0; i < dayNumber.length; i++) {
			if ((i >= dayOfWeek) && (i < daysOfMonth + dayOfWeek)) { // ����
				String day = String.valueOf(i - dayOfWeek + 1); // �õ�������
				dayNumber[i] = i - dayOfWeek + 1 + "";
				// ���ڵ�ǰ�²�ȥ��ǵ�ǰ����
				if (sys_year.equals(String.valueOf(year))
						&& sys_month.equals(String.valueOf(month))
						&& sys_day.equals(day)) {
					// ��ǵ�ǰ����
					currentFlag = i;
					// System.out.println(currentFlag);
				}
			} else {
				dayNumber[i] = " ";
			}
		}
	}
}
