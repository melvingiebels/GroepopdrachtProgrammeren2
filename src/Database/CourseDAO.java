package Database;

import java.util.ArrayList;

import Domain.Course;

public class CourseDAO extends GenericDAO {
    public void addCourse(Course newCourse) {
        SQL = String.format("INSERT INTO Course VALUES ('%s', '%s', '%s', '%s')", newCourse.getName(),
                newCourse.getTopic(), newCourse.getDescription(), newCourse.getDifficulty());
        this.excecuteQuery(SQL);
    }

    public ArrayList<Course> getAllCourses() {
        return null;
    }

    public void updateCourse() {
    }

    public void removeCourse() {
    }
}
