package com.sbs.sbsattend.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Person;
import com.sbs.sbsattend.model.Work;
import com.sbs.sbsattend.model.WorkHistory;

@SuppressWarnings("deprecation")
public class DBConnection {

	/* ����SQL������ڶ�ȡweb���� */
	public static List<Person> getPerson(String sql, String url) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("SQL", sql);
		try {
			HttpResponse response= sendHttpClientPOSTRequest(url, params, "UTF-8");
			List<Person> pes = JSONService.parsePerson(response.getEntity().getContent());
			return pes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*����SQL�����ȡֵ����Ϣ*/
	public static List<String> getWorkInfo(String sql, String url)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("SQL", sql);

		try {
			HttpResponse response= sendHttpClientPOSTRequest(url, params, "UTF-8");
			return JSONService.parseWorkInfo(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*����SQL�����ȡ���������Ϣ*/
	public static List<Leave> getLeave(String sql, String url)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("SQL", sql);
		try {
			HttpResponse response= sendHttpClientPOSTRequest(url, params, "UTF-8");
			return JSONService.parseLeave(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*����SQL�����ȡֵ����Ϣ*/
	public static List<WorkHistory> getWorkHistory(String sql, String url)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("SQL", sql);
		try {
			HttpResponse response= sendHttpClientPOSTRequest(url, params, "UTF-8");
			return JSONService.parseWorkHistory(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*����SQL�����ȡ����������Ϣ*/
	public static List<Work> getWork(String sql, String url)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("SQL", sql);
		try {
			HttpResponse response= sendHttpClientPOSTRequest(url, params, "UTF-8");
			return JSONService.parseWork(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*����SQL���ִ������*/
	public static int exectue(String sql, String url){
		int flag = 0;
		Map<String, String> params = new HashMap<String, String>();
		params.put("SQL", sql);
		try {
			HttpResponse response = sendHttpClientPOSTRequest(url, params, "UTF-8");
			flag = JSONService.parseResult(response.getEntity().getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/*ͨ��POST������WEB��������*/
	private static HttpResponse sendHttpClientPOSTRequest(String path,
			Map<String, String> params, String encoding) throws Exception {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();// ����������
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
		HttpPost httpPost = new HttpPost(path);
		httpPost.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if (response.getStatusLine().getStatusCode() != 200) {
			System.out.println("���ݿ�������ʧ��");
			return null;
		}
		
		System.out.println("���ݿ������ͳɹ�");
		return response;
	}
}
