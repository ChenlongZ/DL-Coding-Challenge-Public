package chenlong.com.droidweather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForecastInteractor {

    /** for debug */
    private static final String TAG = Constants.PROJECT_NAME + "-" +
            ForecastInteractor.class.getSimpleName();

    public ForecastPresenter mForecastPresenter;
    public ForecastInteractor(ForecastPresenter forecastPresenter) {
        this.mForecastPresenter = forecastPresenter;
    }

    /**
     * fetching forecast data using endpoint API through wrapped http-async requester
     * @param url               from which we get forecast data
     */
    public void getForecast(String url) {
        WeatherHttpClient.get(url, null, new WeatherHttpClient.BaseCallback() {
            public void onSuccess (int statusCode, JSONObject response) throws JSONException {
                // debug
                Log.i(TAG, "Fetch forecast successful");
                mForecastPresenter.onForecastSuccess(response);
            }
            public void onSuccess (int statusCode, JSONArray response) throws JSONException {

            }
            public void onFailure (int statusCode, Throwable error) {
                // debug
                Log.e(TAG, "Fetch forecast failed");
                mForecastPresenter.onForecastFailed(statusCode);
            }
        });
    }
}
