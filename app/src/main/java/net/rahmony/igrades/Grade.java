package net.rahmony.igrades;

import io.realm.RealmObject;

/**
 * Created by RaHmOnY on 6/21/2016.
 */
public class Grade extends RealmObject {

    private String courseTitle ;
    private String gradeTitle ;
    private double earnedGrade ;
    private double totalGrade ;

    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }


    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public double getEarnedGrade() {
        return earnedGrade;
    }

    public void setEarnedGrade(double earnedGrade) {
        this.earnedGrade = earnedGrade;
    }

    public double getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(double totalGrade) {
        this.totalGrade = totalGrade;
    }
}
