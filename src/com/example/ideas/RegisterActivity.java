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
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private static String APPKEY = "11b9d49fdf261";

	private static String APPSECRET = "c0e38f320b401703598a7b5f1d0d9084";

	// 用户名
	private EditText edt_register_name;

	// 用户手机
	private EditText edt_register_phone;

	// 用户密码
	private EditText edt_register_pw;

	// 注册验证码
	private EditText edt_register_code;

	// 获取验证码按钮
	private Button btn_register_code;

	// 注册按钮
	private Button btn_register;

	// 已有账号，返回登录
	private TextView txt_register_login;

	// 密码可见
	private Button ibtn_register_invisible;

	// 删除按钮
	private Button bt_register_name_delete;
	private Button bt_register_phone_delete;

	private boolean ready;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		edt_register_name = (EditText) findViewById(R.id.edt_register_name);
		edt_register_phone = (EditText) findViewById(R.id.edt_register_phone);
		edt_register_pw = (EditText) findViewById(R.id.edt_register_pw);
		edt_register_code = (EditText) findViewById(R.id.edt_register_code);
		btn_register_code = (Button) findViewById(R.id.btn_register_code);
		btn_register = (Button) findViewById(R.id.btn_register);
		txt_register_login = (TextView) findViewById(R.id.txt_register_login);
		ibtn_register_invisible = (Button) findViewById(R.id.ibtn_register_invisible);
		bt_register_name_delete = (Button) findViewById(R.id.bt_register_name_delete);
		bt_register_phone_delete = (Button) findViewById(R.id.bt_register_phone_delete);

		initSDK();

		edt_register_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				setNameButtonVisibility();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				setNameButtonVisibility();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				setNameButtonVisibility();
			}
		});

		edt_register_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				setPhoneButtonVisibility();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				setPhoneButtonVisibility();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				setPhoneButtonVisibility();
			}
		});

		// 点击获取验证码
		btn_register_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.getVerificationCode("86", edt_register_phone.getText()
						.toString());
				btn_register_code.setClickable(false);
			}
		});

		// 点击注册
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edt_register_name.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "昵称不能为空",
							Toast.LENGTH_SHORT).show();
				}
				if (edt_register_phone.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "手机号不能为空",
							Toast.LENGTH_SHORT).show();
				}
				if (edt_register_pw.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_SHORT).show();
				}
				SMSSDK.submitVerificationCode("86", edt_register_phone
						.getText().toString(), edt_register_code.getText()
						.toString());
				btn_register_code.setClickable(true);
			}
		});

		// 点击已有账号按钮
		txt_register_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});

		// 点击密码可见
		ibtn_register_invisible.setOnClickListener(new OnClickListener() {

			int i = 2;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i % 2 == 0) {
					edt_register_pw
							.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					i++;
				} else {
					edt_register_pw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					i++;
				}
			}
		});
	}

	private void initSDK() {
		// 初始化短信验证SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET, true);

		final Handler handler = new Handler();
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册监听回调
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;
	}

	protected void onDestroy() {
		if (ready) {
			// 销毁监听回调接口
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		final String name = edt_register_name.getText().toString();
		final String phone = edt_register_phone.getText().toString();
		final String pw = edt_register_pw.getText().toString();

		int event = msg.arg1;
		int result = msg.arg2;
		if (result == SMSSDK.RESULT_COMPLETE) {
			if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
				// 验证成功
				try {
					register("183.238.76.40", name, phone, pw);
					Toast.makeText(this,
							register("183.238.76.40", name, phone, pw),
							Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	public String register(String urlString, String name, String phone,
			String pw) throws JSONException {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(1000);
			conn.setRequestProperty("Content-Type", "text/*;charset=utf-8");

			DataOutputStream outputStream = new DataOutputStream(
					conn.getOutputStream());
			JSONObject json = new JSONObject();
			json.put("USERNAME", name);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void setNameButtonVisibility() {
		if (edt_register_name.getText().toString().length() == 0) {
			bt_register_name_delete.setVisibility(View.INVISIBLE);
		} else {
			bt_register_name_delete.setVisibility(View.VISIBLE);
			bt_register_name_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edt_register_name.getText().clear();
				}
			});
		}
	}

	public void setPhoneButtonVisibility() {
		if (edt_register_phone.getText().toString().length() == 0) {
			bt_register_phone_delete.setVisibility(View.INVISIBLE);
		} else {
			bt_register_phone_delete.setVisibility(View.VISIBLE);
			bt_register_phone_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					edt_register_phone.getText().clear();
				}
			});
		}
	}
}