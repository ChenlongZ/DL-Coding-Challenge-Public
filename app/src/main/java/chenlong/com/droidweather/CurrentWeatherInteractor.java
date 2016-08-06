package chenlong.com.droidweather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zicdq_000 on 2016/8/5.
 */
public class CurrentWeatherInteractor {

    /** for debug */
    private static final String TAG = Constants.PROJECT_NAME + "-" +
            CurrentWeatherInteractor.class.getSimpleName();

    public CurrentWeatherPresenter currentWeatherPresenter;

    public CurrentWeatherInteractor (CurrentWeatherPresenter currentWeatherPresenter) {
        this.currentWeatherPresenter = currentWeatherPresenter;
    }

    public void getLocation(String url) {
        WeatherHttpClient.get(url, null, new WeatherHttpClient.BaseCallback() {
            public void onSuccess (int statusCode, JSONObject response) throws JSONException {
                // debug
                Log.i(TAG, "Location look up successful");
                currentWeatherPresenter.onGeoLookupSuccess(response);
            }
            public void onSuccess (int statusCode, JSONArray response) throws JSONException {

            }
            public void onFailure (int statusCode, Throwable error) {
                // debug
                Log.e(TAG, "Location lookup failed");
                currentWeatherPresenter.onGeoLookupFailed(statusCode);
            }
        });
    }

    public void getCondition(String url) {
        WeatherHttpClient.get(url, null, new WeatherHttpClient.BaseCallback() {
            public void onSuccess (int statusCode, JSONObject response) throws JSONException {
                // debug
                Log.i(TAG, "Fetch condition successful");
                currentWeatherPresenter.onConditionSuccess(response);
            }
            public void onSuccess (int statusCode, JSONArray response) throws JSONException {

            }
            public void onFailure (int statusCode, Throwable error) {
                // debug
                Log.e(TAG, "Fetch condition failed");
                currentWeatherPresenter.onConditionFailed(statusCode);
            }
        });
    }
}
