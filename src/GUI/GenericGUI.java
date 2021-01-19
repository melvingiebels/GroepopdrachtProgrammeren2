package GUI;

import Database.ContentItemDAO;
import Database.CourseDAO;
import Database.StudentDAO;

public abstract class GenericGUI {
    protected CourseDAO courseDAO = new CourseDAO();
    protected StudentDAO studentDAO = new StudentDAO();
    protected ContentItemDAO contentItemDAO = new ContentItemDAO();

}
