package cn.com.swpu.network08.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.com.swpu.network08.R;
import cn.com.swpu.network08.util.LocationProvider;

/**
 * @author xkk
 *
 */
public class HomeFragment extends Fragment implements OnClickListener{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.home_fragment_layout,
				container, false);
		Toast.makeText(getActivity(), LocationProvider.getInstance(getActivity()).getLocation(), Toast.LENGTH_SHORT).show();
		return messageLayout;
	}
	@Override
	public void onClick(View v) {
	}

}
