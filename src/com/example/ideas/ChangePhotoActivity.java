package com.example.ideas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ChangePhotoActivity extends Activity{

	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// ����
	public static final int PHOTOZOOM = 2; // ����
	public static final int PHOTORESOULT = 3;// ���

	public static final String IMAGE_UNSPECIFIED = "image/*";
	private ImageButton imageButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personalpage);
	    imageButton = (ImageButton)findViewById(R.id.ibtn_personalpg_img);
		imageButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				openOptionsMenu();
			}
			
		});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choosephoto, menu);
        return true;
    }
	
    public boolean onOptionsItemSelected(MenuItem item) { {
	    
	        switch (item.getItemId()) {
	        // ����
	        case R.id.takephoto_item:
	        	Intent intent_takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent_takephoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), "temp.jpg")));
				System.out.println("=============" + Environment.getExternalStorageDirectory());
				startActivityForResult(intent_takephoto, PHOTOHRAPH);
	            break;
	        // ���ѡ��ͼƬ
	        case R.id.choosefromgallery_item:
	        	Intent intent_choose = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent_choose.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent_choose, PHOTOZOOM);
	            break;
	        default:
	            break;
	        }
	    }
	return false;
	};
	
 

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// ����
		if (requestCode == PHOTOHRAPH) {
		// �����ļ�����·��������ڸ�Ŀ¼��
			File picture = new File(Environment.getExternalStorageDirectory()+ "/temp.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// ��ȡ�������ͼƬ
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// ������
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
				imageButton.setImageBitmap(photo);
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startPhotoZoom(Uri data) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		//��ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		//�ü�ͼƬ���
		intent.putExtra("outputX", 64);
		intent.putExtra("outputY", 64);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}


}
