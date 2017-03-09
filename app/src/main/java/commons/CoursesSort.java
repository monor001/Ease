package commons;

import java.util.LinkedHashMap;

import models.Course;

/**
 * Created by asus on 08.03.2017.
 */

public interface CoursesSort {

    LinkedHashMap<String, Course> resortcourses (Course course, LinkedHashMap<String, Course> coursesList);
}
