package com.example.youlian.baidumap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.example.youlian.R;
import com.example.youlian.app.YouLianApp;


/**
 * 
 * @author zhangzhibing
 * 
 */
public class BaiduMapActivity extends MapActivity {
	/** Called when the activity is first created. */
//	Button locBn;
	static MapView mMapView;
	static View mPopView = null;	// ���markʱ����������View
	private double dLong,dLat;
//	BMapManager mBMapMan = null;
	private MapController controller;
	
	private TextView storeName;
	
	private String nameStr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.baidu_map);
		
		dLong = getIntent().getDoubleExtra("dLong", 0);
		dLat = getIntent().getDoubleExtra("dLat", 0);
		nameStr =getIntent().getStringExtra("storeName");
		YouLianApp app = (YouLianApp)this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new YouLianApp.MyGeneralListener());
		}
		app.mBMapMan.start();
        // ���ʹ�õ�ͼSDK�����ʼ����ͼActivity
		super.initMapActivity(app.mBMapMan);
		
		Bitmap posBitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.map_point_a);
		
		Bitmap posBitmap2 = BitmapFactory
		.decodeResource(getResources(), R.drawable.map_point_b);

		// ��ȡ�����ϵ�MapView����
		mMapView = (MapView) findViewById(R.id.bmapsView);
		// ��ȡcontroller����
		controller = mMapView.getController();
		// ����ϵͳ���õ����Ű�ť
		mMapView.setBuiltInZoomControls(true);
		
		// �������markʱ�ĵ�������
		mPopView=super.getLayoutInflater().inflate(R.layout.map_popview, null);
		storeName = (TextView)mPopView.findViewById(R.id.pt_text);
		mMapView.addView( mPopView,
                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                		null, MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);
		//���뾭γ�� ��λ
		updateMapView(dLong, dLat,posBitmap,113.39, 23.14,posBitmap2);

	}
//	 Override���·���,����API:
	@Override
	protected void onPause() {
		YouLianApp app = (YouLianApp)this.getApplication();
		app.mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		YouLianApp app = (YouLianApp)this.getApplication();
		app.mBMapMan.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	// ��ݾ��ȡ�γ�Ƚ�MapView��λ��ָ���ص�ķ���
	private void updateMapView(double lng, double lat,Bitmap posBitmap,
			double lng2, double lat2,Bitmap posBitmap2) {
		// ����γ����Ϣ��װ��GeoPoint����
		GeoPoint gp = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		
		GeoPoint gp2 = new GeoPoint((int) (lat2 * 1E6), (int) (lng2 * 1E6));
		// ������ʾ�Ŵ���С��ť
		mMapView.displayZoomControls(true);
		// ����ͼ�ƶ���ָ���ĵ���λ��
		controller.animateTo(gp);
		// ���õ�ͼ����
		controller.setCenter(gp);
		controller.setZoom(15);

		// ���MapView��ԭ�е�Overlay����
//		List<Overlay> ol = mMapView.getOverlays();
		// ���ԭ�е�Overlay����
//		ol.clear();
		// ���һ���µ�OverLay����
//		ol.add(new DemoOverlay(this,gp, posBitmap));
//		ol.add(new DemoOverlay(this,gp2, posBitmap2));
		
		Drawable marker1 = getResources().getDrawable(R.drawable.map_point_a);  //�õ���Ҫ���ڵ�ͼ�ϵ���Դ
		marker1.setBounds(0, 0, marker1.getIntrinsicWidth(), marker1
				.getIntrinsicHeight());   //Ϊmaker����λ�úͱ߽�
		Drawable marker2 = getResources().getDrawable(R.drawable.map_point_b);  //�õ���Ҫ���ڵ�ͼ�ϵ���Դ
		marker2.setBounds(0, 0, marker2.getIntrinsicWidth(), marker2
				.getIntrinsicHeight());   //Ϊmaker����λ�úͱ߽�
		
		OverItemT item1 = new OverItemT(marker1, this,gp);
		OverItemT item2 = new OverItemT(marker2, this,gp2);
		mMapView.getOverlays().add(item1); //���ItemizedOverlayʵ��mMapView
		mMapView.getOverlays().add(item2); //���ItemizedOverlayʵ��mMapView
		
	}
	
	class DemoOverlay extends Overlay {
		Context mContext; // ������
		GeoPoint gp;
		Bitmap pic;
		 
		public DemoOverlay() {
		super();
		}
		 
		public DemoOverlay(Context c,GeoPoint gp,Bitmap pic) {
		this();
		mContext = c;
		this.gp = gp;
		this.pic = pic;
		}
		 
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		 
		Projection proj = mapView.getProjection(); // ��ȡͶӰ����
		Point mPoint = new Point();
		proj.toPixels(gp, mPoint); // ����γ��ת�����ֻ���Ļ�ϵ�����,�洢��Point������
		Paint mPaint = new Paint();
		// mPaint.setColor(Color.RED); //����Ϊ��ɫ
		 
		canvas.drawBitmap(pic, mPoint.x, mPoint.y, mPaint); // ��ͼ
		super.draw(canvas, mapView, shadow);
		}
		 
		@Override
		public boolean onTap(GeoPoint gp, MapView mapView) {
//			setFocus(mGeoList.get(i));
			BaiduMapActivity.mMapView.updateViewLayout( BaiduMapActivity.mPopView,
	                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                		gp, MapView.LayoutParams.BOTTOM_CENTER));
			BaiduMapActivity.mPopView.setVisibility(View.VISIBLE);
			storeName.setText(nameStr);
		return true;
		}
		
		// ���?����¼�

