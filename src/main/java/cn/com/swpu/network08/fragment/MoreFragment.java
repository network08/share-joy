package cn.com.swpu.network08.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.com.swpu.network08.R;

/**
 * @author xkk
 *
 */
public class MoreFragment extends Fragment implements OnClickListener{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.more_fragment_layout,
				container, false);
		return messageLayout;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
