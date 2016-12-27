package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.szpt.hasee.szpt.MyApplication;
import com.szpt.hasee.szpt.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import bean.LostandFoundInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import ui.ShowImageActiviy;

/**
 * Created by hasee on 2016/11/29.
 */

public class LostListviewAdapter extends BaseAdapter {

    private ArrayList<LostandFoundInfo> list;
    private Context context;
    public LostListviewAdapter(ArrayList<LostandFoundInfo> list, Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view ==null){
            view= LayoutInflater.from(context).inflate(R.layout.lost_info_item,null);
            holder=new ViewHolder();
            holder.tv_describe= (TextView) view.findViewById(R.id.tv_describe);
            holder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            holder.tv_time= (TextView) view.findViewById(R.id.tv_time);
            holder.tv_localtion= (TextView) view.findViewById(R.id.tv_localtion);
            holder.tv_puttime= (TextView) view.findViewById(R.id.tv_puttime);
            holder.tv_phone= (TextView) view.findViewById(R.id.tv_phone);
            holder.lost_imavage= (ImageView) view.findViewById(R.id.lost_image);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_describe.setText(list.get(i).getThingsInformation());
        holder.tv_phone.setText("联系方式:"+list.get(i).getThingsPepContactWay());
        holder.tv_title.setText(list.get(i).getThingsName());
        holder.tv_puttime.setText(list.get(i).getPublisTime());
        holder.tv_localtion.setText("在"+list.get(i).getThingsPlace()+"遗失");
        holder.tv_time.setText("遗失时间:"+list.get(i).getThingsTime());
        Glide.with(context).load(list.get(i).getLostPicture().getFileUrl()).fitCenter().into(holder.lost_imavage);
        holder.lost_imavage.setTag(R.id.lost_image, i);
        holder.lost_imavage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowImageActiviy.class);
                intent.putExtra("image_url",list.get(i).getLostPicture().getFileUrl());
                context.startActivity(intent);
            }
        });
    //getBitmap(list.get(i).getLostPicture().getFilename(),holder.lost_imavage);
        return view;
    }
    class ViewHolder{
        TextView tv_title,tv_puttime,tv_phone,tv_time,tv_describe,tv_localtion;
      public  ImageView lost_imavage;
    }



    }
