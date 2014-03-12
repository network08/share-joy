package cn.com.swpu.network08.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import cn.com.swpu.network08.R;
import cn.com.swpu.network08.util.ImageViewAdapter;

/**
 * @author xkk
 *
 */
public class HomeFragment extends Fragment implements OnClickListener{
	private GridView imageGridView;
	private ImageViewAdapter imageAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.image_grid_layout,
				container, false);
		imageGridView = (GridView)messageLayout.findViewById(R.id.image_grid_view);
		String[] imgNames = {};
		//TODO:首页展示的图片的名称
		imageAdapter = new ImageViewAdapter(getActivity(), 0, imgNames, imageGridView);
		imageGridView.setAdapter(imageAdapter);
		return messageLayout;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		imageAdapter.cancelAllTasks();
	}
	@Override
	public void onClick(View v) {
	}

}
