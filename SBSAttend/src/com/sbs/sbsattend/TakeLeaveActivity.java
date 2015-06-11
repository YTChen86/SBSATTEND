package com.sbs.sbsattend;

import com.sbs.sbsattend.OverWorkActivity.CurrentSpinnerListener;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Person;
import com.sbs.tool.DateTimePickDialogUtil;
import com.sbs.tool.MySignal;
import com.sbs.tool.SpecialCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class TakeLeaveActivity extends Activity {
	private Person p;
	private String name;
	private String initTime = "";
	private EditText input;
	private EditText input2;
	private EditText reason;
	private Spinner origin;
	private Spinner current;
	private String shift_origin;
	private String shift_current;
	private ArrayAdapter ad_current;
	private ArrayAdapter ad_origin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.takeleave);
		init_components();	  
	}

	// ��ʼ�������ؼ�
	public void init_components() {
		input = (EditText) findViewById(R.id.leavetimepick);
		input2 = (EditText) findViewById(R.id.leavetimepick2);
		reason = (EditText) findViewById(R.id.leave_reason);
		origin = (Spinner) findViewById(R.id.leave_origin);
		current = (Spinner) findViewById(R.id.leave_current);

		// ����ѡ������ArrayAdapter��������
		ad_current = ArrayAdapter.createFromResource(this, R.array.class_kind,
				android.R.layout.simple_spinner_item);
		// ���������б�ķ��
		ad_current
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		current.setAdapter(ad_current);
		// ����¼�Spinner�¼�����
		current.setOnItemSelectedListener(new CurrentSpinnerListener());
		// ����Ĭ��ֵ
		current.setVisibility(View.VISIBLE);
		current.setSelection(0);

		// ����ѡ������ArrayAdapter��������
		ad_origin = ArrayAdapter.createFromResource(this, R.array.class_kind,
				android.R.layout.simple_spinner_item);
		// ���������б�ķ��
		ad_origin
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		origin.setAdapter(ad_origin);
		// ����¼�Spinner�¼�����
		origin.setOnItemSelectedListener(new OriginSpinnerListener());
		// ����Ĭ��ֵ
		origin.setVisibility(View.VISIBLE);
		origin.setSelection(0);

		name = getIntent().getStringExtra("name");
	}

	class OriginSpinnerListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> adpater, View view,
				int position, long id) {
			shift_origin = (String) ad_origin.getItem(position);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	class CurrentSpinnerListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> adpater, View view,
				int position, long id) {
			shift_current = (String) ad_current.getItem(position);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	public void select_date(View v) {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				TakeLeaveActivity.this, initTime);
		dateTimePicKDialog.dateTimePicKDialog2(input,input2);
	}

	public void select_date2(View v) {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				TakeLeaveActivity.this, input.getText().toString());
		dateTimePicKDialog.dateTimePicKDialog(input2);
	}

	public void quit(View v) {
		this.finish();
	}

	public void commit(View v) {
		// ��ȡѡ������
		String start = input.getText().toString();
		String end = input2.getText().toString();
		String res = reason.getText().toString();
		
		if (start.equals("��ٿ�ʼ����") || end.equals("��ٽ�������")) {
			Toast.makeText(TakeLeaveActivity.this, "δѡ�����ڣ��޷��ύ",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}
		String oweek = SpecialCalendar.getWeekdayofYear(Integer.parseInt(start.substring(0, 4)), Integer.parseInt(start.substring(5, 7)), Integer.parseInt(start.substring(8, 10)));
		String cweek = SpecialCalendar.getWeekdayofYear(Integer.parseInt(end.substring(0, 4)), Integer.parseInt(end.substring(5, 7)), Integer.parseInt(end.substring(8, 10)));
		
		// ���ݿ����
		int flag = Logic.commit_leaevtime(name, start, end, shift_origin, shift_current, oweek, cweek, res);

		if (flag != MySignal.CORRECTCOMMIT) {
			Toast.makeText(TakeLeaveActivity.this, "���ݿ��ύʧ�ܣ�",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(TakeLeaveActivity.this, "��������ύ�ɹ���",
					Toast.LENGTH_LONG).show();
		}
		this.finish();
	}

}
