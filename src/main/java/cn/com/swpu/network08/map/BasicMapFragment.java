package cn.com.swpu.network08.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.swpu.network08.MyApplication;
import cn.com.swpu.network08.R;

import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
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
	
	private PopupOverlay myPopupOverlay  = null;
	
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
		GeoPoint gp = new GeoPoint((int)(30.827* 1E6), (int)(104.189* 1E6));//swpu
		basicMapController.setCenter(gp);
		myPopupOverlay = new PopupOverlay(basicMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int arg0) {
			}
		});
		View viewCache = LayoutInflater.from(getActivity()).inflate(R.layout.basic_map_pop_layout, null);
		TextView popText = ((TextView)viewCache.findViewById(R.id.location_tips));
		popText.setText("母校");
		myPopupOverlay.showPopup(getBitmapFromView(popText), gp, 10);
		basicMapListener = new MKMapViewListener() {
			@Override
			public void onMapMoveFinish() {
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				if (mapPoiInfo != null){
					basicMapController.animateTo(mapPoiInfo.geoPt);
				}
			}

			@Override
			public void onGetCurrentMap(Bitmap b) {
			}

			@Override
			public void onMapAnimationFinish() {
			}
			@Override
			public void onMapLoadFinish() {
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
	public static Bitmap getBitmapFromView(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
	}
}