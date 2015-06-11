package com.sbs.sbsattend;

import java.util.List;

import com.sbs.sbsattend.QueryOverTimeActivity.ViewHolder;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.WorkHistory;
import com.sbs.tool.LayOut;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*�ѷ���*/
public class QueryWorkInfoActivity extends Activity {
	private ListView lv;
	private List<WorkHistory> wks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.queryworkinfo);
		lv = (ListView) findViewById(R.id.workinfo_lv);
		wks = Logic.query_monthlyworkinfo();

		if (wks == null) {
			Toast.makeText(QueryWorkInfoActivity.this, "�޵������ݣ��˳�",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}

		MyAdapter mAdapter = new MyAdapter(this); // �õ�һ��MyAdapter����
		lv.setAdapter(mAdapter); // ΪListView��Adapter
		LayOut.setListViewHeightBasedOnChildren(lv);
	}

	public void quit(View v) {
		this.finish();
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
			String result = "";
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.queryitem, null);
				holder = new ViewHolder();
				/* �õ������ؼ��Ķ��� */
				holder.day = (TextView) convertView
						.findViewById(R.id.start_tv);
				holder.night = (TextView) convertView.findViewById(R.id.end_tv);
				holder.date = (TextView) convertView
						.findViewById(R.id.result_tv);
				convertView.setTag(holder);// ��ViewHolder����
			} else {
				holder = (ViewHolder) convertView.getTag();// ȡ��ViewHolder����
			}
			/* ����TextView��ʾ������ */

			holder.day.setText(wks.get(position).getDaywork());
			holder.night.setText(wks.get(position).getNightwork());
			holder.date.setText(wks.get(position).getWorktime().substring(0,10));

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB��ɫ
			convertView.setBackgroundColor(colors[position % 2]);// ÿ��item֮����ɫ��ͬ

			return convertView;
		}
	}

	/* ��ſؼ� */
	public final class ViewHolder {
		public TextView day;
		public TextView night;
		public TextView date;
	}

}
