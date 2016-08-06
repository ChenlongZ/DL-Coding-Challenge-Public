package chenlong.com.droidweather;

public final class Constants {

    public static final String APIKEY = "fc6497e18c8e6cb7";
    public static final String PROJECT_NAME = "DroidWeather";

    public static final String BASE_URL = "http://api.wunderground.com/api/" + APIKEY;

    public enum  URLS {
        GEO(BASE_URL + "/geolookup/q/"),
        CURRENT(BASE_URL + "/conditions/q/"),
        FORECAST(BASE_URL + "/forecast/q/"),
        RADAR(BASE_URL + "/radar/");

        private final String mUrl;
        private URLS (String url) {
            mUrl = url;
        }
        public String getUrl() {
            return this.mUrl;
        }
    }
}