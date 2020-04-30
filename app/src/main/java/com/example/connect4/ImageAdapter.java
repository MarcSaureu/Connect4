package com.example.connect4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.Serializable;

public class ImageAdapter extends BaseAdapter implements Serializable {
    private Context context;
    private Integer[] myimages;
    private int num;

    public ImageAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return myimages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(myimages[position]);
        return imageView;
    }

    //Construccio del grid
    public void setGrid(int i) {
        this.num = i;
        myimages = new Integer[i * i];
        for (int x = 0; x < i * i; x++) {
            myimages[x] = R.drawable.white_circle;
        }
    }

    public void setChip(int chip, int row, int col) {
        int position = num * (-(row - (num - 1))) + col;
        myimages[position] = chip;
    }
}

