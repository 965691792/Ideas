package com.example.ideas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// 手机号
	private EditText edt_login_phone;

	// 密码
	private EditText edt_login_pw;

	// 登录
	private Button btn_login;

	// 忘记密码
	private TextView txt_login_forgetpw;

	// 注册
	private TextView txt_login_register;

	// 密码可见
	private Button ibtn_login_invisible;

	// 删除
	private Button bt_login_delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		edt_login_phone = (EditText) findViewById(R.id.edt_login_phone);
		edt_login_pw = (EditText) findViewById(R.id.edt_login_pw);
		btn_login = (Button) findViewById(R.id.btn_login);
		txt_login_forgetpw = (TextView) findViewById(R.id.txt_login_forgetpw);
		txt_login_register = (TextView) findViewById(R.id.txt_login_register);
		ibtn_login_invisible = (Button) findViewById(R.id.ibtn_login_invisible);
		bt_login_delete = (Button) findViewById(R.id.bt_login_delete);

		edt_login_phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				setLoginDeleteButtonVisibility();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				setLoginDeleteButtonVisibility();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				setLoginDeleteButtonVisibility();
			}
		});

		// 点击登录按钮
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String phone = edt_login_phone.getText().toString();
				final String pw = edt_login_pw.getText().toString();

				if (phone.length() == 0) {
					Toast.makeText(getApplicationContext(), "手机号不能为空",
							Toast.LENGTH_SHORT).show();
				}
				if (pw.length() == 0) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						login("183.238.76.40", phone, pw);
						Toast.makeText(getApplicationContext(),
								login("183.238.76.40", phone, pw),
								Toast.LENGTH_SHORT).show();
						// Intent intent = new Intent(LoginActivity.this,);
						// startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// 点击忘记密码按钮
		txt_login_forgetpw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						ResetPasswordActivity.class);
				startActivity(intent);
			}
		});

		// 点击注册按钮
		txt_login_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		// 点击密码可见
		ibtn_login_invisible.setOnClickListener(new OnClickListener() {

			int i = 2;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i % 2 == 0) {
					edt_login_pw
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					i++;
				} else {
					edt_login_pw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					i++;
				}
			}
		});
	}

	public void setLoginDeleteButtonVisibility() {
		if (edt_login_phone.getText().toString().length() == 0) {
			bt_login_delete.setVisibility(View.INVISIBLE);
		} else {
			bt_login_delete.setVisibility(View.VISIBLE);
			bt_login_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edt_login_phone.getText().clear();
				}
			});
		}
	}

	public String login(String urlString, String phone, String pw)
			throws JSONException {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(1000);
			conn.setRequestProperty("Content-Type", "text/*;charset=utf-8");

			DataOutputStream outputStream = new DataOutputStream(
					conn.getOutputStream());
			JSONObject json = new JSONObject();
			json.put("PHONE", phone);
			json.put("PASSWORD", pw);
			outputStream.writeBytes(json.toString());
			outputStream.flush();
			conn.connect();

			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			InputStream inputStream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
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
