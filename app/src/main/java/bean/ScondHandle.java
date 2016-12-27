package bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by CGS on 2016/12/25.
 */

public class ScondHandle extends BmobObject {
    private BmobFile file;
    private BmobFile file1;
    private BmobFile file2;
    private BmobFile file3;
    private BmobFile file4;
    private BmobFile file5;
    private BmobFile file6;
    private BmobFile file7;
    private BmobFile file8;
    private Long id;
    private Integer nowbalance;
    private Integer oldbalance;
    private String colleger;
    private String telPhone;
    private String describe;
    private String nowtime;
    private String name;
    private Integer photoCount;

    public void setAllMessage(Integer nowbalance,Integer oldbalance,String colleger,String telPhone,String describe,String nowtime,String name,Integer photoCount){
        this.nowbalance=nowbalance;
        this.oldbalance=oldbalance;
        this.colleger=colleger;
        this.telPhone=telPhone;
        this.describe=describe;
        this.name=name;
        this.nowtime=nowtime;
        this.photoCount=photoCount;
    }
    public ScondHandle(BmobFile file) {
        this.file=file;
    }
    public ScondHandle(BmobFile file,BmobFile file1) {
        this.file=file;
        this.file1=file1;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2) {
        this.file=file;
        this.file1=file1;
        this.file2=file2;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2,BmobFile file3) {
        this.file=file;
        this.file1=file1;
        this.file3=file3;
        this.file2=file2;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2,BmobFile file3,BmobFile file4) {
        this.file=file;
        this.file1=file1;
        this.file3=file3;
        this.file2=file2;
        this.file4=file4;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2,BmobFile file3,BmobFile file4,BmobFile file5) {
        this.file=file;
        this.file1=file1;
        this.file3=file3;
        this.file2=file2;
        this.file3=file4;
        this.file2=file5;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2,BmobFile file3,BmobFile file4,BmobFile file5,BmobFile file6) {
        this.file=file;
        this.file1=file1;
        this.file3=file3;
        this.file2=file2;
        this.file3=file4;
        this.file2=file5;
        this.file6=file6;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2,BmobFile file3,BmobFile file4,BmobFile file5,BmobFile file6,BmobFile file7) {
        this.file=file;
        this.file1=file1;
        this.file3=file3;
        this.file2=file2;
        this.file3=file4;
        this.file2=file5;
        this.file6=file6;
        this.file7=file7;
    }
    public ScondHandle(BmobFile file,BmobFile file1,BmobFile file2,BmobFile file3,BmobFile file4,BmobFile file5,BmobFile file6,BmobFile file7,BmobFile file8) {
        this.file=file;
        this.file1=file1;
        this.file3=file3;
        this.file2=file2;
        this.file3=file4;
        this.file2=file5;
        this.file6=file6;
        this.file7=file7;
        this.file8=file8;
    }
    public int getPhotoCount() {
        return photoCount;
    }
    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BmobFile getFile8() {
        return file8;
    }

    public void setFile8(BmobFile file8) {
        this.file8 = file8;
    }

    public Integer getNowbalance() {
        return nowbalance;
    }

    public void setNowbalance(Integer nowbalance) {
        this.nowbalance = nowbalance;
    }

    public Integer getOldbalance() {
        return oldbalance;
    }

    public void setOldbalance(Integer oldbalance) {
        this.oldbalance = oldbalance;
    }

    public String getColleger() {
        return colleger;
    }

    public void setColleger(String colleger) {
        this.colleger = colleger;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNowtime() {
        return nowtime;
    }

    public void setNowtime(String nowtime) {
        this.nowtime = nowtime;
    }

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public BmobFile getFile1() {
        return file1;
    }

    public void setFile1(BmobFile file1) {
        this.file1 = file1;
    }

    public BmobFile getFile2() {
        return file2;
    }

    public void setFile2(BmobFile file2) {
        this.file2 = file2;
    }

    public BmobFile getFile3() {
        return file3;
    }

    public void setFile3(BmobFile file3) {
        this.file3 = file3;
    }

    public BmobFile getFile4() {
        return file4;
    }

    public void setFile4(BmobFile file4) {
        this.file4 = file4;
    }

    public BmobFile getFile5() {
        return file5;
    }

    public void setFile5(BmobFile file5) {
        this.file5 = file5;
    }

    public BmobFile getFile6() {
        return file6;
    }

    public void setFile6(BmobFile file6) {
        this.file6 = file6;
    }

    public BmobFile getFile7() {
        return file7;
    }

    public void setFile7(BmobFile file7) {
        this.file7 = file7;
    }


}
