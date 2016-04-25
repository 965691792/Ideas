package com.example.ideas;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TestClearEditTextActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ClearEditTextActivity register_name = (ClearEditTextActivity) findViewById(R.id.edt_register_name);
		final ClearEditTextActivity register_phone = (ClearEditTextActivity) findViewById(R.id.edt_register_phone);
		final ClearEditTextActivity register_code = (ClearEditTextActivity) findViewById(R.id.edt_register_code);
		final ClearEditTextActivity register_pw = (ClearEditTextActivity) findViewById(R.id.edt_register_pw);
		final ClearEditTextActivity login_phone = (ClearEditTextActivity) findViewById(R.id.edt_login_phone);
		final ClearEditTextActivity login_pw = (ClearEditTextActivity) findViewById(R.id.edt_login_pw);
		final ClearEditTextActivity reset_phone = (ClearEditTextActivity) findViewById(R.id.edt_reset_phone);
		final ClearEditTextActivity reset_code = (ClearEditTextActivity) findViewById(R.id.edt_reset_code);
		final ClearEditTextActivity reset_pw = (ClearEditTextActivity) findViewById(R.id.edt_reset_pw);

		((Button) findViewById(R.id.btn_register))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (TextUtils.isEmpty(register_name.getText())) {
							// …Ë÷√ªŒ∂Ø
							register_name.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(register_phone.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(register_code.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(register_pw.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(login_phone.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(login_pw.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(reset_phone.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(reset_code.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

						if (TextUtils.isEmpty(reset_pw.getText())) {
							register_phone.setShakeAnimation();
							return;
						}

					}
				});
	}

}
