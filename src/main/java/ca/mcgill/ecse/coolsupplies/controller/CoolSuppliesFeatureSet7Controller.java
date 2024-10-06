package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;

import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

public class CoolSuppliesFeatureSet7Controller {

  public static String addGrade(String level) {
    //implement code to find coolsuppliesapplication
    Grade newgrade = new Grade(level,CoolSuppliesApplication.getCoolSupplies);
    if (!Grade.hasWithlevel(level)) return "Grade successfully added";
    else return "Error: Grade was not added";
  }

  public static String updateGrade(String level, String newLevel) {
    if (Grade.getWithLevel(level).setLevel(newLevel)) return "Grade updated successfully";
    else return "Error: Grade was not updated";
  }

  public static String deleteGrade(String level) {
    getWithLevel(level).delete();
    if (!Grade.hasWithlevel(level)) return "Grade successfully deleted";
    else return "Error: Grade was not deleted";

  }

  public static TOGrade getGrade(String level) {
    return TOGrade.TOGrade(level);
  }

  // returns all grades
  public static List<TOGrade> getGrades() {
    List<TOGrade> grades;
    for (Grade i : CoolSupplies.getGrades()) grades.add(getGrade(i.level));
    return grades;
  }

}
