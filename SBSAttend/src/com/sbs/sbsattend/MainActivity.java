package com.sbs.sbsattend;

import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText userName, password;
	private CheckBox rem_pw, auto_login;
	private String userNameValue, passwordValue;
	private SharedPreferences sp;
	private Person p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		// ���ʵ������
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		userName = (EditText) findViewById(R.id.et_zh);
		password = (EditText) findViewById(R.id.et_mima);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);

		// �жϼ�ס�����ѡ���״̬
		if (sp.getBoolean("ISCHECK", false)) {
			// ����Ĭ���Ǽ�¼����״̬
			rem_pw.setChecked(true);
			userName.setText(sp.getString("USER_NAME", ""));
			password.setText(sp.getString("PASSWORD", ""));
			// �ж��Զ���½��ѡ��״̬
			if (sp.getBoolean("AUTO_ISCHECK", false)) {
				// ����Ĭ�����Զ���¼״̬
				auto_login.setChecked(true);
				step();
			}
		} 
  
        //������ס�����ѡ��ť�¼�  
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (rem_pw.isChecked()) {  
                      
                    System.out.println("��ס������ѡ��");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("��ס����û��ѡ��");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });  
          
        //�����Զ���¼��ѡ���¼�  
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (auto_login.isChecked()) {  
                    System.out.println("�Զ���¼��ѡ��");  
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();  
  
                } else {  
                    System.out.println("�Զ���¼û��ѡ��");  
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();  
                }  
            }  
        });  
	}
	public void step()
	{
		Intent intent;
		//����жϣ�������ת
		try {
			p = Logic.login(userName.getText().toString(), password.getText().toString());
			switch(p.getPermission())
			{
			case 0:
				 Toast.makeText(MainActivity.this,"��ݣ�Ա��", Toast.LENGTH_LONG).show();  
				 intent = new Intent(MainActivity.this, StaffActivity.class);
				 intent.putExtra("name", p.getName());
				 MainActivity.this.startActivity(intent);
				 break;
			case 1:
				 Toast.makeText(MainActivity.this,"��ݣ��쵼", Toast.LENGTH_LONG).show();
				 intent = new Intent(MainActivity.this, AdminActivity.class);
				 intent.putExtra("quotan", p.getQuotan());
				 MainActivity.this.startActivity(intent);
				 break;
			default:
				 Toast.makeText(MainActivity.this,"�û�����������������µ�¼", Toast.LENGTH_LONG).show();  
				 break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sys_login(View v)
	{
		userNameValue = userName.getText().toString();  
        passwordValue = password.getText().toString();  
          
        
        if(rem_pw.isChecked())  
        {  
         //��ס�û��������롢  
          Editor editor = sp.edit();  
          editor.putString("USER_NAME", userNameValue);  
          editor.putString("PASSWORD",passwordValue);  
          editor.commit();  
        }  
        //��ת����  
        step();
        //finish();  

	}
	
	public void sys_quit(View v)
	{
		finish();
	}
}
