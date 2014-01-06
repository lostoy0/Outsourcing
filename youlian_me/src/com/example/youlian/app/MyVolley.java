/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.youlian.app;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;


/**
 * Helper class that is used to provide references to initialized RequestQueue(s) and ImageLoader(s)
 * 
 * @author Ognyan Bankov
 * 
 */
public class MyVolley {
	
    private static final int MAX_IMAGE_CACHE_SIZE  = 5 * 1024 * 1024;
    
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    public static void init(Context context) {
        mRequestQueue = newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(MAX_IMAGE_CACHE_SIZE));
    }


    public static RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//        	mRequestQueue = newRequestQueue(context);
//        } 
        return mRequestQueue;
    }


    /**
     * Returns instance of ImageLoader initialized with {@see FakeImageCache} which effectively means
     * that no memory caching is used. This is useful for images that you know that will be show
     * only once.
     * 
     * @return
     */
    public static ImageLoader getImageLoader() {
//        if (mImageLoader != null) {
//            return mImageLoader;
//        } else {
//        	if(mRequestQueue == null){
//        		mRequestQueue = newRequestQueue(context);
//        	}
//        	mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(MAX_IMAGE_CACHE_SIZE));
//        }
        return mImageLoader;
    }
    
    /** Default on-disk cache directory. */
    private static final String DEFAULT_CACHE_DIR = "volley";

	private static final String TAG = "MyVolley";

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @param stack An {@link HttpStack} to use for the network, or null for default.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
    	File cacheDir = getCacheDir(context);
    	Log.i(TAG, "cacheDir:" + cacheDir.getPath());
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();

            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

//        Network network = new BasicNetwork(stack);
        Network network = new DopoolNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir, 100*1024*1024), network);
        queue.start();

        return queue;
    }


	public static File getCacheDir(Context context) {
		File cacheDir;
		if (android.os.Environment.getExternalStorageState().equals( 
    			android.os.Environment.MEDIA_MOUNTED)){ // sdcard is exits
    		cacheDir = initSdcardDir();
    	} else{
    		cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
    	}
		return cacheDir;
	}

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @return A started {@link RequestQueue} instance.
     */
    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, null);
    }
    
    private static File initSdcardDir() {// sdcard/volley/
		String fileDir = Environment.getExternalStorageDirectory().getPath()+ "/" + DEFAULT_CACHE_DIR + "/";
		ensureDirExists(fileDir);
		return new File(fileDir);

	}
    
    public static boolean ensureDirExists(String dirString) {
		File dir = new File(dirString);
		if (!dir.exists()) {
			return dir.mkdirs();
		} else if (!dir.isDirectory()) {
			return false;
		}
		return true;
	}
}
