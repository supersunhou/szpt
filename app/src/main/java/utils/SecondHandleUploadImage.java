package utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CGS on 2016/12/17.
 */
public class SecondHandleUploadImage {
    private Bitmap mBitmap;
    public String pathPicture;
    public SecondHandleUploadImage(Bitmap bmp){
        mBitmap=bmp;
    }
    private Uri saveBitmapToSD(Bitmap bmp) throws IOException {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date(System.currentTimeMillis());
        String filename=format.format(date);
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if(!path.exists()){
            path.mkdir();
        }
        File imagePath=new File(path,filename+".jpg");
        imagePath.createNewFile();
        FileOutputStream fos=new FileOutputStream(imagePath);
        bmp.compress(Bitmap.CompressFormat.JPEG,100,fos);
        fos.flush();
        fos.close();
        return Uri.fromFile(imagePath);
    }
    public Uri loadBitmap(Bitmap bmp) throws IOException {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date(System.currentTimeMillis());
        String filename=format.format(date);
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if(!path.exists()){
            path.mkdir();
        }
        File imagePath=new File(path,filename+".jpg");
        imagePath.createNewFile();
        FileOutputStream fos=new FileOutputStream(imagePath);
        bmp.compress(Bitmap.CompressFormat.JPEG,100,fos);
        fos.flush();
        fos.close();
        pathPicture=imagePath.toString();
        return Uri.fromFile(imagePath);
    }
}
