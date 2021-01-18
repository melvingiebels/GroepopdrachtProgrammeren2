package GUI;

import Database.CourseDAO;
import Database.StudentDAO;

public abstract class GenericGUI {
    protected CourseDAO courseDAO = new CourseDAO();
    protected StudentDAO studentDAO = new StudentDAO();

}
