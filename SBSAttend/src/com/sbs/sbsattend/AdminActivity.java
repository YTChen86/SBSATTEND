package com.sbs.sbsattend;

import com.sbs.tool.MySignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AdminActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.admin);
	}
	
	//�������
	public void review_leave(View v)
	{
		 Intent intent = new Intent(AdminActivity.this, ReivewLeaveActivity.class);
		 AdminActivity.this.startActivity(intent);
	}
	
	public void review_over(View v)
	{
		Intent intent = new Intent(AdminActivity.this, ReivewOverTimeActvity.class);
		AdminActivity.this.startActivity(intent);
	}
	
	//���°����ҳ�ӿڣ��ѷ���
	public void watch_web(View v)
	{
		 Intent intent = new Intent(AdminActivity.this, WatchTableActivity.class);
		 AdminActivity.this.startActivity(intent);
	}
	
	//���°�Σ���ʱ����
	public void watch_table(View v)
	{
		Intent intent = new Intent(AdminActivity.this, QueryWorkInfoActivity.class);
		AdminActivity.this.startActivity(intent);
	}
	
	//��ʾ����ֵ����������ķ�ʽ
	public void watch_shift(View v){
		Intent intent = new Intent(AdminActivity.this, QueryShiftActivity.class);
		AdminActivity.this.startActivity(intent);
	}
	
	//��ʾ���µ��ݱ�������ʽ
	public void watch_rest(View v){
		Intent intent = new Intent(AdminActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETREST);
		AdminActivity.this.startActivity(intent);
	}
	
	//��ʾ������ٱ��б���ʽ
	public void watch_leaveall(View v){
		Intent intent = new Intent(AdminActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETLEAVE);
		AdminActivity.this.startActivity(intent);
	}

}
