package chenlong.com.droidweather;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by zicdq_000 on 2016/8/5.
 */
public class CurrentWeatherPresenter {

    /** for debug */
    private static final String TAG = Constants.PROJECT_NAME + "-" + CurrentWeatherPresenter.class.getSimpleName();

    private MainView mMainView;
    private CurrentWeatherInteractor mCurrentWeatherInteractor;
    private HashMap<String, String> mCurrentCondition;

    public CurrentWeatherPresenter(MainView mainView) {
        this.mMainView = mainView;
        this.mCurrentWeatherInteractor = new CurrentWeatherInteractor(this);
    }

    public void geoLookup(double latitude, double longitude) {
        String geoUrl;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.URLS.GEO.getUrl());
        sb.append(latitude);
        sb.append(",");
        sb.append(longitude);
        sb.append(".json");
        geoUrl = sb.toString();
        // debug - check url
        Log.d(TAG, geoUrl);
        mCurrentWeatherInteractor.getLocation(geoUrl);
    }

    public void fetchCurrentConditions(String secondary) {
        String conditionUrl;
        conditionUrl = Constants.URLS.CURRENT.getUrl() + secondary;
        // debug - check url
        Log.d(TAG, conditionUrl);
        mCurrentWeatherInteractor.getCondition(conditionUrl);
    }

    public void onGeoLookupSuccess(JSONObject data) {
        JSONObject location;
        String state = "";
        String city = "";
        try {
            location = data.getJSONObject("location");
            state = location.getString("state");
            city = location.getString("city");
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage());
        } finally {
            // debug
            Log.d(TAG, "Location found: " + state + " " + city);
            mMainView.onGeoAcquired(state, city);
        }
    }

    public void onGeoLookupFailed(int statusCode) {
        Log.e(TAG, "Location lookup failed, status code = " + statusCode);
        mMainView.onGeoFailed();
    }

    public void onConditionSuccess(JSONObject data) {
        mCurrentCondition = new HashMap<>();
        JSONObject condition;
        try {
            condition = data.getJSONObject("current_observation");
            mCurrentCondition.put("city", condition.getJSONObject("display_location").getString("city"));
            mCurrentCondition.put("temp_f", condition.getString("temp_f"));
            mCurrentCondition.put("Humidity", condition.getString("relative_humidity"));
            mCurrentCondition.put("Wind Direction",condition.getString("wind_dir"));
            mCurrentCondition.put("Wind Speed", condition.getString("wind_mph"));
            mCurrentCondition.put("Pressure (mb)", condition.getString("pressure_mb"));
            mCurrentCondition.put("Feels like", condition.getString("feelslike_f"));
            mCurrentCondition.put("UV index", condition.getString("UV"));
            mCurrentCondition.put("Preciptation", condition.getString("precip_today_in"));
            mCurrentCondition.put("weather", condition.getString("weather"));
            mCurrentCondition.put("icon", condition.getString("icon_url"));
        } catch (JSONException error) {
            Log.e(TAG, error.getMessage());
        } finally {
            Log.d(TAG, "parsing condition data successful");
            mMainView.onCurrentConditionAcquired(new HashMap<>(mCurrentCondition));
        }
    }

    public void onConditionFailed(int statusCode) {
        Log.e(TAG, "Condition acquisition failed, status code = " + statusCode);
        mMainView.onCurrentConditionFailed();
    }
}
