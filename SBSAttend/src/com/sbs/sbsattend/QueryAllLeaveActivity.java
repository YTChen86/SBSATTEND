package com.sbs.sbsattend;

import java.util.List;

import com.sbs.sbsattend.QueryLeaveActivity.ViewHolder;
import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Logic;
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

/*attention:�ѷ���!!*/
public class QueryAllLeaveActivity extends Activity {
	private ListView lv;
	private List<Leave> les;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.leaveall);
		lv = (ListView) findViewById(R.id.lv_leaveall);
		
		les = Logic.query_leavetimeall();
		if (les == null) {
			Toast.makeText(QueryAllLeaveActivity.this, "��������ݣ��˳�",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}
		
		MyAdapter mAdapter = new MyAdapter(this); // �õ�һ��MyAdapter����
		lv.setAdapter(mAdapter); // ΪListView��Adapter
		LayOut.setListViewHeightBasedOnChildren(lv);	
	}
	
	public void quit(View v)
	{
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
			if (les != null)
				return les.size();// ��������ĳ���;
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
				convertView = mInflater.inflate(R.layout.leaveall_item, null);
				holder = new ViewHolder();
				/* �õ������ؼ��Ķ��� */
				holder.name = (TextView) convertView
						.findViewById(R.id.name_tv);
				holder.start = (TextView) convertView.findViewById(R.id.lstart_tv);
				holder.end = (TextView) convertView
						.findViewById(R.id.lend_tv);
				convertView.setTag(holder);// ��ViewHolder����
			} else {
				holder = (ViewHolder) convertView.getTag();// ȡ��ViewHolder����
			}
			/* ����TextView��ʾ������ */
			holder.name.setText(les.get(position).getName());
			holder.start.setText(les.get(position).getStarttime().substring(0, 10)+ " " + les.get(position).getOriginweek() + " "+ les.get(position).getOriginshift());
			holder.end.setText(les.get(position).getEndtime().substring(0, 10)+ " "+ les.get(position).getCurrentweek()+" "+ les.get(position).getCurrentshift());

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
	}
}
