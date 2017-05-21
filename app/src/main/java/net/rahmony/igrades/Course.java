package net.rahmony.igrades;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by RaHmOnY on 6/16/2016.
 */
public class Course extends RealmObject {

    @PrimaryKey
    private String courseTitle ;


    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }


}
