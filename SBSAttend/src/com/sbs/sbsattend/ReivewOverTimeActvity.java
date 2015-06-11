package com.sbs.sbsattend;

import java.util.List;

import com.sbs.sbsattend.ReivewLeaveActivity.ViewHolder;
import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Work;
import com.sbs.tool.LayOut;
import com.sbs.tool.MySignal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReivewOverTimeActvity extends Activity {
	private ListView lv;
	private List<Work> wks;
	private Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reivewovertime);
		bt = (Button) findViewById(R.id.overtime_bt);
		lv = (ListView) findViewById(R.id.overtime_lv);

		wks = Logic.query_overtime();
		if (wks == null) {
			bt.setText("��������Ϣ������˳�");
		}
		MyAdapter mAdapter = new MyAdapter(this);// �õ�һ��MyAdapter����
		lv.setAdapter(mAdapter); // ΪListView��Adapter
		LayOut.setListViewHeightBasedOnChildren(lv);
	}

	public void commit(View v) {
		int count = 0;

		if (wks == null) {
			Toast.makeText(ReivewOverTimeActvity.this, "�޿�������Ϣ���˳���",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}

		for (Work l : wks) {
			count = count + Logic.approve_overtime(l);
		}

		if (count == wks.size()) {
			Toast.makeText(ReivewOverTimeActvity.this, "�����ύ�ɹ���",
					Toast.LENGTH_LONG).show();
		} else {
			StringBuilder temp = new StringBuilder();
			temp.append(wks.size() - count);
			Toast.makeText(ReivewOverTimeActvity.this,
					"���β�����" + temp.toString() + "���ύʧ��", Toast.LENGTH_LONG)
					.show();
		}
		finish();
	}

	/*
	 * �½�һ����̳�BaseAdapter��ʵ����ͼ�����ݵİ�
	 */
	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;// �õ�һ��LayoutInfalter�����������벼��

		/* ���캯�� */
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (wks != null)
				return wks.size();// ��������ĳ���;
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.leaveitem, null);
				holder = new ViewHolder();
				/* �õ������ؼ��Ķ��� */
				holder.name = (TextView) convertView
						.findViewById(R.id.staff_name);
				holder.start = (TextView) convertView
						.findViewById(R.id.starttime);
				holder.end = (TextView) convertView.findViewById(R.id.endtime);
				holder.res = (TextView) convertView.findViewById(R.id.reason);
				holder.cb = (CheckBox) convertView.findViewById(R.id.ifagree);
				convertView.setTag(holder);// ��ViewHolder����
			} else {
				holder = (ViewHolder) convertView.getTag();// ȡ��ViewHolder����
			}
			/* ����TextView��ʾ�����ݣ������Ǵ���ڶ�̬�����е����� */
			if (wks.get(position).getOriginrest() != "") {
				holder.name.setText(wks.get(position).getName());
				holder.start.setText(wks.get(position).getOriginrest()
						.substring(0, 10)+" "+wks.get(position).getOriginweek()+"\n"
						+ wks.get(position).getOriginshift());
				holder.end.setText(wks.get(position).getCurrentrest()
						.subSequence(0, 10)+" "+wks.get(position).getCurrentweek()+"\n"
						+ wks.get(position).getCurrentshift());
				holder.res.setText(wks.get(position).getReason());
				// Ĭ��������ͨ��
				wks.get(position).setApprove(MySignal.NOTAPPROVE);
			}

			holder.cb.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (holder.cb.isChecked()) {
						wks.get(position).setApprove(MySignal.APPROVE); // �����ѡ�У���approve��Ϊ4
					} else {
						wks.get(position).setApprove(MySignal.NOTAPPROVE); // ���δ�У���approve��Ϊ5
					}
				}
			});

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB��ɫ
			convertView.setBackgroundColor(colors[position % 2]);// ÿ��item֮����ɫ��ͬ

			return convertView;
		}

	}

	/* ��ſؼ� */
	public final class ViewHolder {
		public TextView name;
		public TextView start;
		public TextView end;
		public TextView res;
		public CheckBox cb;
	}

}
