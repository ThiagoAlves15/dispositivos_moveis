package com.example.easyrecipes.easyrecipes.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private int width;
    private int height;
    private final int widthPos = 0;
    private final int heightPos = 1;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        setImgDimens(urldisplay);
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        List<Integer> newScale = calculateNewWidthHeight();
        int newWidth = newScale.get(widthPos);
        int newHeight = newScale.get(heightPos);

        bmImage.setImageBitmap(Bitmap.createScaledBitmap(result, newWidth, newHeight, false));
    }

    private void setImgDimens(String imgUrl) {

        String[] split1 = imgUrl.split("mode=");
        width = Integer.parseInt(split1[split1.length-1].split("&")[1].split("=")[1]);
        height = Integer.parseInt(split1[split1.length-1].split("&")[2].split("=")[1]);

    }

    private List<Integer> calculateNewWidthHeight() {

        final int fixedHeight = 210;
        float per = height - fixedHeight;
        per = per*100;
        per = per/height;
        per = per/100;
        int newWidth, newHeight;
        if (width == height) {
            newHeight = newWidth = Math.round(height - (height * per));
        } else {
            newWidth = Math.round(width - (width * per));
            newHeight = Math.round(height - (height * per));
        }
        List<Integer> result = new ArrayList<>();
        result.add(widthPos, newWidth);
        result.add(heightPos, newHeight);

        return result;

    }

}

