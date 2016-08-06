package chenlong.com.droidweather;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastPresenter {

    /** for debug */
    private static final String TAG = Constants.PROJECT_NAME + "-" +
            ForecastPresenter.class.getSimpleName();

    private MainView mMainView;
    private ForecastInteractor mForecastInteractor;
    private List<Map<String, String>> mForecastData;

    public ForecastPresenter (MainView mainView) {
        this.mMainView = mainView;
        this.mForecastInteractor = new ForecastInteractor(this);
    }

    /**
     * handle url and pass on request to Interactor
     *
     * @param secondary             secondary url to be append
     */
    public void fetchForecast(String secondary) {
        String forecastUrl;
        forecastUrl = Constants.URLS.FORECAST.getUrl() + secondary;
        mForecastInteractor.getForecast(forecastUrl);
    }

    /**
     *  forecast request callback and organize data to be ready for presenting
     *
     *  @param data                 response data to be handled
     */
    public void onForecastSuccess(JSONObject data) {
        mForecastData = new ArrayList<>(5);
        JSONArray forecast;
        try {
            forecast = data.getJSONObject("forecast")
                    .getJSONObject("txt_forecast")
                    .getJSONArray("forecastday");
            for (int i = 0; i < 5; i++) {
                JSONObject jo = (JSONObject) forecast.get(i);
                Map<String, String> entry = new HashMap<>();
                String temp;
                String[] tmp;
                entry.put("icon_url", jo.getString("icon_url"));
                entry.put("day", jo.getString("title"));
                temp = jo.getString("fcttext");
                tmp = temp.split(" ");
                for (String s : tmp) {
                    if (s.endsWith("F.")) {
                        entry.put("temp", s.substring(0, s.length() - 1));
                    }
                    if (s.endsWith("F")) {
                        entry.put("temp", s.substring(0, s.length()));
                    }
                }
                mForecastData.add(entry);
            }
            Log.d(TAG, "parsing forecasting data successful");
            Log.d(TAG, mForecastData.toString());
            mMainView.onForecastAcquired(mForecastData);
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage());
        }
    }

    /**
     * callback on forecast fetch failure
     *
     * @param statusCode
     */
    public void onForecastFailed(int statusCode) {
        Log.e(TAG, "Acquiring forecast failed, status code = " + statusCode);
        mMainView.onForecastFailed();
    }
}
