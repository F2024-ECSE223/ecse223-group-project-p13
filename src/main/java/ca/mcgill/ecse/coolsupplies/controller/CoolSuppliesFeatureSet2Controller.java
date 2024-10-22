package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

  public static String addStudent(String name, String gradeLevel) {


    if (!(name == null) && !(name.equals("") && !(gradeLevel == null)&&!(gradeLevel.equals(""))) ) {
      try {
        Grade grade = new Grade(gradeLevel, CoolSuppliesApplication.getCoolSupplies());
        new Student(name, CoolSuppliesApplication.getCoolSupplies(), grade);
      } catch (RuntimeException e){

      }
      return "";

    } else return "The name or the level cannot be empty or null.";

  }

  public static String updateStudent(String name, String newName, String newGradeLevel) {

    if (!name.isEmpty() && !newGradeLevel.isEmpty() && !newName.isEmpty() && !(name == null) && !(newName == null) && !(newGradeLevel == null)) {
      if (Student.getWithName(name) != null) {
        if (!Student.getWithName(name).setName(newName)) return "The student's name could not be changed.";
        if (!Student.getWithName(name).setGrade(new Grade(newGradeLevel, CoolSuppliesApplication.getCoolSupplies()))) return "The student's garde could not be modified.";
        return "";

      }else return "the Student does not exist.";

    }
    else return "The name or the level cannot be empty or null.";
  }

  public static String deleteStudent(String name) {

    if (!name.equals("") && !(name == null)){
      if (Student.getWithName(name) != null){
        Student.getWithName(name).delete();
        return "";
      }
      else return "The Student could not be found.";
    } else return "The name of the student cannot be empty or null.";
  }

  public static TOStudent getStudent(String name) {
    return new TOStudent(name, Student.getWithName(name).getGrade().getLevel());
  }

  // returns all students
  public static List<TOStudent> getStudents() {
    List<TOStudent> students = new ArrayList<>();
    for (Student s : CoolSuppliesApplication.getCoolSupplies().getStudents()) {
      students.add(getStudent(s.getName()));
    }
      return students;

  }

}
