package cn.com.swpu.network08.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import cn.com.swpu.network08.R;
import cn.com.swpu.network08.image.ImageSimpleHandlePage;

/**
 * @author xkk
 *
 */
public class FunnyFragment extends Fragment implements OnClickListener{
	private ImageButton  imagePicBtn;
	private ImageButton  imageCamBtn;
	private static int CAMERA_OPTION = 1;
	private static int PIC_OPTION = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.image_navigate_layout,
				container, false);
		imagePicBtn = (ImageButton)messageLayout.findViewById(R.id.img_navi_pic_btn);
		imagePicBtn.setOnClickListener(this);
		imageCamBtn = (ImageButton)messageLayout.findViewById(R.id.img_navi_cam_btn);
		imageCamBtn.setOnClickListener(this);
		return messageLayout;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_navi_pic_btn:
			startActivity(new Intent(getActivity(), ImageSimpleHandlePage.class));
//			startActivityForResult(new Intent(Intent.ACTION_PICK, 
//					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PIC_OPTION);
			break;
		case R.id.img_navi_cam_btn:
			startActivity(new Intent(getActivity(), ImageSimpleHandlePage.class));
//			startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_OPTION);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitmap = null;
		if(resultCode == Activity.RESULT_OK && null != data){
			if (requestCode == PIC_OPTION) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getActivity().getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				bitmap = BitmapFactory.decodeFile(picturePath);
			}else if(requestCode == CAMERA_OPTION) {
				Bundle bundle=data.getExtras();
				if (bundle!=null) {
					bitmap = (Bitmap) bundle.get("data");
				}
			}
			//TODO:将图片推送至图片处理页
		}
	}
}