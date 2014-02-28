package cn.com.swpu.network08.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.com.swpu.network08.R;
import cn.com.swpu.network08.model.User;

public class MeFragment extends Fragment implements OnClickListener{
	Button saveBtn;
	EditText emailEt;
	EditText phoneEt;
	EditText nameEt;
	ImageView myImg;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.me_fragment_layout,
				container, false);
		saveBtn = (Button)messageLayout.findViewById(R.id.me_save_btn);
		emailEt = (EditText)messageLayout.findViewById(R.id.me_email);
		phoneEt = (EditText)messageLayout.findViewById(R.id.me_phone);
		nameEt = (EditText)messageLayout.findViewById(R.id.me_nickname);
		myImg = (ImageView)messageLayout.findViewById(R.id.me_img);
		saveBtn.setOnClickListener(this);
		
		return messageLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.me_save_btn:
			Toast.makeText(getActivity(), "edit userinfo", Toast.LENGTH_SHORT).show();	
			break;
		case R.id.me_img:
			Toast.makeText(getActivity(), "upload a head portrait", Toast.LENGTH_SHORT).show();	
			break;
		default:
			break;
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
