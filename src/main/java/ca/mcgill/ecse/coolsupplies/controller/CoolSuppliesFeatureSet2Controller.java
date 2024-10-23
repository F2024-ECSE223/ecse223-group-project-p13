package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

  /**
   * @author Clara Dupuis
   * @param name: name of the student that we wish to add in the list of students
   * @param gradeLevel: Grade Level of the student that we wish to add to the list of students
   * @return string: explication of the error if the student could not be added or empty string if the student was successfully added
   * This method attempts to add a student
   */
  public static String addStudent(String name, String gradeLevel) {


    if (name.equals("")) {
      return "The name must not be empty.";

    }else{
      try {
        if (Grade.getWithLevel(gradeLevel) != null) {
          new Student(name, CoolSuppliesApplication.getCoolSupplies(), Grade.getWithLevel(gradeLevel));
        }
        else {
          return "The grade does not exist.";
        }
      } catch (RuntimeException e){
        if (e.getMessage().equals("Cannot create due to duplicate name. See https://manual.umple.org?RE003ViolationofUniqueness.html")) {
          return "The name must be unique.";
        }
      }
      return "";

    }

  }
  /**
   * @author Clara Dupuis
   * @param name: name of the student that we wish to update
   * @param newName: name that we wish to change the student to
   * @param newGradeLevel: grade Level that we wish to change the student to
   * @return string: explication of the error if the student could not be updtated of an empty string if the student was updated successfully
   * This method attempts to modify the name and the grade level of a current student
   */
  public static String updateStudent(String name, String newName, String newGradeLevel) {

    Student student = Student.getWithName(name);

    if (student == null) {
      return "The student does not exist.";
    }

    Grade grade = Grade.getWithLevel(newGradeLevel);

    if (grade == null) {
      return "The grade does not exist.";
    }

    if (newName.isEmpty()) {
      return "The name must not be empty.";
    }

    if (!student.setName(newName)) {
      return "The name must be unique.";
    }

    if (!student.setGrade(grade)) {
      return "The grade could not be updated.";
    }

    return "";

  }

  /**
   * @author Clara Dupuis
   * @param name: name of the student that we wish to delete from the list of students
   * @return string: explication of the error if the student could not be deleted or empty string if the student was successfully deleted
   * This method attempts tto delete a student
   */

  public static String deleteStudent(String name) {

    if (!name.equals("") && !(name == null)){
      if (Student.getWithName(name) != null){
        Student.getWithName(name).delete();
        return "";
      }
      else return "The student does not exist.";
    } else return "The name must not be empty.";
  }

  /**
   * @author Clara Dupuis
   * @param name: the name of the student to retrieve
   * @return TOStudent: an object containing the student's name and grade level or null if the student could not be retrieved
   * This method retrieves a student based on the student's name
   */
  public static TOStudent getStudent(String name) {
    if (Student.getWithName(name) != null) {
      return new TOStudent(name, Student.getWithName(name).getGrade().getLevel());
    } else return null;
  }

  /**
   * @author Clara Dupuis
   * @return List<TOStudent>: a list of TOStudent objects representing all students in the CoolSupplies system
   *This method retrieves a list of all students currently managed by the CoolSupplies application by iterating through each Student object and calling getStudent() to retrieve the corresponding TOStudent object.
   */

  public static List<TOStudent> getStudents() {
    List<TOStudent> students = new ArrayList<>();
    for (Student s : CoolSuppliesApplication.getCoolSupplies().getStudents()) {
      students.add(getStudent(s.getName()));
    }
    return students;

  }

}