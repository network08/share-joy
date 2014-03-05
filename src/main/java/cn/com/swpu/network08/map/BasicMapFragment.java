package cn.com.swpu.network08.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import cn.com.swpu.network08.MyApplication;
import cn.com.swpu.network08.R;

import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * @author xkk
 *
 */
public class BasicMapFragment extends Fragment implements OnClickListener{
	/**
	 *  MapView 是地图主控件
	 */
	private MapView basicMapView = null;
	/**
	 *  用MapController完成地图控制 
	 */
	private MapController basicMapController = null;
	/**
	 *  MKMapViewListener 用于处理地图事件回调
	 */
	MKMapViewListener basicMapListener = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyApplication app = (MyApplication)getActivity().getApplication(); 
		app.getBaseContext();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.basic_map_layout,
				container, false);
		basicMapView = (MapView)messageLayout.findViewById(R.id.bmapView);
		//获取地图控制器
		basicMapController = basicMapView.getController();
		basicMapController.enableClick(true);
		//设置地图缩放级别
		basicMapController.setZoom(12);
		basicMapController.setCenter(new GeoPoint((int)(30.827* 1E6), (int)(104.189* 1E6)));//swpu
		basicMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
				/**
				 * 在此处理地图移动完成回调
				 * 缩放，平移等操作完成后，此回调被触发
				 */
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * 在此处理底图poi点击事件
				 * 显示底图poi名称并移动至该点
				 * 设置过： basicMapController.enableClick(true); 时，此回调才能被触发
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null){
					title = mapPoiInfo.strText;
					showToast(title);
					basicMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 *  当调用过 basicMapView.getCurrentMap()后，此回调会被触发
				 *  可在此保存截图至存储设备
				 */
			}

			@Override
			public void onMapAnimationFinish() {
				/**
				 *  地图完成带动画的操作（如: animationTo()）后，此回调被触发
				 */
			}
			/**
			 * 在此处理地图载完成事件 
			 */
			@Override
			public void onMapLoadFinish() {
				showToast("地图加载完成");
			}
		};
		basicMapView.regMapViewListener(MyApplication.getInstance().getbMapManager(), basicMapListener);
		return messageLayout;
	}
	@Override
	public void onClick(View arg0) {

	}

	@Override
	public void onResume() {
		basicMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		basicMapView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		basicMapView.destroy();
		super.onDestroy();
	}
	
	private void showToast(String content){
		Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
	}
}
