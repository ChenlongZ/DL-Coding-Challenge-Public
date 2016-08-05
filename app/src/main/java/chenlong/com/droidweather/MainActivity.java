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

        // checking network
        if (!Utils.checkConnectivity(this)) {
            setDefaultView();
        }

        // register location listener
        registerLocationListener();
        try {
            Location prevLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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
                    if (Math.abs(location.getLatitude() - prevLocation.getLatitude()) < 0.01 && Math.abs(location.getLongitude() - prevLocation.getLongitude()) < 0.01) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                    } catch (SecurityException error) {
                        Log.e(TAG, "still don't have permission");
                    }

                }
            }
        }
    }


    @Override
    public void acquireGeoInfo(double latitude, double longitude) {

    }

    @Override
    public void acquireCurrentCondition(String secondary) {

    }

    @Override
    public void acquireForecast(String secondary) {

    }

    @Override
    public void onGeoAcquired(String state, String city) {

    }

    @Override
    public void onGeoFailed() {

    }

    @Override
    public void onCurrentConditionAcquired(HashMap<String, String> data) {

    }

    @Override
    public void onCurrentConditionFailed() {

    }

    @Override
    public void onForecastAcquired(List<Map<String, String>> data) {

    }

    @Override
    public void onForecstFailed() {

    }

    @Override
    public void setDefaultView() {

    }
}
