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
        ArrayList<Course> courses = new ArrayList<>();

        try {
            SQL = "SELECT * FROM Course";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                courses.add(new Course(rs.getString("CourseName"), rs.getString("Topic"), rs.getString("Description"),
                        rs.getString("Difficulty")));
            }
        } catch (Exception e) {
        }
        return courses;
    }

    public void updateCourse(Course course) {
        SQL = String.format(
                "UPDATE Course SET CourseName='%s', Topic='%s', Description='%s', Difficulty='%s' WHERE CourseName='%s'",
                course.getName(), course.getTopic(), course.getDescription(), course.getDifficulty(), course.getName());
        this.excecuteQuery(SQL);
    }

    public void removeCourse(String courseName) {
        SQL = String.format("DELETE FROM Course WHERE CourseName='%s'", courseName);
        this.excecuteQuery(SQL);
    }
}
