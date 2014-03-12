package cn.com.swpu.network08.util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

/**
 * 
 * @author hq
 *
 */
public class CameraUtil {
	static CameraUtil m_instance;
	static final String TAG = "CameraUtil";
	public static final int OPEN_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;
	public static final int OPEN_BACK = Camera.CameraInfo.CAMERA_FACING_BACK;
	
	private Camera mCamera;
	private WindowManager mWindowManager;
	private ContentResolver mContentResolver;
	private int mFacing = 0;
	private Bitmap mCurPicture = null;
	private int orientionOfCamera = 0;
	private int mCamerIndex = 0;
	
	public static CameraUtil instance(int facing)
	{
		if(m_instance == null)
		{
			m_instance = new CameraUtil(facing);
		}
		return m_instance;
	}

	private CameraUtil(int facing)
	{
		mFacing = facing;
		open(facing);
	}
	
	public void setWindowManager(WindowManager windowManager)
	{
		mWindowManager = windowManager;
	}
	
	public void setContentResolver(ContentResolver contentResolver)
	{
		mContentResolver = contentResolver;
	}
	
	public void initialPreview(SurfaceHolder holder)
	{
		try
		{
			if(mCamera != null)
			{
				mCamera.setPreviewDisplay(holder);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void startPreview(int width, int height)
	{
		if(mCamera != null)
		{
			Camera.Parameters parameters = mCamera.getParameters();
			Display display = mWindowManager.getDefaultDisplay();

	        if(display.getRotation() == Surface.ROTATION_0)
	        {
	            parameters.setPreviewSize(height, width);                           
	            mCamera.setDisplayOrientation(90);
	        }

	        if(display.getRotation() == Surface.ROTATION_90)
	        {
	            parameters.setPreviewSize(width, height);                           
	        }

	        if(display.getRotation() == Surface.ROTATION_180)
	        {
	            parameters.setPreviewSize(height, width);               
	        }

	        if(display.getRotation() == Surface.ROTATION_270)
	        {
	            parameters.setPreviewSize(width, height);
	            mCamera.setDisplayOrientation(180);
	        }

        	//parameters.setPreviewSize(width, height);
        	mCamera.setParameters(parameters);
        	mCamera.startPreview();
		}
	}
	
	public void stopPreview()
	{
		if(mCamera != null)
		{
			mCamera.stopPreview();
		}
	}
	
	public void takePicture()
	{
		if(mCamera != null)
			mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
	}
	
	public boolean hasFrontCamera(){
		int cameraCnt = Camera.getNumberOfCameras();
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

		for (int i = 0; i < cameraCnt; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				return true;
			}
		}
		return false;
	}
	
	public void open(int facing)
	{
		if((mCamera != null && facing != mFacing)){
			release();
			
		}else if (mCamera == null){
			
		}
		else {
			return;
		}
		mFacing = facing;
		
		int cameraCnt = Camera.getNumberOfCameras();
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

		for (int i = 0; i < cameraCnt; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == facing) {
				try {
					mCamera = Camera.open(i);
					mCamerIndex = i;
					setCameraDisplayOrientation();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void release()
	{
		if(mCamera != null)
		{
			mCamera.release();
			mCamera = null;
		}
	}
	
	public Bitmap getCurPicture(){
		return mCurPicture;
	}
	
	public void setCameraDisplayOrientation() {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(mCamerIndex, info);

		int rotation = mWindowManager.getDefaultDisplay().getRotation();
		int degree = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degree = 0;
			break;
		case Surface.ROTATION_90:
			degree = 90;
			break;
		case Surface.ROTATION_180:
			degree = 180;
			break;
		case Surface.ROTATION_270:
			degree = 270;
			break;
		}

		orientionOfCamera = info.orientation;
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degree) % 360;
			result = (360 - result) % 360;
		} else {
			result = (info.orientation - degree + 360) % 360;
		}
		mCamera.setDisplayOrientation(result);
	}

	
	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			try
			{
				BitmapFactory.Options options = new BitmapFactory.Options();
				Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length,
						options);
				int width = bitmap1.getWidth();
				int height = bitmap1.getHeight();
				Matrix matrix = new Matrix();
				switch (orientionOfCamera) {
				case 0:
					matrix.postRotate(0.0f, width / 2, height / 2);
					mCurPicture = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
					break;
				case 90:
					matrix.postRotate(-270.0f, height / 2, width / 2);
					mCurPicture = Bitmap.createBitmap(height, width, Bitmap.Config.RGB_565);
					break;
				case 180:

					matrix.postRotate(-180.0f, width / 2, height / 2);
					mCurPicture = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
					break;
				case 270:

					matrix.postRotate(-90.0f, height / 2, width / 2);
					mCurPicture = Bitmap.createBitmap(height, width, Bitmap.Config.RGB_565);
					break;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				camera.startPreview();
			}
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};
}
