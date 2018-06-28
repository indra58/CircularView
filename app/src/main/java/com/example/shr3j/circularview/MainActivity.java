package com.example.shr3j.circularview;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.shr3j.circularview.circularview.CircularListView;
import com.example.shr3j.circularview.circularview.CircularViewModifier;
import com.example.shr3j.circularview.entities.CountryItemAdapter;

import org.json.JSONException;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final boolean DEBUG = false;
    private int height;
    private CircularListView mCircularListView;
    private CountryItemAdapter mSampleAdapter;
    private String versionName = "";
    private int width;

    protected void onStop() {
        super.onStop();
    }

    protected void onStart() {
        super.onStart();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getSize(size);
        } catch (NoSuchMethodError e) {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        this.width = size.x;
        this.height = size.y;
        try {
            this.versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        this.mCircularListView = (CircularListView) findViewById(R.id.circle_list);
        this.mSampleAdapter = new CountryItemAdapter(this);
        this.mCircularListView.setOnItemClickListener(this);
        this.mCircularListView.setAdapter(this.mSampleAdapter);
        this.mCircularListView.setViewModifier(new CircularViewModifier());
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    protected void onResume() {
        super.onResume();
        try {
            this.mSampleAdapter.reloadCountries();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onPause() {
        super.onPause();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, this.mSampleAdapter.getItem(position).getName() + " clicked", 0).show();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}