//		@Override
//		public boolean onTap(GeoPoint arg0, MapView arg1) {
//			// TODO Auto-generated method stub
//			// ��ȥ����������
//			ItemizedOverlayDemo.mPopView.setVisibility(View.GONE);
//			return super.onTap(arg0, arg1);
//		}
		
	}
	class OverItemT extends ItemizedOverlay<OverlayItem> {

		private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		private Drawable marker;
		private Context mContext;

		private double mLat1 = 39.90923; // point1γ��
		private double mLon1 = 116.357428; // point1����

		private double mLat2 = 39.90923;
		private double mLon2 = 116.397428;

		private double mLat3 = 39.90923;
		private double mLon3 = 116.437428;

		public OverItemT(Drawable marker, Context context,GeoPoint gp) {
			super(boundCenterBottom(marker));

			this.marker = marker;
			this.mContext = context;

			// �ø�ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
//			GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
//			GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
//			GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));

			// ����OverlayItem�������������Ϊ��item��λ�ã������ı�������Ƭ��
			mGeoList.add(new OverlayItem(gp, "", ""));
//			mGeoList.add(new OverlayItem(p2, "P2", "point2"));
//			mGeoList.add(new OverlayItem(p3, "P3", "point3"));		
			populate();  //createItem(int)��������item��һ��������ݣ��ڵ�������ǰ�����ȵ����������
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			// Projection�ӿ�������Ļ�������;�γ�����֮��ı任
			Projection projection = mapView.getProjection(); 
			for (int index = size() - 1; index >= 0; index--) { // ����mGeoList
				OverlayItem overLayItem = getItem(index); // �õ��������item

				String title = overLayItem.getTitle();
				// �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ�������
				Point point = projection.toPixels(overLayItem.getPoint(), null); 

				// ���ڴ˴������Ļ��ƴ���
//				Paint paintText = new Paint();
//				paintText.setColor(Color.BLUE);
//				paintText.setTextSize(15);
//				canvas.drawText(title, point.x-30, point.y, paintText); // �����ı�
			}

			super.draw(canvas, mapView, shadow);
			//����һ��drawable�߽磬ʹ�ã�0��0�������drawable�ײ����һ�����ĵ�һ������
			boundCenterBottom(marker);
		}

		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return mGeoList.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return mGeoList.size();
		}
		@Override
		// ���?����¼�
		protected boolean onTap(int i) {
			setFocus(mGeoList.get(i));
			// ��������λ��,��ʹ֮��ʾ
			GeoPoint pt = mGeoList.get(i).getPoint();
			BaiduMapActivity.mMapView.updateViewLayout( BaiduMapActivity.mPopView,
	                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                		pt, MapView.LayoutParams.BOTTOM_CENTER));
			BaiduMapActivity.mPopView.setVisibility(View.VISIBLE);
			storeName.setText(nameStr);
//			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
//					Toast.LENGTH_SHORT).show();
			return true;
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			// TODO Auto-generated method stub
			// ��ȥ����������
			BaiduMapActivity.mPopView.setVisibility(View.GONE);
			return super.onTap(arg0, arg1);
		}
	}
}