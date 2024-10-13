package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

  public static String addStudent(String name, String gradeLevel) {


    if (!name.isEmpty() || !gradeLevel.isEmpty() ) {
      try {
        Grade grade = new Grade(gradeLevel, CoolSuppliesApplication.getCoolSupplies());
        new Student(name, CoolSuppliesApplication.getCoolSupplies(), grade);
      } catch (RuntimeException e){

      }

    } else return "The name or the level cannot be empty or null.";

  }

  public static String updateStudent(String name, String newName, String newGradeLevel) {

    if (!name.isEmpty() || !newGradeLevel.isEmpty() || !newName.isEmpty() ) {
      if (Student.getWithName(name) != null){
        if (!Student.getWithName(name).setName(newName)){
          return "what";
        }
        return "";
        }

      }
      else return "The name or the level cannot be empty or null.";
  }

  public static String deleteStudent(String name) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static TOStudent getStudent(String name) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  // returns all students
  public static List<TOStudent> getStudents() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

}
