package bean;

import java.util.List;

/**
 * Created by hasee on 2016/12/29.
 */

public class SchoolTimeTable {
    private String nowWeek;
    private String nowDay;
    private List<Course> courses;

    public SchoolTimeTable(String nowWeek,String nowDay,List<Course> courses) {
     this.nowWeek=nowWeek;
        this.nowDay=nowDay;
        this.courses=courses;
    }
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getNowWeek() {
        return nowWeek;
    }

    public void setNowWeek(String nowWeek) {
        this.nowWeek = nowWeek;
    }

    public String getNowDay() {
        return nowDay;
    }

    public void setNowDay(String nowDay) {
        this.nowDay = nowDay;
    }



}
