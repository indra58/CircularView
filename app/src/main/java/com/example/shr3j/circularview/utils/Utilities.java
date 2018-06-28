package com.example.shr3j.circularview.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Utilities {
    public static String loadJSONFromAsset(Context ctx, String path) {
        try {
            InputStream is = ctx.getAssets().open(path);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}