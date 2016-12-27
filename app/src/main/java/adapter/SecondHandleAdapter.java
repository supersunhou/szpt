package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.szpt.hasee.szpt.R;

import java.util.ArrayList;
import java.util.List;

import bean.ScondHandle;

/**
 * Created by CGS on 2016/12/25.
 */

public class SecondHandleAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<ScondHandle> mlist=new ArrayList<ScondHandle>();
   // private int []guideIamge={R.drawable.danche1,R.drawable.danche2,R.drawable.danche3,R.drawable.danche4};
    public SecondHandleAdapter(){
        super();
    }
    public SecondHandleAdapter(Context context,List<ScondHandle> mlist) {
        this.context=context;
        this.mlist=mlist;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  holder=null;
        if(convertView==null){

            convertView=View.inflate(context, R.layout.secondhandlelist_item, null);
            //TextView tv=(TextView) item.findViewById(R.id.headname);
            holder=new ViewHolder();
            holder.telphone=(TextView) convertView.findViewById(R.id.tel_phone);
            holder.tvmassage=(TextView) convertView.findViewById(R.id.tv_massage);
            holder.tvliuyan=(TextView) convertView.findViewById(R.id.tv_liuyan);
            holder.tv_nb=(TextView) convertView.findViewById(R.id.et_pricenow);
            holder.tv_ob=(TextView) convertView.findViewById(R.id.et_priceold);
            holder.tv_xy=(TextView) convertView.findViewById(R.id.et_xueyuan);
            holder.tv_ntime=(TextView) convertView.findViewById(R.id.et_time);
            holder.tv_describe=(TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_shouc=(TextView) convertView.findViewById(R.id.tv_shouc);
            mInflater=LayoutInflater.from(context);
            holder.mgallery=(LinearLayout) convertView.findViewById(R.id.id_gallery);
//            for(int i:guideIamge){
//                View mview=mInflater.inflate(R.layout.activity_index_gallery, holder.mGallery, false);
//                ImageView img=(ImageView) mview.findViewById(R.id.id_index_gallery_item_image);
//                img.setImageResource(i);
//                holder.mGallery.addView(mview);
//
//           }
            convertView.setTag(holder);
            //mGallery.setOnTouchListener(new mViewTouchLinstener());

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final ScondHandle p=mlist.get(position);
        final int a=p.getPhotoCount();
        holder.mgallery.removeAllViews();
        switch (a){
            case 1:View mview6 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img6 = (ImageView) mview6.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile8().getFileUrl()).fitCenter().into(img6);
                holder.mgallery.addView(mview6);

            case 2:View mview5 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img5 = (ImageView) mview5.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile7().getFileUrl()).fitCenter().into(img5);
                holder.mgallery.addView(mview5);

            case 3:View mview4 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img4 = (ImageView) mview4.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile6().getFileUrl()).fitCenter().into(img4);
                holder.mgallery.addView(mview4);
            case 4:View mview3 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img3 = (ImageView) mview3.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile5().getFileUrl()).fitCenter().into(img3);
                holder.mgallery.addView(mview3);
            case 5: View mview2 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img2 = (ImageView) mview2.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile4().getFileUrl()).fitCenter().into(img2);
                holder.mgallery.addView(mview2);
            case 6:  View mview = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img = (ImageView) mview.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile3().getFileUrl()).fitCenter().into(img);
                holder.mgallery.addView(mview);
            case 7:View mview7 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img7 = (ImageView) mview7.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile2().getFileUrl()).fitCenter().into(img7);
                holder.mgallery.addView(mview7);
            case 8: View mview8 = mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img8 = (ImageView) mview8.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile1().getFileUrl()).fitCenter().into(img8);
                holder.mgallery.addView(mview8);
            case 9:  View mview9= mInflater.inflate(R.layout.activity_index_gallery, holder.mgallery, false);
                ImageView img9 = (ImageView) mview9.findViewById(R.id.id_index_gallery_item_image);
                Glide.with(context).load(p.getFile().getFileUrl()).fitCenter().into(img9);
                holder.mgallery.addView(mview9);break;
            default:break;
        }
        //holder.txtname.setText(a.getName());
        //holder.telphone.setText(a.getTelPhone());
        holder.tv_nb.setText(p.getNowbalance()+"");
        holder.tv_ob.setText(p.getOldbalance()+"");
        holder.tv_xy.setText(p.getColleger());
        holder.tv_ntime.setText(p.getNowtime());
        holder.tv_describe.setText(p.getDescribe());
        final ViewHolder vh=holder;
        final String tel=p.getTelPhone();
        holder.telphone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String regex="^13\\d{9}|15\\d{9}|18\\d{9}$";
                if(tel.matches(regex)||tel.equals("10086")){
                    android.content.DialogInterface.OnClickListener ls=new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel));
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ((Context) context).startActivity(intent);
                        }
                    };
                    AlertDialog.Builder builder=new AlertDialog.Builder((Context)context);
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("输入您的手机号码验证？");
                    builder.setView(new EditText((Context)context));
                    builder.setPositiveButton("确定", ls);
                    builder.setNegativeButton("取消", null);
                    builder.show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder((Context)context);
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setTitle("不好意思!");
                    builder.setMessage("卖主留下联系方式非手机号码，请选择私信获取其他联系!");
                    builder.setNegativeButton("取消", null);
                    builder.show();
                    //Toast.makeText(((Context)context), "不好意思!卖主留下联系方式非手机号码，请选择私信获取联系。", 1).show();
                }
            }
        });
        holder.tvmassage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String regex="^13\\d{9}|15\\d{9}|18\\d{9}$";
                if(tel.matches(regex)||tel.equals("10086")){
                    Uri smsToUri=Uri.parse("smsto:"+tel);
                    Intent intent=new Intent(Intent.ACTION_SENDTO,smsToUri);
                    intent.putExtra("sms_body", "您好!我想了解，\""+vh.tv_describe.getText().toString().trim()+"\"");
                    ((Context) context).startActivity(intent);
                }else{
                    android.content.DialogInterface.OnClickListener ls=new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    };
                    AlertDialog.Builder builder=new AlertDialog.Builder((Context)context);
                    builder.setTitle("请选择索取联系方式？");
                    builder.setMessage(tel);
                    builder.setPositiveButton("微信号", ls);
                    builder.setPositiveButton("QQ号", ls);
                    builder.show();
                    //Toast.makeText(((Context)context), "不好意思!卖主留下联系方式非手机号码,请选择私信获取联系。", 1).show();
                }
            }
        });
        holder.tv_shouc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent=new Intent((Context) context,LonginActivity.class);
//                ((Context) context).startActivity(intent);
            }
        });
        holder.tvliuyan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent=new Intent((Context) context,ListItemActivity.class);
//                ((Context) context).startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder {
        TextView txtname,tv_xy,tv_ob,tv_nb,tv_ntime,tv_describe,telphone,tvmassage,tvliuyan,tv_shouc;
        LinearLayout mgallery;
        HorizontalScrollView scrollView;
    }
}
