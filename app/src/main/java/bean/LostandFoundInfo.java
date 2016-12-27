package bean;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by hasee on 2016/11/28.
 */

public class LostandFoundInfo extends BmobObject {
    private int  ThingsType;//1捡到物品，2表示丢了物品
    private String  ThingsName;
    private String  ThingsPlace;
    private String ThingsInformation;
    private String  ThingsPepContactWay;
    private  String ThingsTime;
    private BmobFile LostPicture;
    private String publisTime ;//
    private String likeQuary;

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    private String campus;
    public String getLikeQuary() {
        return likeQuary;
    }

    public void setLikeQuary(String likeQuary) {
        this.likeQuary = likeQuary;
    }

    private int status; //1表示刚发布，2表示已解决，3表示过期


    public String getPublisTime() {
        return publisTime;
    }

    public void setPublisTime(String publisTime) {
        this.publisTime = publisTime;
    }

    public LostandFoundInfo(){

}
    public BmobFile getLostPicture() {
        return LostPicture;
    }
    public void setLostPicture(BmobFile lostPicture) {
        LostPicture = lostPicture;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getThingsType() {
        return ThingsType;
    }

    public void setThingsType(int thingsType) {
        ThingsType = thingsType;
    }

    public String getThingsName() {
        return ThingsName;
    }

    public void setThingsName(String thingsName) {
        ThingsName = thingsName;
    }

    public String getThingsPlace() {
        return ThingsPlace;
    }

    public void setThingsPlace(String thingsPlace) {
        ThingsPlace = thingsPlace;
    }

    public String getThingsInformation() {
        return ThingsInformation;
    }

    public void setThingsInformation(String thingsInformation) {
        ThingsInformation = thingsInformation;
    }

    public String getThingsPepContactWay() {
        return ThingsPepContactWay;
    }

    public void setThingsPepContactWay(String thingsPepContactWay) {
        ThingsPepContactWay = thingsPepContactWay;
    }

    public String getThingsTime() {
        return ThingsTime;
    }

    public void setThingsTime(String thingsTime) {
        ThingsTime = thingsTime;
    }
}
