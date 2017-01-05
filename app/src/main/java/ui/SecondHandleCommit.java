package ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.szpt.hasee.szpt.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.ScondHandle;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import fragment.SecondHandFragment;

/**
 * Created by CGS on 2016/12/25.
 */

public class SecondHandleCommit extends Activity {
         //拍照标记

    private EditText tv_xy,tv_ob,tv_nb,tv_describe,tv_phone;

    private List<ScondHandle> list=new ArrayList<ScondHandle>();
    private int sendCode=0;

    private GridView gridView1;
    private Button buttonPublish;
    private final int IMAGE_OPEN = 4;
    private final int GET_DATA = 2;
    private final int TAKE_PHOTO = 3;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE2=5;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE=6;
    private String pathImage;
    private Bitmap bmp;
    private Uri imageUri;
    private String pathTakePhoto;
    private List<String> mphotopath=new ArrayList<>();
    private String[] urlPicture;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;
    private Button btn_query;
    ScondHandle process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_secondhandlecommit);
       // Bmob.initialize(this,"094aaa7dc57ca4bd04781a4ceb7af1c0");
        initViews();
        initGridView();
        initDatas();

    }



    private void initViews() {
        gridView1=(GridView)findViewById(R.id.gridView1);
        tv_xy= (EditText) findViewById(R.id.et_xueyuan);
        tv_ob= (EditText) findViewById(R.id.et_oldbalance);
        tv_nb= (EditText) findViewById(R.id.now_balance);
        tv_describe= (EditText) findViewById(R.id.et_describe);
        tv_phone= (EditText) findViewById(R.id.et_phone);
        buttonPublish= (Button) findViewById(R.id.btn_submit);
        btn_query= (Button) findViewById(R.id.btn_query);
    }
    private void initGridView() {
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic); //加号
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        map.put("pathImage", "add_pic");
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.secondhandlegetphoto,
                new String[] { "itemImage"}, new int[] { R.id.imageView1});
        /*
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView1.setAdapter(simpleAdapter);
        // gridView1.setAdapter(simpleAdapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(imageItem.size()==10&&position==0){
                    Toast.makeText(SecondHandleCommit.this,"图片数为9张已满",Toast.LENGTH_LONG).show();
                }
                else if(position==0){
                    AddImageDialog();
                }else{
                    DeleteDialog(position);
                }
            }
        });
    }

    private void initDatas() {
        buttonPublish.setOnClickListener(new View.OnClickListener()
        {

            @SuppressWarnings("unused")
            @Override
            public void onClick(View v) {
               final String describe=tv_describe.getText().toString().trim();
                String nb=tv_nb.getText().toString().trim();
                String ob=tv_ob.getText().toString().trim();
               final String xy=tv_xy.getText().toString().trim();
               final String telp=tv_phone.getText().toString().trim();
                SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
               final String tv_time=s.format(new java.util.Date());
               final int nb1=Integer.parseInt(nb);
               final int ob1=Integer.parseInt(ob);
                if(describe.equals("")||nb.equals("")||ob.equals("")||xy.equals("")||telp.equals("")){
                    Toast.makeText(SecondHandleCommit.this, "请完整填写信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (imageItem.size() == 1) {
                    Toast.makeText(SecondHandleCommit.this, "请添加商品图片", Toast.LENGTH_LONG).show();
                    return;
                }
                final String[] photo1 = new String[mphotopath.size()];
                int i = 0;
                for (String photo2 : mphotopath) {
                    photo1[i++] = photo2;
                }
                final ProgressDialog progressDialog = new ProgressDialog(SecondHandleCommit.this);
                progressDialog.setMessage("正在上传...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                Bmob.uploadBatch(photo1, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        if (list1.size() == photo1.length) {
                            switch (photo1.length){
                                case 1: ScondHandle process1 = new ScondHandle(list.get(0));
                                   process1.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",9);
                                    insertObject(process1);
                                    break;
                                case 2: ScondHandle process2 = new ScondHandle(list.get(0), list.get(1));
                                    process2.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",8);
                                    insertObject(process2);;
                                    break;
                                case 3: ScondHandle process3 = new ScondHandle(list.get(0), list.get(1),list.get(2));
                                    process3.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",7);
                                    insertObject(process3);
                                    break;
                                case 4: ScondHandle process4 = new ScondHandle(list.get(0), list.get(1),list.get(2),list.get(3));
                                    process4.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",6);
                                    insertObject(process4);
                                    break;
                                case 5: ScondHandle process5 = new ScondHandle(list.get(0), list.get(1),list.get(2),list.get(3),list.get(4));
                                    process5.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",5);
                                    insertObject(process5);
                                    break;
                                case 6: ScondHandle process6 = new ScondHandle(list.get(0), list.get(1),list.get(2),list.get(3),list.get(4),list.get(5));
                                    process6.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",4);
                                    insertObject(process6);
                                    break;
                                case 7: ScondHandle process7 = new ScondHandle(list.get(0), list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6));
                                    process7.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",3);
                                    insertObject(process7);
                                    break;
                                case 8: ScondHandle process8 = new ScondHandle(list.get(0), list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6),list.get(7));
                                    process8.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",2);
                                    insertObject(process8);
                                    break;
                                case 9: ScondHandle process9 = new ScondHandle(list.get(0), list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6),list.get(7),list.get(8));
                                    process9.setAllMessage(nb1,ob1,xy,telp,describe,tv_time,"小陈",1);
                                    insertObject(process9);
                                    break;
                            }
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                        Log.i("life", "insertBatchDatasWithOne -onProgress :" + i + "---" + i1 + "---" + i2 + "----" + i3);

                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(SecondHandleCommit.this, "错误码" + i + ",错误描述：" + s, Toast.LENGTH_LONG).show();
                    }


                });

               // Toast.makeText(SecondHandleCommit.this, "发布成功", Toast.LENGTH_SHORT).show();
            }

            private void insertObject(ScondHandle processn) {
                processn.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(SecondHandleCommit.this,"process上传成功",Toast.LENGTH_LONG).show();
                            tv_describe.setText("");
                            tv_nb.setText("");
                            tv_ob.setText("");
                            tv_xy.setText("");
                            tv_phone.setText("");
                            sendCode++;
                        }
                    }
                });
            }
        });

        /**
         * 载入图片添加图片加号
         * 通过适配器实现
         * SimpleAdapter参数imageItem为数据源R.layout.gridItem_addpic为布局
         */

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondHandleCommit.this, SecondHandFragment.class);
                startActivity(i);
            }
        });
    }
    private void DeleteDialog(final int position) {
        AlertDialog.Builder buidler=new AlertDialog.Builder(SecondHandleCommit.this);
        buidler.setMessage("確定删除图片吗？");
        buidler.setTitle("提示");
        buidler.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        buidler.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        buidler.create().show();
    }
    private void AddImageDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(SecondHandleCommit.this);
        builder.setTitle("添加图片");
       // builder.setTitle(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setItems(new String[]{"本地相册选择", "手机相机添加", "取消选择图片"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        dialog.dismiss();
                        chooseImage();
                        break;
                    case 1:
                        dialog.dismiss();
                        permission();
                        break;
                    case 2:
                        dialog.dismiss();break;
                    default:break;
                }
            }
        });
        builder.create().show();
    }
    private void permission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        }else{
            takePhoto();
        }
    }
    public void chooseImage(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        }else {
            choosePhoto();
        }
    }

    private void choosePhoto() {
        Intent intent=new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMAGE_OPEN);
        Toast.makeText(this,"打开相册",Toast.LENGTH_SHORT).show();
    }

    private void takePhoto() {
        File outputImage=new File(Environment.getExternalStorageDirectory(),"cgs_image.jpg");
        pathTakePhoto=outputImage.toString();
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        imageUri=Uri.fromFile(outputImage);
        Intent intentPhoto=new Intent("android.media.action.IMAGE_CAPTURE");
        intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intentPhoto,TAKE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                takePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(SecondHandleCommit.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                choosePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(SecondHandleCommit.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(pathImage)){
            Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            map.put("pathImage", pathImage);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.secondhandlegetphoto,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            //接口载入图片
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            mphotopath.add(pathImage);
            pathImage = null;
            for (String m:mphotopath){
                System.out.println(m);
                Toast.makeText(SecondHandleCommit.this,m,Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[] { MediaStore.Images.Media.DATA },
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                //向处理活动传递数据
                //Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SecondleSelectImage.class); //主活动->处理活动
                intent.putExtra("path", path);
                //startActivity(intent);
                startActivityForResult(intent, GET_DATA);
            } else {
                Intent intent = new Intent(this, SecondleSelectImage.class); //主活动->处理活动
                intent.putExtra("path", uri.getPath());
                //startActivity(intent);
                startActivityForResult(intent, GET_DATA);
            }
        }
        if(resultCode==RESULT_OK&&requestCode==GET_DATA){
            pathImage=data.getStringExtra("pathProcess");
        }
        if(resultCode==RESULT_OK&&requestCode==TAKE_PHOTO){
            Intent intentBc=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intentBc.setData(imageUri);
            this.sendBroadcast(intentBc);
            Intent intentput=new Intent(this, SecondleSelectImage.class);
            intentput.putExtra("path",pathTakePhoto);
            startActivityForResult(intentput,GET_DATA);
        }
    }



}
