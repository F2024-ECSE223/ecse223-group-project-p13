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

