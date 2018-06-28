package com.example.shr3j.circularview.entities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shr3j.circularview.R;
import com.example.shr3j.circularview.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CountryItemAdapter extends BaseAdapter {
    private final Context mContext;
    private List<CountryItem> mCountries = new ArrayList();
    private LayoutInflater mInflater;

    private class ViewHolder {
        TextView mAppName;
        CircleImageView mIcon;

        private ViewHolder() {
        }
    }

    public CountryItemAdapter(Context ctx) {
        this.mContext = ctx;
    }

    public void reloadCountries() throws JSONException {
        new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        JSONArray json = new JSONArray(Utilities.loadJSONFromAsset(this.mContext, "countries.json"));
        for (int i = 0; i < json.length(); i++) {
            this.mCountries.add(new CountryItem(json.getJSONObject(i)));
        }
        notifyDataSetChanged();
    }

    public int getCount() {
            return 100;
    }

    public CountryItem getItem(int position) {
        if (this.mCountries.size() > 0) {
            return this.mCountries.get(position % this.mCountries.size());
        }
        return null;
    }

    public long getItemId(int position) {
        CountryItem info = getItem(position);
        if (info == null) {
            return 0;
        }
        return (long) info.hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.circle_list_item, null);
            holder = new ViewHolder();
            holder.mIcon = v.findViewById(R.id.icon);
            holder.mAppName = v.findViewById(R.id.label_app_name);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        /*v.setTag(R.id.key_parent, parent);
        v.setTag(R.id.key_position, Integer.valueOf(position));*/
        CountryItem resolveInfo = getItem(position);
        if (resolveInfo != null) {
            try {
                holder.mIcon.setImageDrawable(Drawable.createFromStream(this.mContext.getAssets().open("images/" + resolveInfo.getPicture()), null));
            } catch (IOException e) {
                e.printStackTrace();
                holder.mIcon.setImageDrawable(null);
            }
            holder.mAppName.setText(resolveInfo.getName());
        } else {
            holder.mIcon.setImageDrawable(null);
            holder.mAppName.setText(null);
        }
        return v;
    }
}
