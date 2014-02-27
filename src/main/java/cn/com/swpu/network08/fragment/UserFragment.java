package cn.com.swpu.network08.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import cn.com.swpu.network08.R;

public class UserFragment extends Fragment implements OnClickListener{
	Button editBtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.me_fragment_layout,
				container, false);
		editBtn = (Button)messageLayout.findViewById(R.id.me_edit_btn);
		editBtn.setOnClickListener(this);

		return messageLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.me_edit_btn:
			Toast.makeText(getActivity(), "edit userinfo", 
					Toast.LENGTH_SHORT).show();	
			break;
		default:
			break;
		}

	}
}
