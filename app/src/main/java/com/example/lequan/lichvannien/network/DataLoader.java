package com.example.lequan.lichvannien.network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.Debug;
import com.example.lequan.lichvannien.utils.NSDialog;
import com.example.lequan.lichvannien.utils.NSDialog.OnDialogClick;
import com.example.lequan.lichvannien.utils.NSLog;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;

import io.fabric.sdk.android.services.network.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class DataLoader {
    public static final String DELETE = "delete";
    public static final String GET = "get";
    public static final String POST = "post";
    public static final String POST_FILE = "postFile";
    public static final String PUT = "put";
    public static final int STATUS_CODE_ERROR_JSON_INVALID = 1004;
    public static final int STATUS_CODE_ERROR_NETWORK = 401;
    public static final int STATUS_CODE_SUCCESS = 200;
    private static final String TAG = DataLoader.class.getSimpleName();
    public static String str;
    public static String[] strArr;
    public static String str2;
    public static StringRequestCallback stringRequestCallback;

    private static class Response {
        public String data;
        public int status;

        public Response(int status, String data) {
            this.status = status;
            this.data = data;
        }

        public String toString() {
            return "Response [status=" + this.status + ", data=" + this.data + "]";
        }
    }

    public static void postAPI(final String method, final boolean showLoading, final BaseActivity activity, String api, StringRequestCallback callback, String... param) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        NSLog.m1451d(TAG, "onStart:  api: " + api);
        if (activity == null || Utils.checkNetworkConnection(activity)) {
            if (showLoading && activity != null) {
                Log.d(TAG, "Start request, show loading");
//                if (!(dialog == null || dialog.isShowing())) {
//                    dialog.show();
//                }
            }
            final String str = api;
            final boolean z = showLoading;
            final BaseActivity baseActivity = activity;
            final String str2 = method;
            final StringRequestCallback stringRequestCallback = callback;
            final String[] strArr = param;
            encryptedPost(method, activity, api, new StringRequestCallback() {

                class C13011 implements OnDialogClick {
                    C13011() {
                    }

                    public void onPositive() {
                        DataLoader.postAPI(str2, z, baseActivity, str, stringRequestCallback, strArr);
                    }

                    public void onNegative() {
                    }
                }

                class C13022 implements OnDialogClick {
                    C13022() {
                    }

                    public void onPositive() {
                        DataLoader.postAPI(str2, z, baseActivity, str, stringRequestCallback, strArr);
                    }

                    public void onNegative() {
                    }
                }

                public void onSuccess(String response) {
                    NSLog.m1451d(DataLoader.TAG, "onSuccess: " + response + " api: " + str);
                    if (z && baseActivity != null && dialog != null && dialog.isShowing()) {
                        dialog.hide();
                    }
                    if (Utils.isJSONValid(response) || str.startsWith("http://apalon1.accu-weather.com/widget/apalon1/weather-data.asp")) {
                        if (stringRequestCallback != null) {
                            stringRequestCallback.onSuccess(response);
                        }
                    } else if (z) {
                        NSDialog.showDialog(baseActivity, baseActivity.getString(R.string.txt_network_error), "Retry", "Close", new C13011());
                        if (stringRequestCallback != null) {
                            stringRequestCallback.onError(1004, response);
                        }
                    }
                }

                public void onError(int statusCode, String responseString) {
                    NSLog.m1452e(DataLoader.TAG, "onError, statusCode: " + statusCode + ", error: " + responseString);
                    if (z && baseActivity != null) {
                        Log.e(DataLoader.TAG, "onError, show loading");
                        if (dialog != null && dialog.isShowing()) {
                            dialog.hide();
                        }
                    }
                    if (stringRequestCallback != null) {
                        stringRequestCallback.onError(statusCode, responseString);
                    }
                    if (baseActivity != null && z) {
                        NSDialog.showDialog(baseActivity, baseActivity.getString(R.string.txt_network_error), "Retry", "Close", new C13022());
                    }
                }
            }, param);
            return;
        }
        callback.onError(STATUS_CODE_ERROR_NETWORK, null);
        if (showLoading) {
            final String dialog2 = api;
            final StringRequestCallback stringRequestCallback2 = callback;
            final String[] strArr2 = param;
            NSDialog.showDialog(activity, activity.getString(R.string.txt_network_error), "Retry", "Close", new OnDialogClick() {
                public void onPositive() {
                    DataLoader.postAPI(method, showLoading, activity, dialog2, stringRequestCallback2, strArr2);
                }

                public void onNegative() {
                }
            });
        }
    }

    public static void showDialogRetry() {
    }

    @SuppressLint("StaticFieldLeak")
    public static void encryptedPost(String method, BaseActivity activity, String url, StringRequestCallback callback, String... param) {
        str = method;
        strArr = param;
        str2 = url;
        stringRequestCallback = callback;
        new RequestTask().execute(url);
    }

    static class RequestTask extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... params) {
            Response response = null;
            List<NameValuePair> nameValuePairs;
            DefaultHttpClient httpClient;
            HttpPost httpPost;
            HttpResponse httpResponse;
            int statusCode;
            Debug.e(str);
            if (str.equals(DataLoader.POST)) {
                try {
                    nameValuePairs = DataLoader.parseParam(strArr);
                    httpClient = DataLoader.buildHttpClient();
                    httpPost = new HttpPost(params[0]);
                    httpPost.setHeader("Content-Type", HttpRequest.CONTENT_TYPE_FORM);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpResponse = httpClient.execute(httpPost);
                    statusCode = httpResponse.getStatusLine().getStatusCode();
                    response = new Response(200, getResponeString(httpResponse));
                } catch (Exception e) {
                    e.printStackTrace();
                    response = new Response(DataLoader.STATUS_CODE_ERROR_NETWORK, e.getMessage());
                }
            } else if (str.equalsIgnoreCase(DataLoader.DELETE)) {
                httpClient = DataLoader.buildHttpClient();
                HttpDelete delete = new HttpDelete();
                try {
                    delete.setURI(new URI(str2));
                    httpResponse = httpClient.execute(delete);
                    statusCode = httpResponse.getStatusLine().getStatusCode();
                    response = new Response(200, getResponeString(httpResponse));
                } catch (URISyntaxException e2) {
                    e2.printStackTrace();
                } catch (IOException e3) {
                    response = new Response(DataLoader.STATUS_CODE_ERROR_NETWORK, e3.getMessage());
                    e3.printStackTrace();
                }
            } else if (str.equalsIgnoreCase(DataLoader.POST_FILE)) {
                try {
                    List parseParam = DataLoader.parseParam(strArr);
                    httpClient = DataLoader.buildHttpClient();
                    httpPost = new HttpPost(params[0]);
                    MultipartEntity reqEntity = new MultipartEntity();
                    if (strArr.length % 2 == 1) {
                        return null;
                    }
                    for (int i = 0; i < strArr.length; i += 2) {
                        if (i == 0) {
                            reqEntity.addPart(strArr[0], new FileBody(new File(strArr[1])));
                        } else {
                            try {
                                reqEntity.addPart(strArr[i], new StringBody(strArr[i + 1]));
                            } catch (UnsupportedEncodingException e4) {
                                e4.printStackTrace();
                            }
                        }
                    }
                    httpPost.setEntity(reqEntity);
                    httpResponse = httpClient.execute(httpPost);
                    response = new Response(200, getResponeString(httpResponse));
                } catch (Exception e5) {
                    e5.printStackTrace();
                    response = new Response(DataLoader.STATUS_CODE_ERROR_NETWORK, e5.getMessage());
                }
            } else if (str.equalsIgnoreCase(DataLoader.GET)) {
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    httpResponse = httpclient.execute(new HttpGet(params[0]));
                    response = new Response(200, getResponeString(httpResponse));
                } catch (Exception e52) {
                    e52.printStackTrace();
                }
            } else if (str.equals(DataLoader.PUT)) {
                try {
                    nameValuePairs = DataLoader.parseParam(strArr);
                    httpClient = DataLoader.buildHttpClient();
                    HttpPut httpPut = new HttpPut(params[0]);
                    httpPut.setHeader("Content-Type", HttpRequest.CONTENT_TYPE_FORM);
                    httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    httpResponse = httpClient.execute(httpPut);
                    statusCode = httpResponse.getStatusLine().getStatusCode();
                    response = new Response(200, getResponeString(httpResponse));
                } catch (Exception e522) {
                    e522.printStackTrace();
                    response = new Response(DataLoader.STATUS_CODE_ERROR_NETWORK, e522.getMessage());
                }
            }
            Debug.e(response.toString());
            return response;
        }

        @Override
        protected void onPostExecute(Response result) {
            super.onPostExecute(result);
            if (result.status == 200) {
                stringRequestCallback.onSuccess(result.data);
            } else {
                stringRequestCallback.onError(result.status, result.data);
            }
        }
    }

    public static List<NameValuePair> parseParam(String... param) {
        List<NameValuePair> mParam = new ArrayList();
        if (param.length % 2 == 1) {
            return null;
        }
        for (int i = 0; i < param.length; i += 2) {
            mParam.add(new BasicNameValuePair(param[i], param[i + 1]));
        }
        return mParam;
    }

    public static String getResponeString(HttpResponse httpResponse) {
        String responseString = "";
        StatusLine statusLine = httpResponse.getStatusLine();
        Debug.e("Fuck phet");
        try {
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else {
                //Closes the connection.
                httpResponse.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e)

        {
            //TODO Handle problems..
        } catch (
                IOException e)

        {
            //TODO Handle problems..
        }
        Debug.e(responseString);
        return responseString;
    }

    public static DefaultHttpClient buildHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setSoTimeout(httpParams, 30000);
        HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        return httpClient;
    }
}
