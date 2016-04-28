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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity implements OnClickListener,
		Callback {
	// 填写从短信SDK应用后台注册得到的APPKEY
	private static String APPKEY = "11b9d49fdf261";

	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "c0e38f320b401703598a7b5f1d0d9084";

	// 手机
	private EditText edt_reset_phone;

	// 验证码
	private EditText edt_reset_code;

	// 获取验证码
	private Button btn_reset_code;

	// 密码
	private EditText edt_reset_pw;

	// 完成
	private Button btn_reset;

	// 密码可见
	private Button ibtn_reset_invisible;

	// 删除按钮
	private Button bt_resetpassword_delete;

	private boolean ready;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);

		edt_reset_phone = (EditText) findViewById(R.id.edt_reset_phone);
		edt_reset_code = (EditText) findViewById(R.id.edt_reset_code);
		btn_reset_code = (Button) findViewById(R.id.btn_reset_code);
		edt_reset_pw = (EditText) findViewById(R.id.edt_reset_pw);
		btn_reset = (Button) findViewById(R.id.btn_reset);
		ibtn_reset_invisible = (Button) findViewById(R.id.ibtn_reset_invisible);
		bt_resetpassword_delete = (Button) findViewById(R.id.bt_resetpassword_delete);

		initSDK();

		edt_reset_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				setResetDeleteButtonVisibility();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				setResetDeleteButtonVisibility();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				setResetDeleteButtonVisibility();
			}
		});

		// 点击获取验证码按钮
		btn_reset_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.getVerificationCode("86", edt_reset_phone.getText()
						.toString());
				btn_reset_code.setClickable(false);
			}
		});

		// 点击完成按钮
		btn_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edt_reset_phone.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "手机号不能为空",
							Toast.LENGTH_SHORT).show();
				}
				if (edt_reset_pw.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
				}
				SMSSDK.submitVerificationCode("86", edt_reset_phone.getText()
						.toString(), edt_reset_code.getText().toString());
				btn_reset_code.setClickable(true);
			}
		});

		// 点击密码可见
		ibtn_reset_invisible.setOnClickListener(new OnClickListener() {

			int i = 2;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i % 2 == 0) {
					edt_reset_pw
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					i++;
				} else {
					edt_reset_pw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					i++;
				}
			}
		});
	}

	private void initSDK() {
		// 初始化短信SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET, true);

		final Handler handler = new Handler((Callback) this);
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;
	}

	protected void onDestroy() {
		if (ready) {
			// 销毁回调监听接口
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		final String phone = edt_reset_phone.getText().toString();
		final String pw = edt_reset_pw.getText().toString();

		int event = msg.arg1;
		int result = msg.arg2;
		if (result == SMSSDK.RESULT_COMPLETE) {
			if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
				try {
					reset("183.238.76.40", phone, pw);
					Toast.makeText(this, reset("183.238.76.40", phone, pw),
							Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	public String reset(String urlString, String phone, String pw)
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

	public void setResetDeleteButtonVisibility() {
		if (edt_reset_phone.getText().toString().length() == 0) {
			bt_resetpassword_delete.setVisibility(View.INVISIBLE);
		} else {
			bt_resetpassword_delete.setVisibility(View.VISIBLE);
			bt_resetpassword_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edt_reset_phone.getText().clear();
				}
			});
		}
	}
}
