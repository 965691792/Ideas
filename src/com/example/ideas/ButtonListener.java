package com.example.ideas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ButtonListener extends Button implements OnClickListener {

	public ButtonListener(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	public void sendMessage(String u, String s1, String s2, String s3) {
		URL url = null;
		try {
			url = new URL(u);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			Message msg1 = new Message();
			msg1.obj = s1;
			handler.sendMessage(msg1);
			Message msg2 = new Message();
			msg2.obj = s2;
			handler.sendMessage(msg2);
			Message msg3 = new Message();
			msg3.obj = s3;
			handler.sendMessage(msg3);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String u, String s1, String s2) {
		boolean b = false;
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		try{
			conn = (HttpURLConnection) url.openConnection();
		}catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = null;
		try{
			is = conn.getInputStream();
			Message msg1 = new Message();
			msg1.obj = s1;
			handler.sendMessage(msg1);
			Message msg2 = new Message();
			msg2.obj = s2;
			handler.sendMessage(msg2);
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = in.readLine())!=null){
				if(line.equals("true"))
					b = true;
			}
			is.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		conn.disconnect();
		return b;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
