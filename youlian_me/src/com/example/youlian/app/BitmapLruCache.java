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

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;


public class BitmapLruCache implements ImageCache {
	
	private SoftReference<LruCache<String, Bitmap>> mSoftReference;
	private int maxSize;
	
    public BitmapLruCache(int maxSize) {
    	this.maxSize = maxSize;
    	mSoftReference = new SoftReference<LruCache<String, Bitmap>>(createLruCache());
    }

	private LruCache<String, Bitmap> createLruCache() {
		return new LruCache<String, Bitmap>(maxSize){
   		 @Override
		    protected int sizeOf(String key, Bitmap value) {
		        return value.getRowBytes() * value.getHeight();
		    }
    	};
	}

    @Override
    public Bitmap getBitmap(String url) {
    	if(mSoftReference != null && mSoftReference.get() != null){
    		return mSoftReference.get().get(url);
    	}else{
    		mSoftReference  = new SoftReference<LruCache<String, Bitmap>>(createLruCache());
			return null;
    	}
    }


    @Override
    public void putBitmap(String url, Bitmap bitmap) {
    	if(mSoftReference == null || mSoftReference.get() == null){
    		mSoftReference  = new SoftReference<LruCache<String, Bitmap>>(createLruCache());
    	}
    	mSoftReference.get().put(url, bitmap);
    }
}
