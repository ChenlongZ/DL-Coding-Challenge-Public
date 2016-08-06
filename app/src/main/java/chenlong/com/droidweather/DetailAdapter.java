package chenlong.com.droidweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailAdapter extends BaseAdapter{

    /** for debug */
    private static final String TAG = Constants.PROJECT_NAME + "-" + DetailAdapter.class.getSimpleName();

    private List<String> mData;
    private HashMap<String, String> mContainer;
    private Context mContext;

    public DetailAdapter (Context context, HashMap<String, String> data, boolean isDescription) {
        this.mContext = context;
        this.mContainer = new HashMap<>();
        for (String key : data.keySet()) {
            if (Character.isUpperCase(key.charAt(0))) {
                mContainer.put(key, data.get(key));
            }
        }
        if (isDescription) {
            this.mData = new ArrayList<>(mContainer.keySet());
        } else {
            this.mData = new ArrayList<>(mContainer.values());
        }
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.current_detail_item_layout, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.current_des_text);
        textView.setText(mData.get(position));
        return convertView;
    }
}
