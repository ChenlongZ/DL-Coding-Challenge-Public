package chenlong.com.droidweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView{

    /** for debug */
    private static final String TAG = Constants.PROJECT_NAME + "-" + MainActivity.class.getSimpleName();

    /** activity related member varibles */
    private CurrentWeatherPresenter mCurrentWeatherPresenter;
    private ForecastPresenter mForecastPresenter;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location prevLocation;

    /** binding main content views */
    @Bind(R.id.main_city)
    TextView mainCity;
    @Bind(R.id.main_condition)
    TextView mainCondition;
    @Bind(R.id.main_temp)
    TextView mainTemp;

    /** binding slideup pannel views */
    @Bind(R.id.current_des)
    ListView currentDescriptionList;
    @Bind(R.id.current_value)
    ListView currentValueList;
    @Bind(R.id.forecast)
    GridView forecastGrid;

    private DetailAdapter detailAdapterDesption, detailAdapterValue;
    private ForecastAdapter forecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding views
        ButterKnife.bind(this);

        // init presenters
        mCurrentWeatherPresenter = new CurrentWeatherPresenter(this);
//        mForecastPresenter = new ForecastPresenter(this);

        // checking network
        if (!Utils.checkConnectivity(this)) {
            Toast.makeText(MainActivity.this,
                    "No network... check your connection",
                    Toast.LENGTH_LONG).show();
        }

        // register location listener
        registerLocationListener();
        try {
            Location prevLocation
                    = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            this.acquireGeoInfo(prevLocation.getLatitude(), prevLocation.getLongitude());
        } catch (SecurityException error) {
            Log.e(TAG, error.getMessage());
        }

    }

    /**
     * Register GPS sensor listener, listener will trigger weather info update upon location change
     */
    public void registerLocationListener() {
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "Location info found");
                if (prevLocation != null) {
                    if (Math.abs(location.getLatitude() - prevLocation.getLatitude()) < 0.01
                            && Math.abs(location.getLongitude() - prevLocation.getLongitude()) < 0.01) {
                        // we are too close to last location, skip fetching new data;
                    } else {
                        MainActivity.this.acquireGeoInfo(location.getLatitude(), location.getLongitude());
                    }
                } else {
                    prevLocation = location;
                    MainActivity.this.acquireGeoInfo(location.getLatitude(), location.getLongitude());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
            permissions[1] = Manifest.permission.ACCESS_FINE_LOCATION;
            this.requestPermissions(permissions, 1);
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
    }

    /**
     * request system permission to be granted by user
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                0, 0, mLocationListener);
                    } catch (SecurityException error) {
                        Log.e(TAG, "still don't have permission");
                    }

                }
            }
        }
    }


    @Override
    public void acquireGeoInfo(double latitude, double longitude) {
        mCurrentWeatherPresenter.geoLookup(latitude, longitude);
    }

    @Override
    public void acquireCurrentCondition(String secondary) {
        mCurrentWeatherPresenter.fetchCurrentConditions(secondary);
    }

    @Override
    public void acquireForecast(String secondary) {

    }

    @Override
    public void onGeoAcquired(String state, String city) {
        String new_city = city.replace(' ', '_');
        this.acquireCurrentCondition(state + "/" + new_city + ".json");
        this.acquireForecast(state + "/" + new_city + ".json");
    }

    @Override
    public void onGeoFailed() {
        Toast.makeText(MainActivity.this, "Location lookup failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCurrentConditionAcquired(HashMap<String, String> data) {
        // debug
        Log.d(TAG, "updating current weather view content");

        // setting up main content
        mainCity.setText(data.get("city"));
        mainCondition.setText(data.get("weather"));
        mainTemp.setText((data.get("temp_f")) + " F");

        /** setting up slideup panel
         *  there are two listview, one for description and one for the value
         *  so that there will be two adapter preparing two dataset to the two listview
         *  a single boolean passing to the constructor will tell them apart
         */
        detailAdapterDesption = new DetailAdapter(MainActivity.this, data, true);
        currentDescriptionList.setAdapter(detailAdapterDesption);
        detailAdapterValue = new DetailAdapter(MainActivity.this, data, false);
        currentValueList.setAdapter(detailAdapterValue);
    }

    @Override
    public void onCurrentConditionFailed() {
        Toast.makeText(MainActivity.this, "Fetch condition failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onForecastAcquired(List<Map<String, String>> data) {

    }

    @Override
    public void onForecstFailed() {

    }
}
