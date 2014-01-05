/*
 * Copyright (C) 2011 The Android Open Source Project
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

import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 *
 * @param <T> JSON type of response expected
 */
public  class ObjectRequest<T> extends Request<T> {

    private static final String TAG = "ObjectRequest";

	private final Listener<T> mSuccessListener;
    
    private ParseListener<T> mParseListener;
    
    public interface ParseListener<T> {
        /** Called when a response is received. */
        public T parse(String data);
    }
    
    /**
     * Deprecated constructor for a JsonRequest which defaults to GET unless {@link #getPostBody()}
     * or {@link #getPostParams()} is overridden (which defaults to POST).
     *
     * @deprecated Use {@link #JsonRequest(int,  String, Listener, ErrorListener)}.
     */
    public ObjectRequest(String url, Listener<T> listener,
            ErrorListener errorListener,  ParseListener<T> parseListener) {
        this(Method.DEPRECATED_GET_OR_POST, url, listener, errorListener, parseListener);
       
    }
    /**
     * 
     * @param method 请求方法
     * @param url请求url
     * @param successlistener请求成功的listener
     * @param errorListener 请求失败的listener
     * @param parseListener 解析json数据的listener
     */
    public ObjectRequest(int method, String url, Listener<T> successlistener,
            ErrorListener errorListener, ParseListener<T> parseListener) {
        super(method, url, errorListener);
        mSuccessListener = successlistener;
        this.mParseListener = parseListener;
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccessListener.onResponse(response);
    }
    

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response){
    	String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Log.i(TAG, "response:" + parsed);
    	return Response.success(mParseListener.parse(parsed), HttpHeaderParser.parseCacheHeaders(response));
    }

    

    
}
