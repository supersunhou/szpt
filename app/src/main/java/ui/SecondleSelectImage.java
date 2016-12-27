package ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szpt.hasee.szpt.R;

import utils.SecondHandleUploadImage;

/**
 * Created by CGS on 2016/12/25.
 */

public class SecondleSelectImage extends Activity {
    private TextView textShow;
    private ImageView imageShow;
    private Bitmap bmp;
    private Bitmap mbmp;
    private ImageView imageSelect;
    private SecondHandleUploadImage personProcess=null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondhandleselect_img);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        textShow= (TextView) findViewById(R.id.textView1);
        imageShow= (ImageView) findViewById(R.id.imageView1);
        imageSelect= (ImageView) findViewById(R.id.image_increase);
        Intent intent=getIntent();
        String path=intent.getStringExtra("path");
        ShowPhotoByImageView(path);
        SetClickTouchLinstener();
    }
private void SetClickTouchLinstener() {
    imageSelect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textShow.setText("上传图片到发布界面");
            try{
                if(mbmp==null){
                    mbmp=bmp;
                }
                personProcess=new SecondHandleUploadImage(bmp);
                Uri uri=personProcess.loadBitmap(bmp);
                Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                sendBroadcast(intent);
                Intent intentPut=new Intent(SecondleSelectImage.this, SecondHandleCommit.class);
                String pathImage=null;
                intentPut.putExtra("pathProcess",personProcess.pathPicture);
                setResult(RESULT_OK,intentPut);
                Toast.makeText(SecondleSelectImage.this,"图片上传成功",Toast.LENGTH_LONG).show();
                SecondleSelectImage.this.finish();
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(SecondleSelectImage.this, "上传照片失败", Toast.LENGTH_SHORT).show();
            }

        }
    });
}

    private void ShowPhotoByImageView(String path) {
        if (null == path) {
            Toast.makeText(this, "载入图片失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    /*
     * 问题:
     * 获取Uri不知道getStringExtra()没对应uri参数
     * 使用方法Uri uri=Uri.parse(path)获取路径不能显示图片
     * mBitmap=BitmapFactory.decodeFile(path)方法不能适应大小
     * 解决:
     * 但我惊奇的发现decodeFile(path,opts)函数可以实现,哈哈哈
     */
        //获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;    //屏幕水平分辨率
        int height = dm.heightPixels;  //屏幕垂直分辨率
        try {
            //Load up the image's dimensions not the image itself
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            bmp = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);
            //压缩显示
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    bmpFactoryOptions.inSampleSize = heightRatio * 2;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio * 2;
                }
            }
            //图像真正解码
            bmpFactoryOptions.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            mbmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
            imageShow.setImageBitmap(bmp); //显示照片
	        /*
	         * [失败] 动态设置属性
	         当设置android:scaleType="matrix"后图像显示左上角
	         设置图片居中 起点=未使用屏幕/2=(屏幕分辨率-图片宽高)/2
	         int widthCenter=imageShow.getWidth()/2-bmp.getWidth()/2;
	         int heightCenter=imageShow.getHeight()/2-bmp.getHeight()/2;
	         Matrix matrix = new Matrix();
	         matrix.postTranslate(widthCenter, heightCenter);
	         imageShow.setImageMatrix(matrix);
	         imageShow.setImageBitmap(bmp);
	         */
            //加载备份图片 绘图使用


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
