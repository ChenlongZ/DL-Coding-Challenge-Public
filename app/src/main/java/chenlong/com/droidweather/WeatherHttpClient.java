package chenlong.com.droidweather;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class WeatherHttpClient {

    /* loggin info */
    public static final String TAG = Constants.PROJECT_NAME + "-" + WeatherHttpClient.class.getSimpleName();

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * async-http get wrapper method
     *
     * @param url           requested end point
     * @param params        the get method parameters, packaged in Hashmap;
     * @param callback      callback to response the http request result;
     */
    public static void get (String url, HashMap<String, String> params, BaseCallback callback) {

        // fetching without extra parameters
        if (params == null) {
            client.get(url, new CustomizedHandler(callback));
        } else {
            RequestParams requestParams = new RequestParams(params);
            client.get(url, requestParams, new CustomizedHandler(callback));
        }
    }

    /**
     * customized to accept JSON response as well as to pass on the response to presenters through callback
     */
    static class CustomizedHandler extends JsonHttpResponseHandler {

        private BaseCallback mCallback;

        public CustomizedHandler (BaseCallback callback) {

            this.mCallback = callback;
        }

        @Override
        public void onSuccess (int statusCode, Header[] headers, JSONArray response) {

            if (mCallback != null) {
                try {
                    this.mCallback.onSuccess (statusCode, response);
                } catch (JSONException error) {
                    Log.e(TAG, error.getMessage());
                }
            }
        }

        @Override
        public void onSuccess (int statusCode, Header[] headers, JSONObject response) {

            if (mCallback != null) {
                try {
                    this.mCallback.onSuccess(statusCode, response);
                } catch (JSONException error) {
                    Log.e(TAG, error.getMessage());
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            if (mCallback != null) {
                this.mCallback.onFailure(statusCode, throwable);
                if (errorResponse != null) {
                    String error = errorResponse.toString();
                    Log.e(TAG, "bad response:" + statusCode + " - " + error);
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            if (mCallback != null) {
                this.mCallback.onFailure(statusCode, throwable);
                if (errorResponse != null) {
                    String error = errorResponse.toString();
                    Log.e(TAG, "bad response:" + statusCode + " - " + error);
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
            if (mCallback != null) {
                this.mCallback.onFailure(statusCode, throwable);
                if (errorResponse != null) {
                    String error = errorResponse.toString();
                    Log.e(TAG, "bad response:" + statusCode + " - " + error);
                }
            }
        }
    }

    /**
     *  the general interface of all network request callback
     */
    interface BaseCallback {

        public void onSuccess (int statusCode, JSONObject response) throws JSONException;
        public void onSuccess (int statusCode, JSONArray response) throws JSONException;
        public void onFailure (int statusCode, Throwable error);
    }

}
