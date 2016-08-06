package chenlong.com.droidweather;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MainView {

    /**
     * get current location through endpoint API
     * @param latitude              the latitude return from device GPS sensor
     * @param longitude             the longitude return from device GPS sensor
     */
    public void acquireGeoInfo(double latitude, double longitude);

    /**
     * get the current weather condition
     * @param secondary             the secondary url to be appended
     */
    public void acquireCurrentCondition(String secondary);

    /**
     * get the weather forecast
     * @param secondary             the secondary url to be appended
     */
    public void acquireForecast(String secondary);

    /**
     * callback when geo info acquired
     * @param state                 the parameters to passed in secondary url to fetch condition
     * @param city
     */
    public void onGeoAcquired(String state, String city);

    /**
     * callback if can't fetch geo info
     */
    public void onGeoFailed();

    /**
     * callback when current location weather info arrive
     * @param data                  current weather info after formatting
     */
    public void onCurrentConditionAcquired(HashMap<String, String> data);

    /**
     * callback when current weanther failed
     */
    public void onCurrentConditionFailed();

    /**
     * callback when forecast info arrives
     * @param data                  forecast data after fromatting
     */
    public void onForecastAcquired(List<Map<String, String>> data);

    /**
     * callback when forecsting data fetching failed
     */
    public void onForecastFailed();

}
