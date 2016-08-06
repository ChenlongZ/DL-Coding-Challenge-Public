package chenlong.com.droidweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

public class ForecastAdapter extends BaseAdapter{

    /** for debug info */
    public static final String TAG = Constants.PROJECT_NAME + "-" +
            ForecastAdapter.class.getSimpleName();

    /**
     * a A list hold a gridview items, each hashmap contains three entries for a forecast item
     * a forecast item include: time, temperature and a url to the condition image
     */
    public List<Map<String, String>> mForecastData;
    public Context mContext;

    public ForecastAdapter(Context context, List<Map<String, String>> data) {
        this.mForecastData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mForecastData.size();
    }

    @Override
    public Object getItem(int position) {
        return mForecastData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.forecast_item_layout, null);
        }
        TextView forecastDay = (TextView) convertView.findViewById(R.id.forecast_day);
        TextView forecastTemp = (TextView) convertView.findViewById(R.id.forecast_temp);
        ImageView forecastImg = (ImageView) convertView.findViewById(R.id.forecast_img);
        forecastDay.setText(mForecastData.get(position).get("day"));
        forecastTemp.setText((mForecastData.get(position)).get("temp"));
        Glide
                .with(mContext)
                .load(mForecastData.get(position).get("icon_url"))
                .into(forecastImg);
        return convertView;
    }
}
