package com.example.ideas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// 手机号
	private EditText edt_login_phone;

	// 密码
	private EditText edt_login_pw;

	// 登录
	private ButtonListener btn_login;

	// 忘记密码
	private TextView txt_login_forgetpw;

	// 注册
	private TextView txt_login_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edt_login_phone = (EditText) findViewById(R.id.edt_login_phone);
		edt_login_pw = (EditText) findViewById(R.id.edt_login_pw);
		btn_login = (ButtonListener) findViewById(R.id.btn_login);
		txt_login_forgetpw = (TextView) findViewById(R.id.txt_login_forgetpw);
		txt_login_register = (TextView) findViewById(R.id.txt_login_register);
		
		final String phone = edt_login_phone.getText().toString();
		final String pw = edt_login_pw.getText().toString();
		
		//点击登录按钮
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
				}
				if(pw.equals("")){
					Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
				}
				if(btn_login.login(u, phone, pw)){
					Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this,);
					startActivity(intent);
				}else
					Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
			}
		});
		
		//点击忘记密码按钮
		txt_login_forgetpw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
				startActivity(intent);
			}
		});
		
		//点击注册按钮
		txt_login_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
}
