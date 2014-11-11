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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup.OnCheckedChangeListener;
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


	// ��¿� �ʿ��� �������� ���� �÷���
	static int red;
	static int green;
	static int blue;
	static double distance_red;
	static double distance_orange;
	static double distance_white;
	static double distance_black;
	static double distance_cyan;
	static double distance_array[];
	String result; // ���� ����� �� �����
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
		setContentView(R.layout.camera_main);
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
	 * ī�޶󿡼� �̹��� ��������
	 */
	private void doTakePhotoAction() {
		/*
		 * ���� �غ��� http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
		 * http://stackoverflow
		 * .com/questions/1050297/how-to-get-the-url-of-the-captured-image
		 * http://www.damonkohler.com/2009/02/android-recipes.html
		 * http://www.firstclown.us/tag/android/
		 */

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// �ӽ÷� ����� ������ ��θ� ����
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
	 * �ٹ����� �̹��� ��������
	 */
	private void doTakeAlbumAction() {
		// �ٹ� ȣ��
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
			// ũ���� �� ������ �̹����� �Ѱ� �޽��ϴ�. �̹����信 �̹����� �����شٰų� �ΰ����� �۾� ���Ŀ�
			// �ӽ� ������ �����մϴ�.
			final Bundle extras = data.getExtras();

			if (extras != null) {
				// �ڸ� �̹����� mFaceBitmap �� ����
				Bitmap photo = extras.getParcelable("data");
				mFaceHeight = photo.getHeight();
				mFaceWidth = photo.getWidth();
				mFaceBitmap = photo;

				// mPhotoImageView.setImageBitmap(photo);
				/*
				 * Intent intent = new Intent(CameraCropActivity.this,
				 * Menu1_Camera.class); intent.putExtra("bm", (Bitmap)
				 * mFaceBitmap); startActivity(intent);
				 */

				FrameLayout f = (FrameLayout) findViewById(R.id.frameimage);
				faceView fv = new faceView(this);
				f.addView(fv);

			}

			// �ӽ� ���� ����
			File f = new File(mImageCaptureUri.getPath());
			if (f.exists()) {
				f.delete();
			}

			break;
		}

		case PICK_FROM_ALBUM: {
			// ������ ó���� ī�޶�� �����Ƿ� �ϴ� break���� �����մϴ�.
			// ���� �ڵ忡���� ���� �ո����� ����� �����Ͻñ� �ٶ��ϴ�.

			mImageCaptureUri = data.getData();
		}

		case PICK_FROM_CAMERA: {
			// �̹����� ������ ������ ���������� �̹��� ũ�⸦ �����մϴ�.
			// ���Ŀ� �̹��� ũ�� ���ø����̼��� ȣ���ϰ� �˴ϴ�.

			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");

			intent.putExtra("outputX", 300);
			intent.putExtra("outputY", 400);
			intent.putExtra("aspectX", 3);
			intent.putExtra("aspectY", 4);
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

		new AlertDialog.Builder(this).setTitle("���ε��� �̹��� ����")
				.setPositiveButton("�����Կ�", cameraListener)
				.setNeutralButton("�ٹ�����", albumListener)
				.setNegativeButton("���", cancelListener).show();
	}

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
		int left_recX, left_recY, right_recX, right_recY; // ���ʺ�,������ �簢���� ��ǥ
		boolean isLeft, isRight; // ����� �簢���� ���õǾ�����

		public faceView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			mFaceBitmap2 = mFaceBitmap.copy(Bitmap.Config.RGB_565, true);
			mFaceBitmap2 = Bitmap.createScaledBitmap(mFaceBitmap2, 450, 650,
					true);

			BitmapFactory.Options BitmapFactoryOptionsbfo = new BitmapFactory.Options();
			BitmapFactoryOptionsbfo.inPreferredConfig = Bitmap.Config.RGB_565;
			bitmapImag = mFaceBitmap2;

			faceObj = new FaceDetector.Face[numberOfFace];
			faceDectect = new FaceDetector(bitmapImag.getWidth(),
					bitmapImag.getHeight(), numberOfFace);

			numberOfFacesDetected = faceDectect.findFaces(bitmapImag, faceObj);

			// �������Լ����� face �Լ� �������� �������ش�.
			Face face = faceObj[0];

			myMidPoint = new PointF();
			face.getMidPoint(myMidPoint);
			eyesDistance = face.eyesDistance();
			// face.getMidPoint(eyescenter);
			// eyesdist = face.eyesDistance();
			// ���ʺ� �簢�� ����Ʈ
			left_recX = (int) (myMidPoint.x - eyesDistance);
			left_recY = (int) (myMidPoint.y + 20);
			// ������ �簢�� ����Ʈ
			right_recX = (int) (myMidPoint.x + eyesDistance / 2);
			right_recY = (int) (myMidPoint.y + 20);

		}

		// ��ġ �̺�Ʈ �Լ�
		public boolean onTouchEvent(android.view.MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			// ��ġ�Է½�
			case MotionEvent.ACTION_DOWN:
				// ���ʻ簢���ΰ��� ȹ�εǸ�(�簢��+5�� ���̾��� Ŭ���ϸ�)
				if (x > left_recX - 5 && x < left_recX + 55
						&& y > left_recY - 5 && y < left_recY + 55) {
					Log.i("log", "x:	" + x + "		y: ," + y + "���� �簢�� ���õǾ����ϴ�.");
					isLeft = true;
					invalidate();
				} else if (x > right_recX - 5 && x < right_recX + 35
						&& y > right_recY - 5 && y < right_recY + 35) {
					Log.i("log", "x:	" + x + "		y: ," + y + "������ �簢�� ���õǾ����ϴ�.");
					isRight = true;
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				if (isLeft) { // ���ʼ��ý� ���ʻ簢�� ��ġ�� �ٲ�
					Log.i("log", "x:	" + x + "		y: ," + y + "���⼭ ������");
					left_recX = (int) x;
					left_recY = (int) y;
					invalidate();
					isLeft = false;
				} else if (isRight) {
					Log.i("log", "x:	" + x + "		y: ," + y + "���⼭ ������(right)");
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

			// �׸� �׷����°�
			canvas.drawRect(left_recX, left_recY, left_recX + 55,
					left_recY + 55, myPaint);
			canvas.drawRect(right_recX, right_recY, right_recX + 55,
					right_recY + 55, myPaint);

			// /////////////////�̹��� �߶� ��Ʈ��
			// ����/////////////////////////////////////////////

			Bitmap rectbitmap_left = Bitmap.createBitmap(bitmapImag, left_recX,
					left_recY, 50, 50);
			Bitmap rectbitmap_right = Bitmap.createBitmap(bitmapImag,
					right_recX, right_recY, 50, 50);

			canvas.drawBitmap(rectbitmap_left, 0, 300, myPaint);
			canvas.drawBitmap(rectbitmap_right, 0, 350, myPaint);

			String result = getClosestColor(rectbitmap_left, rectbitmap_right);
		//	tv_result.setText("result : " + result);
			// //////////////////////////////////////////////////////////////////////////////////////

		}
	}

	// �ΰ��� ��Ʈ�� ��� rgb���� ���ϰ�, �� ���� ���� ����� ������ ������
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
		}// �ΰ��� ��Ʈ�� �� rgb���� ����, ��հ� ����Ѵ�.
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

		Log.i("���� ����� ������", result);
		
		
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