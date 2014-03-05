package cn.com.swpu.network08.map;

import android.content.Intent;
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
	private MapView mMapView = null;
	/**
	 *  用MapController完成地图控制 
	 */
	private MapController mMapController = null;
	/**
	 *  MKMapViewListener 用于处理地图事件回调
	 */
	MKMapViewListener mMapListener = null;
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
		mMapView = (MapView)getActivity().findViewById(R.id.bmapView);
		/**
		 * 获取地图控制器
		 */
		mMapController = mMapView.getController();
		/**
		 *  设置地图是否响应点击事件  .
		 */
		mMapController.enableClick(true);
		/**
		 * 设置地图缩放级别
		 */
		mMapController.setZoom(12);

		/**
		 * 将地图移动至指定点
		 * 使用百度经纬度坐标，可以通过http://api.map.baidu.com/lbsapi/getpoint/index.html查询地理坐标
		 * 如果需要在百度地图上显示使用其他坐标系统的位置，请发邮件至mapapi@baidu.com申请坐标转换接口
		 */
		GeoPoint p ;
		double cLat = 39.945 ;
		double cLon = 116.404 ;
		Intent  intent = getActivity().getIntent();
		if ( intent.hasExtra("x") && intent.hasExtra("y") ){
			//当用intent参数时，设置中心点为指定点
			Bundle b = intent.getExtras();
			p = new GeoPoint(b.getInt("y"), b.getInt("x"));
		}else{
			//设置中心点为天安门
			p = new GeoPoint((int)(cLat * 1E6), (int)(cLon * 1E6));
		}

		mMapController.setCenter(p);

		mMapListener = new MKMapViewListener() {
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
				 * 设置过： mMapController.enableClick(true); 时，此回调才能被触发
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null){
					title = mapPoiInfo.strText;
					Toast.makeText(getActivity(),title,Toast.LENGTH_SHORT).show();
					mMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 *  当调用过 mMapView.getCurrentMap()后，此回调会被触发
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
				Toast.makeText(getActivity(), 
						"地图加载完成", 
						Toast.LENGTH_SHORT).show();

			}
		};
		mMapView.regMapViewListener(MyApplication.getInstance().getbMapManager(), mMapListener);
		return messageLayout;
	}
	@Override
	public void onClick(View arg0) {

	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		mMapView.destroy();
		super.onDestroy();
	}
}
