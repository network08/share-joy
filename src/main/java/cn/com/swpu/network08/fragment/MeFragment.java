package cn.com.swpu.network08.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.com.swpu.network08.R;
import cn.com.swpu.network08.model.User;
/**
 * 
 * @author xkk
 *
 */
public class MeFragment extends Fragment implements OnClickListener{
	Button saveBtn;
	EditText emailEt;
	EditText phoneEt;
	EditText nameEt;
	ImageButton myImgBtn;
	private static final int CAMERA_OPTION = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.me_fragment_layout,
				container, false);
		saveBtn = (Button)messageLayout.findViewById(R.id.me_save_btn);
		myImgBtn = (ImageButton)messageLayout.findViewById(R.id.me_img_btn);
		emailEt = (EditText)messageLayout.findViewById(R.id.me_email);
		phoneEt = (EditText)messageLayout.findViewById(R.id.me_phone);
		nameEt = (EditText)messageLayout.findViewById(R.id.me_nickname);

		saveBtn.setOnClickListener(this);
		myImgBtn.setOnClickListener(this);
		loadUserInfo();
		return messageLayout;
	}

	private void loadUserInfo(){
		phoneEt.setText(getPhoneNum());
	}

	private String getPhoneNum(){
		String phone = null;
		Object o = getActivity(). getSystemService(Context.TELEPHONY_SERVICE);
		if(o != null){
			TelephonyManager tm = (TelephonyManager)o;
			phone =  tm.getLine1Number();
		}

		return phone; 
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.me_save_btn:
			Toast.makeText(getActivity(), "edit userinfo", Toast.LENGTH_SHORT).show();	
			break;
		case R.id.me_img_btn:
			intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_OPTION);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			Bundle bundle=data.getExtras();
			if (bundle!=null) {
				Bitmap bm=(Bitmap) bundle.get("data");
				myImgBtn.setImageBitmap(bm);
			}
		}
	}

	private User getUserDataFromUI(){  
		User user = new User(); 
		user.setEmail(emailEt.getText().toString());
		user.setName(nameEt.getText().toString());
		user.setPhone(phoneEt.getText().toString());
		return user;
	}
	private void setUserDataToUI(User user){
		emailEt.setText(user.getEmail());
		nameEt.setText(user.getName());
		phoneEt.setText(user.getPhone());
	}
}
