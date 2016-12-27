package bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by hasee on 2016/11/27.
 */

public class User extends BmobObject {
    private String name;
    private String address;
    private String stundentNumber;
    private String phoneNumber;
    private String passWord;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStundentNumber() {
        return stundentNumber;
    }

    public void setStundentNumber(String stundentNumber) {
        this.stundentNumber = stundentNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


}
