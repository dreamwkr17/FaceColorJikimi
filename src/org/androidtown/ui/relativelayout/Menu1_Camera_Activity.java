package org.androidtown.ui.relativelayout;

import java.util.Arrays;
import java.io.File;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.widget.RadioGroup;
import android.widget.EditText;





public class Menu1_Camera_Activity extends Activity implements OnClickListener {
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	String resultRGB;
	private Uri mImageCaptureUri;
	private Button mButton;
	private Button mButton2;


	// 출력에 필요한 변수들을 위로 올렸음
	static int red;
	static int green;
	static int blue;
	static double distance_red;
	static double distance_orange;
	static double distance_white;
	static double distance_black;
	static double distance_cyan;
	static double distance_array[];
	String result; // 가장 가까운 색 결과값
	// ================================================================//
	int mFaceWidth;
	int mFaceHeight;
	Bitmap mFaceBitmap;

	float eyesDistance;
	int numberOfFacesDetected;

	Bitmap mFaceBitmap2;
	TextView tv_result;
	
	// ================================================================//

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu1_camera);
		 ActionBar actionBar = getActionBar();
	        actionBar.hide();
		
		
		mButton = (Button) findViewById(R.id.button);
		mButton.setOnClickListener(this);
		
		
		Button btn_goResult = (Button) findViewById(R.id.button2);
		btn_goResult.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setContentView(R.layout.red_info);
					
					
				}
			});

		

	
		
		
	}

	/**
	 * 카메라에서 이미지 가져오기
	 */
	private void doTakePhotoAction() {
	
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 임시로 사용할 파일의 경로를 생성
		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}

	/**
	 * 앨범에서 이미지 가져오기
	 */
	private void doTakeAlbumAction() {
		// 앨범 호출
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case CROP_FROM_CAMERA: {
			// 크롭이 된 이후의 이미지를 넘겨 받습니다. 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
			// 임시 파일을 삭제합니다.
			final Bundle extras = data.getExtras();

			if (extras != null) {
				// 자른 이미지를 mFaceBitmap 에 저장
				Bitmap photo = extras.getParcelable("data");
				mFaceHeight = photo.getHeight();
				mFaceWidth = photo.getWidth();
				mFaceBitmap = photo;

				//xml파일에 정의한 frame layout 
				//! 가운데로 정렬이 안됨!! ㅜㅜ 
				RelativeLayout f = (RelativeLayout) findViewById(R.id.frameimage);
				
				//새로운 canvas 를 생성한다 
				faceView fv = new faceView(this);
				
				
				//frame layout에 더함
				f.addView(fv);

			}

			// 임시 파일 삭제
			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			mImageCaptureUri = data.getData();
		}

		case PICK_FROM_CAMERA: {
			// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
			// 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 300);
			intent.putExtra("outputY", 300);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", false);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CROP_FROM_CAMERA);

			break;
		}
		}
	}

	@Override
	public void onClick(View v) {
		DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doTakePhotoAction();
			}
		};

		DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doTakeAlbumAction();
			}
		};

		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};

		new AlertDialog.Builder(this).setTitle("업로드할 이미지 선택")
				.setPositiveButton("사진촬영", cameraListener)
				.setNeutralButton("앨범선택", albumListener)
				.setNegativeButton("취소", cancelListener).show();
	}

	//custom View 
	public class faceView extends View {
		// numberOfFace is for how many faces you want to find
		private int numberOfFace = 1;
		private FaceDetector faceDectect;
		private FaceDetector.Face[] faceObj;
		float eyesDistance;
		int numberOfFacesDetected;
		Bitmap bitmapImag;
		PointF myMidPoint;
		PointF eyescenter;
		int[] fpx = null;
		int[] fpy = null;
		float eyesdist = 0.0f;
		int left_recX, left_recY, right_recX, right_recY; // 왼쪽볼,오른쪽 사각형의 좌표
		boolean isLeft, isRight; // 어느쪽 사각형인 선택되었는지

		public faceView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			mFaceBitmap2 = mFaceBitmap.copy(Bitmap.Config.RGB_565, true);
			mFaceBitmap2 = Bitmap.createScaledBitmap(mFaceBitmap2, 400, 400,
					true);

			BitmapFactory.Options BitmapFactoryOptionsbfo = new BitmapFactory.Options();
			BitmapFactoryOptionsbfo.inPreferredConfig = Bitmap.Config.RGB_565;
			bitmapImag = mFaceBitmap2;

			
			faceObj = new FaceDetector.Face[numberOfFace];
			faceDectect = new FaceDetector(bitmapImag.getWidth(),
					bitmapImag.getHeight(), numberOfFace);

			if(faceDectect.findFaces(bitmapImag, faceObj) != 0){
			numberOfFacesDetected = faceDectect.findFaces(bitmapImag, faceObj);
			Log.i("face", "face를 찾음 넘버오브 페이스 디텍티드는  "+numberOfFacesDetected);
			Log.i("face", "face를 찾 ");
			Face face = faceObj[0];
			
			myMidPoint = new PointF();
			face.getMidPoint(myMidPoint);
			eyesDistance = face.eyesDistance();
			 //dd
			
			//test2 + 카메라 체인지중 
			
			left_recX = (int) (myMidPoint.x - eyesDistance);
			left_recY = (int) (myMidPoint.y + 20);
			// 오른볼 사각형 디폴트
			right_recX = (int) (myMidPoint.x + eyesDistance / 2);
			right_recY = (int) (myMidPoint.y + 20);
			Log.i("G",left_recX+"    "+left_recY+"	"+right_recX+"	"+right_recY);

		}else{
			left_recX = 108;
			left_recY = 217;
			right_recX = 256;
			right_recY = 217;
			
			}
	
		}

		// 터치 이벤트 함수
		public boolean onTouchEvent(android.view.MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			// 터치입력시
			case MotionEvent.ACTION_DOWN:
				// 왼쪽사각형인것이 획인되면(사각형+5의 넓이안을 클릭하면)
				if (x > left_recX - 5 && x < left_recX + 55
						&& y > left_recY - 5 && y < left_recY + 55) {
					Log.i("log", "x:	" + x + "		y: ," + y + "왼쪽 사각형 선택되었습니다.");
					isLeft = true;
					invalidate();
				} else if (x > right_recX - 5 && x < right_recX + 35
						&& y > right_recY - 5 && y < right_recY + 35) {
					Log.i("log", "x:	" + x + "		y: ," + y + "오른쪽 사각형 선택되었습니다.");
					isRight = true;
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				if (isLeft) { // 왼쪽선택시 왼쪽사각형 위치를 바꿈
					Log.i("log", "x:	" + x + "		y: ," + y + "여기서 놓았음");
					left_recX = (int) x;
					left_recY = (int) y;
					invalidate();
					isLeft = false;
				} else if (isRight) {
					Log.i("log", "x:	" + x + "		y: ," + y + "여기서 놓았음(right)");
					right_recX = (int) x;
					right_recY = (int) y;
					invalidate();
					isRight = false;
				}

				break;
			}
			return true;
		};

		@Override
		public void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub

			canvas.drawBitmap(bitmapImag, 0, 0, null);

			Paint myPaint = new Paint();
			myPaint.setColor(Color.YELLOW);
			myPaint.setStyle(Paint.Style.STROKE);
			myPaint.setStrokeWidth(2);

			// 네모가 그려지는곳
			canvas.drawRect(left_recX, left_recY, left_recX + 55,
					left_recY + 55, myPaint);
			canvas.drawRect(right_recX, right_recY, right_recX + 55,
					right_recY + 55, myPaint);

			// /////////////////이미지 잘라서 비트맵
			// 생성/////////////////////////////////////////////

			Bitmap rectbitmap_left = Bitmap.createBitmap(bitmapImag, left_recX,
					left_recY, 50, 50);
			Bitmap rectbitmap_right = Bitmap.createBitmap(bitmapImag,
					right_recX, right_recY, 50, 50);

			canvas.drawBitmap(rectbitmap_left, 0, 300, myPaint);
			canvas.drawBitmap(rectbitmap_right, 0, 350, myPaint);

			String result = getClosestColor(rectbitmap_left, rectbitmap_right);
		
		}
	}

	// 두개의 비트맵 평균 rgb값을 구하고, 그 값에 가장 가까운 색깔을 리턴함
	String getClosestColor(Bitmap bm1, Bitmap bm2) {

		int redColors = 0;
		int greenColors = 0;
		int blueColors = 0;
		int pixelCount = 0;
		Bitmap[] Arrbm = { bm1, bm2 };
		int i;
		for (i = 0; i < 2; i++) {
			for (int y = 0; y < Arrbm[i].getHeight(); y++) {
				for (int x = 0; x < Arrbm[i].getWidth(); x++) {
					int c = Arrbm[i].getPixel(x, y);
					pixelCount++;
					redColors += Color.red(c);
					greenColors += Color.green(c);
					blueColors += Color.blue(c);
				}
			}
		}// 두개의 비트맵 의 rgb값을 추출, 평균값 계산한다.
		red = (redColors / pixelCount);
		green = (greenColors / pixelCount);
		blue = (blueColors / pixelCount);

		Log.i("rgb average red :::::::", Integer.toString(red));
		Log.i("rgb average green :::::::", Integer.toString(green));
		Log.i("rgb average blue :::::::", Integer.toString(blue));

		
		
		 
		
		double ave_distance_white = white_distance_cal(Menu1_Activity.ave_red, Menu1_Activity.ave_green, Menu1_Activity.ave_blue);
		double ave_distance_red =red_distance_cal(Menu1_Activity.ave_red, Menu1_Activity.ave_green, Menu1_Activity.ave_blue);
		double ave_distance_orange = orange_distance_cal(Menu1_Activity.ave_red,Menu1_Activity. ave_green, Menu1_Activity.ave_blue);
		double ave_distance_black = black_distance_cal(Menu1_Activity.ave_red, Menu1_Activity.ave_green, Menu1_Activity.ave_blue);
		double ave_distance_cyan = cyan_distance_cal(Menu1_Activity.ave_red, Menu1_Activity.ave_green, Menu1_Activity.ave_blue);

		distance_red = red_distance_cal(red,green,blue) - ave_distance_red ;
		distance_orange = orange_distance_cal(red,green,blue) - ave_distance_orange ;
		distance_white = white_distance_cal(red,green,blue) - ave_distance_white ;
		distance_black = black_distance_cal(red,green,blue) - ave_distance_black ;
		distance_cyan = cyan_distance_cal(red,green,blue) - ave_distance_cyan ;

		double distance_array[] = {Math.abs(distance_red), Math.abs(distance_orange), Math.abs(distance_white), Math.abs(distance_black),  Math.abs(distance_cyan) };

		
		
		Arrays.sort(distance_array);
		Log.i("min_dstance", Double.toString(distance_array[0]));
		Log.i("Menu1_Activity.ave_red", Double.toString(Menu1_Activity.ave_red));
		
		if (distance_array[0] == distance_red)
			result = "red";
		else if (distance_array[0] == distance_orange)
			result = "orange";

		else if (distance_array[0] == distance_white)
			result = "white";
		else if (distance_array[0] == distance_black)
			result = "black";
		else
			result = "cyan";

		Log.i("가장 가까운 색깔은", result);
		
		
		return result;
	   	
	
	}
	
	
	public double red_distance_cal(int r, int g, int b)
	   {
		   return ((255 - r) * (255 - r)) + (g * g) + (b * b);
	   }
	public double orange_distance_cal(int r, int g, int b)
	   {
		   return ((255 - r) * (255 - r)) + ((255 - g) * (255 - g)) + (b*b);
	   }
	public double white_distance_cal(int r, int g, int b)
	   {
		   return ((255 - r) * (255 - r)) + ((255 - g) * (255 - g)) + ((255 - b) * (255 - b));
	   }
	public double black_distance_cal(int r, int g, int b)
	   {
		   return (r * r) + (g * g) + (b * b);
	   }
	public double cyan_distance_cal(int r, int g, int b)
	   {
		   return (r * r) + ((255 - g) * (255 - g)) + (255-b) *(255-b);
	   }
	
	
		
}