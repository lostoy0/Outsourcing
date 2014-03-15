package com.example.youlian.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class Utilities {
	private static final String TAG = "Utilities";
	 /**
     * Creates an intent used to let the user take a photo from the camera
     */
    public static Intent createPhotoCaptureIntent(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        return intent;
    }
	
	 /**
     * Creates an Intent used to let the user pick a photo from the gallery
     */
    public static Intent createPhotoPickIntent(int w, int h, int aspectX, int aspectY, Uri uri) {
        Intent intent = null;
        if(uri != null) {
        	intent = new Intent("com.android.camera.action.CROP");
        	intent.putExtra("image-path", uri.getPath());
        } else {
        	intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", w);
        intent.putExtra("outputY", h);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        return intent;
    }
    
    /**
     * Convert bitmap to jpeg binary data
     * 
     * @param photo {@link Bitmap} object
     * @return byte array, or null if failed
     */
    public static byte[] bitmap2Bytes(Bitmap photo) {
        if (photo != null) {
            // Try go estimate how much space the icon will take when serialized
            // to avoid unnecessary allocations/copies during the write.
            int size = photo.getWidth() * photo.getHeight() * 4;
            ByteArrayOutputStream out = new ByteArrayOutputStream(size);
            try {
            	photo.compress(Bitmap.CompressFormat.JPEG, 75, out);
                out.flush();
                out.close();
                return out.toByteArray();
            } catch (IOException e) {
            	Log.w(TAG, "unable to compress bitmap to png data");
            }
        }
        
        return null;
    }
    
    public static Bitmap toOvalBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getHeight(),
				bitmap.getWidth(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint();
		paint.setAntiAlias(true); 
		paint.setFilterBitmap(true);
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		canvas.drawOval(rectF, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rectF, paint);
		return output;
	}
}
