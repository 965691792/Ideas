package com.example.ideas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// �ֻ���
	private EditText edt_login_phone;

	// ����
	private EditText edt_login_pw;

	// ��¼
	private ButtonListener btn_login;

	// ��������
	private TextView txt_login_forgetpw;

	// ע��
	private TextView txt_login_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edt_login_phone = findViewById(R.id.edt_login_phone);
		edt_login_pw = findViewById(R.id.edt_login_pw);
		btn_login = findViewById(R.id.btn_login);
		txt_login_forgetpw = findViewById(R.id.txt_login_forgetpw);
		txt_login_register = findViewById(R.id.txt_login_register);
		
		final String phone = edt_login_phone.getText().toString();
		final String pw = edt_login_pw.getText().toString();
		
		//�����¼��ť
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "�ֻ��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(pw.equals("")){
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(login(u, phone, pw).equals("success")){
					Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this,);
					startActivity(intent);
				}else
					Toast.makeText(getApplicationContext(), login(u,phone,pw), Toast.LENGTH_SHORT).show();
			}
		});
		
		//����������밴ť
		txt_login_forgetpw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
				startActivity(intent);
			}
		});
		
		//���ע�ᰴť
		txt_login_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	public String login(String urlString, String phone, String pw) throws JSONException {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(1000);
			// �趨���͵����������ǿ����л���java����
			// (����������,�ڴ������л�����ʱ,��WEB����Ĭ�ϵĲ�����������ʱ������java.io.EOFException)
			conn.setRequestProperty("Content-Type", "text/*;charset=utf-8");
			DataOutputStream outputStream = new DataOutputStream(
					conn.getOutputStream());
			JSONObject user = new JSONObject();
			user.put("phone", phone);
			user.put("password", pw);
			outputStream.writeBytes(user.toString());
			outputStream.flush();
			conn.connect();

			conn.setRequestMethod("Get");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
