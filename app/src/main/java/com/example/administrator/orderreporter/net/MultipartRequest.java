package com.example.administrator.orderreporter.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.util.Map;

/**
 * 功能：
 *
 * @modificationHistory 逻辑或功能性重大变更记录
 * @modify by reason:{方法名}:{原因}
 */
public class MultipartRequest extends Request {

    private final Gson mGson = new Gson();

    private final Class mClazz;

    private final Response.Listener mListener;

    private final Map mHeaders;

    private final String mMimeType;

    private final byte[] mMultipartBody;

    public MultipartRequest(int method, String url, Class clazz, Map headers, String mimeType, byte[] multipartBody, Response.Listener listener, Response.ErrorListener errorListener) {

        super(method, url, errorListener);

        this.mClazz = clazz;

        this.mHeaders = headers;

        this.mListener = listener;

        this.mMimeType = mimeType;

        this.mMultipartBody = multipartBody;

    }

    @Override

    public Map getHeaders() throws AuthFailureError {

        return (mHeaders != null) ? mHeaders : super.getHeaders();

    }

    @Override

    public String getBodyContentType() {

        return mMimeType;

    }

    @Override

    public byte[] getBody() throws AuthFailureError {

        return mMultipartBody;

    }

    @Override

    protected Response parseNetworkResponse(NetworkResponse response) {

        try {

            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {

            return Response.error(new ParseError(e));

        }

    }

    @Override
    protected void deliverResponse(Object o) {
        mListener.onResponse(o);
    }


    @Override
    public int compareTo(Object another) {
        return 0;
    }
}
