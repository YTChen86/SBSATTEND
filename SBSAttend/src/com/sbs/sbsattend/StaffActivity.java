package com.sbs.sbsattend;

import com.sbs.tool.MySignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StaffActivity extends Activity {
	private String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.staff);
		name = getIntent().getStringExtra("name");
	}

	// ���
	public void dayleave(View v) {
		Intent intent = new Intent(StaffActivity.this, TakeLeaveActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}
	
	//����
	public void dayoff(View v)
	{
		Intent intent = new Intent(StaffActivity.this, OverWorkActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}

	// ���°�νӿ�������,��Ϊ�ϵ�ֵ��ϵͳ�޷�ͨ��wlan����
	public void watch_web(View v) {
		Intent intent = new Intent(StaffActivity.this, WatchTableActivity.class);
		StaffActivity.this.startActivity(intent);
	}
	
	//�鿴��������������
	public void watch_leave(View v){
		Intent intent = new Intent(StaffActivity.this, QueryLeaveActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}
	
	//�鿴���µ����������
	public void watch_overtime(View v){
		Intent intent = new Intent(StaffActivity.this, QueryOverTimeActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}
	
	//���°�Σ���ʱ����listview
	public void watch_table(View v)
	{
		Intent intent = new Intent(StaffActivity.this, QueryWorkInfoActivity.class);
		StaffActivity.this.startActivity(intent);
	}
	
	//��ʾ����ֵ����������ķ�ʽ
	public void watch_shift(View v){
		Intent intent = new Intent(StaffActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETSHIFT);
		StaffActivity.this.startActivity(intent);
	}
	
	public void watch_rest(View v){
		Intent intent = new Intent(StaffActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETREST);
		StaffActivity.this.startActivity(intent);
	}
	
	public void watch_leaveall(View v){
		Intent intent = new Intent(StaffActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETLEAVE);
		StaffActivity.this.startActivity(intent);
	}
}
