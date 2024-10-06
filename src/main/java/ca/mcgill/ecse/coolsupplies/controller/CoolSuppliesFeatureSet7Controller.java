package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;

import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;

public class CoolSuppliesFeatureSet7Controller {

  public static String addGrade(String level) {
    //implement code to find coolsuppliesapplication
    Grade newgrade=new Grade(level,getCoolSupplies());
  }

  public static String updateGrade(String level, String newLevel) {
    //Grade oldGrade = getWithLevel(level);
    //boolean updated=oldgrade.setLevel(newLevel);
    if (getWithLevel(level).setLevel(newLevel)) return "Grade updated successfully";
    else return "Error: Grade was not updated";
  }

  public static String deleteGrade(String level) {

    getWithLevel(level).delete();
    List<Grade> allGrades = getGrades()
    if (!hasWithlevel(level)) return "Grade successfully deleted";
    else return "Error: Grade was not deleted";

  }

  public static TOGrade getGrade(String level) {
    return TOGrade(level);
  }

  // returns all grades
  public static List<TOGrade> getGrades() {

    List<TOGrade> grades;
    //figure out how to access coolsupplies getgrades function
    for (Grade i : CoolSupplies.getGrades())) grades.append(i);
  return grades;

  }

}
