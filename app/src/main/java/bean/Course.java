package bean;

/**
 * Created by hasee on 2016/12/29.
 */

public class Course {
    private String coueseName;
    private String courseTime;
    private String coursePlace;

    public Course(String coueseName,String courseTime,String coursePlace){
        this.coueseName=coueseName;
        this.courseTime=courseTime;
        this.coursePlace=coursePlace;
    }
    public String getCoueseName() {
        return coueseName;
    }

    public void setCoueseName(String coueseName) {
        this.coueseName = coueseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCoursePlace() {
        return coursePlace;
    }

    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }
}
