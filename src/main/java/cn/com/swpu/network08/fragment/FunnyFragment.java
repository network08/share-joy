package cn.com.swpu.network08.fragment;

import java.util.Date;

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
import android.widget.ImageView;
import cn.com.swpu.network08.R;
import cn.com.swpu.network08.db.ImageSqliteService;
import cn.com.swpu.network08.model.Image;
import cn.com.swpu.network08.util.DateUtil;
import cn.com.swpu.network08.util.ImageUtil;

/**
 * @author xkk
 *
 */
public class FunnyFragment extends Fragment implements OnClickListener{
	private ImageButton  imagePicBtn;
	private ImageButton  imageCamBtn;
	private ImageView imgView;
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageSqliteService imageSqliteService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.image_navigate_layout,
				container, false);
		imgView = (ImageView)messageLayout.findViewById(R.id.fun_image_test_view);
		imagePicBtn = (ImageButton)messageLayout.findViewById(R.id.img_navi_pic_btn);
		imagePicBtn.setOnClickListener(this);
		imageCamBtn = (ImageButton)messageLayout.findViewById(R.id.img_navi_cam_btn);
		imageCamBtn.setOnClickListener(this);
		imageSqliteService = new ImageSqliteService(getActivity());
		return messageLayout;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_navi_pic_btn:
			startActivityForResult(new Intent(Intent.ACTION_PICK, 
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
			break;
		case R.id.img_navi_cam_btn:
			imageSqliteService.readTestFunc();
			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
			long i = imageSqliteService.insert(new Image("hehe", ImageUtil.BitMap2Byte(bitmap), new Date()));
			imgView.setImageBitmap(bitmap);
		}
	}

}