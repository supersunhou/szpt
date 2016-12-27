package ui;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.szpt.hasee.szpt.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.BaseActivity;
import bean.LostandFoundInfo;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import utils.BitmapUtils;

/**
 * Created by hasee on 2016/11/27.
 */

public class AddLostInfoActivity extends BaseActivity {

    private EditText edt_things_name,edt_things_information,
            edt_things_place,edt_things_time,edt_things_contact_way;
     private Button btn_add_ok,btn_add_no;
    private ImageView mImageView;
   private int ThingsType;
   private ProgressDialog progressDialog;
private Spinner mSpinner;
    private Spinner spn_campus;
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==1){
                Bitmap bitmap=(Bitmap)msg.obj;
                mImageView.setImageBitmap(bitmap);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }



    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_info_activity);
        //下拉框
        mSpinner= (Spinner) findViewById(R.id.spn_add_info_type);
        ArrayAdapter arr=ArrayAdapter.createFromResource(this,R.array.infotype, android.R.layout.simple_spinner_item);
        arr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinner.setAdapter(arr);
        //
        spn_campus= (Spinner) findViewById(R.id.spn_campus);
        ArrayAdapter arr2=ArrayAdapter.createFromResource(this,R.array.campus, android.R.layout.simple_spinner_item);
        arr.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spn_campus.setAdapter(arr2);
        //
        mImageView= (ImageView) findViewById(R.id.add_things_image);
        edt_things_name= (EditText) findViewById(R.id.edt_things_name);
        edt_things_information= (EditText) findViewById(R.id.edt_things_information);
        edt_things_contact_way= (EditText) findViewById(R.id.edt_things_contact_way);
        edt_things_place= (EditText) findViewById(R.id.edt_things_place);
        edt_things_time= (EditText) findViewById(R.id.edt_things_time);
        btn_add_ok= (Button) findViewById(R.id.btn_add_ok);
        btn_add_no= (Button) findViewById(R.id.btn_add_no);
        btn_add_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                startActivityForResult(intent, 1);
            }
        });
        btn_add_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(AddLostInfoActivity.this);
                progressDialog.setMessage("正在上传。。。");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                if(mSpinner.getSelectedItem().equals("捡到物品")){
                    ThingsType=1;
                }else{
                    ThingsType=2;
                }
                //
                String picPath = getFilesDir().getAbsolutePath()+"/bomS.jpg";
                final BmobFile bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            LostandFoundInfo info1=new LostandFoundInfo();
                            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            String strCurDate= formatter.format(curDate);
                            String thingsInformation=edt_things_information.getText().toString().trim();
                            String thingsName=edt_things_name.getText().toString().trim();
                            String thiingsPepContactWay=edt_things_contact_way.getText().toString().trim();
                            String thingsPlace=edt_things_place.getText().toString().trim();
                            String thingsTime=edt_things_time.getText().toString().trim();
                            if (thiingsPepContactWay.equals("")||thingsInformation.equals("")||thingsName.equals("")
                                    ||thingsPlace.equals("")||thingsTime.equals("")){
                                toast("发布失败，请填写完整信息");
                                progressDialog.dismiss();
                                return;
                            }else {
                                info1.setStatus(1);
                                info1.setCampus(spn_campus.getSelectedItem().toString());
                                info1.setThingsInformation(thingsInformation);
                                info1.setThingsName(thingsName);
                                info1.setThingsPepContactWay(thiingsPepContactWay);
                                info1.setThingsPlace(thingsPlace);
                                info1.setThingsTime(thingsTime);
                                info1.setPublisTime(strCurDate);
                                info1.setThingsType(ThingsType);
                                info1.setLostPicture(bmobFile);
                                info1.setLikeQuary(thingsName+thingsInformation+thingsPlace+thingsTime);
                                info1.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            toast("发布成功");
                                            progressDialog.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(AddLostInfoActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }else{
                            toast("上传文件失败：" + e.getMessage());
                        }
                    }
                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            if (data != null) {
                Uri selectedImage = data.getData();
                String picturePath = null;
                String[] filePathColumn = { MediaStore.Images.Media.DATA,
                        MediaStore.Images.Thumbnails.DATA,
                        MediaStore.Images.ImageColumns.DATA
                };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, MediaStore.Images.Media.MIME_TYPE+"=?or "+MediaStore.Images.Media.MIME_TYPE+"=?", new String[]{"image/jpeg","image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                if (cursor != null) {
                    Log.d("11111111111111111111", "图片来自数据库");
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    cursor = null;
                } else {
                    Log.d("222222222222222222222", "图片来自绝对路径");
                    picturePath=selectedImage.getPath();
                }
                //图片处理有待提高
                final Bitmap bitmap1= BitmapFactory.decodeFile(picturePath);
                if (bitmap1!=null) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            Bitmap  bitmap2= BitmapUtils.comp(bitmap1);
                            File fileB=new File(getFilesDir().getAbsolutePath()+"/bomS.jpg");
                            try {
                                fileB.createNewFile();
                                FileOutputStream out2=new FileOutputStream(fileB);
                                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, out2);
                                //更新界面
                                Message message=new Message();
                                message.what=1;
                                message.obj=bitmap2;
                                handler.sendMessage(message);
                                toast("图片获取成功");
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    }).start();
                }else  {
                    Toast.makeText(AddLostInfoActivity.this, "找不到该图片位置", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
