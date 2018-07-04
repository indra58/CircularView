package com.example.shr3j.circularview.recyclercircularview;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shr3j.circularview.DragListener;
import com.example.shr3j.circularview.Listener;
import com.example.shr3j.circularview.R;
import com.example.shr3j.circularview.entities.CountryItem;
import com.example.shr3j.circularview.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.VH>
        implements View.OnTouchListener {

    private List<CountryItem> mCountries = new ArrayList();
    private Context mContext;
    private Listener listener;

    public CircularAdapter(Context context)  {
        mContext = context;
        this.listener = (Listener) context;
        new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        try {
            JSONArray json = new JSONArray(Utilities.loadJSONFromAsset(this.mContext, "countries.json"));
            for (int i = 0; i < json.length(); i++) {
                this.mCountries.add(new CountryItem(json.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.circle_list_item, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int i) {
        CountryItem resolveInfo = mCountries.get(i);
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

        holder.wrapper.setTag(i);
        holder.wrapper.setOnTouchListener(this);
        holder.wrapper.setOnDragListener(new DragListener(listener));
    }

    public void reloadCountries() throws JSONException {
        /*new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        JSONArray json = new JSONArray(Utilities.loadJSONFromAsset(this.mContext, "countries.json"));
        for (int i = 0; i < json.length(); i++) {
            this.mCountries.add(new CountryItem(json.getJSONObject(i)));
        }
        notifyDataSetChanged();*/
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mAppName;
        public FrameLayout wrapper;
        public VH(@NonNull View v) {
            super(v);

            mIcon = v.findViewById(R.id.icon);
            mAppName = v.findViewById(R.id.label_app_name);
            wrapper = v.findViewById(R.id.wrapper);
        }


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true;
        }
        return false;
    }

    public List<CountryItem> getList() {
        return mCountries;
    }

    public void updateList(List<CountryItem> list) {
        this.mCountries = list;
    }

    public DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener(listener);
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }
}
