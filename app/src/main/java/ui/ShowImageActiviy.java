package ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.szpt.hasee.szpt.R;

import base.BaseActivity;

/**
 * Created by hasee on 2016/12/4.
 */

public class ShowImageActiviy extends BaseActivity {
private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_activity);
        imageView= (ImageView) findViewById(R.id.lost_big_image);
        Intent intent=getIntent();
        Glide.with(this).load(intent.getStringExtra("image_url")).fitCenter().into(imageView);
    }
}
