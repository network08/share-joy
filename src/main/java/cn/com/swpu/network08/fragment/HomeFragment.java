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
public class HomeFragment extends Fragment implements OnClickListener{
//	private GridView imageGridView;
//	private ImageViewAdapter imageAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.image_grid_layout,
				container, false);
		return messageLayout;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//imageAdapter.cancelAllTasks();
	}
	@Override
	public void onClick(View v) {
	}

}
