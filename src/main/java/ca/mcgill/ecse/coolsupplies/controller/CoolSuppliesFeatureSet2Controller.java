package ca.mcgill.ecse.coolsupplies.controller;

import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;
import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CoolSuppliesFeatureSet2Controller {

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
  public static String updateStudent(String name, String newName, String newGradeLevel) {
    System.out.println(name);
    System.out.println(newName);
    System.out.println(newGradeLevel);

    if (!name.isEmpty() && !newGradeLevel.isEmpty() && !newName.isEmpty() && !(name == null) && !(newName == null) && !(newGradeLevel == null)) {
      if (Student.getWithName(name) != null) {
        Student student = Student.getWithName(name);
        if (!student.setName(newName)) return "The name must be unique.";
        if (Grade.getWithLevel(newGradeLevel) == null || student.setGrade(Grade.getWithLevel(newGradeLevel))){
          return "The grade does not exist.";
        }
        return "";

      }else return "The student does not exist.";

    }
    else return "The name must not be empty.";
  }

  public static String deleteStudent(String name) {

    if (!name.equals("") && !(name == null)){
      if (Student.getWithName(name) != null){
        Student.getWithName(name).delete();
        return "";
      }
      else return "The student does not exist.";
    } else return "The name of the student cannot be empty or null.";
  }

  public static TOStudent getStudent(String name) {
    if (Student.getWithName(name) != null) {
      return new TOStudent(name, Student.getWithName(name).getGrade().getLevel());
    } else return null;
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

