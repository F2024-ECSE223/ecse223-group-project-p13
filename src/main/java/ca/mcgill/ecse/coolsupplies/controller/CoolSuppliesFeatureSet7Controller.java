package ca.mcgill.ecse.coolsupplies.controller;

import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse.coolsupplies.model.Grade;
import ca.mcgill.ecse.coolsupplies.application.CoolSuppliesApplication;

public class CoolSuppliesFeatureSet7Controller {

  public static String addGrade(String level) {
    //Checks if level is empty
    //Checks if level isnt a duplicate
    //If no errors arise, initializes newgrade and displays status message accordingly
   if (!level.equals("")) {
    try {
      new Grade(level,CoolSuppliesApplication.getCoolSupplies());
    } catch (RuntimeException e) {
      if (e.getMessage().equals("Cannot create due to duplicate level. See https://manual.umple.org?RE003ViolationofUniqueness.html")) return "The level must be unique";
    }
    return "";
   } else return "The level must not be empty.";
  }

  public static String updateGrade(String level, String newLevel) {
    //Checks if newLevel is empty
    //Checks if level exists
    //Checks if level was set to newlevel successfully
    //If it executes successfully, setLevel returns true and method returns empty string
    //Otherwise, error message is displayed accordingly. 
    if (!newLevel.equals("")){
      if (Grade.getWithLevel(level) != null) {
        if (!Grade.getWithLevel(level).setLevel(newLevel)) return "The level must be unique.";
        return "";
      } else return "The grade does not exist.";
    }
    else return "The level must not be empty.";
  }

  public static String deleteGrade(String level) {
    //Calls delete level on the instance of grade
    //Checks if grade was successfully removed, then displays status message accordingly
    Grade.getWithLevel(level).delete();
    if (!Grade.hasWithLevel(level)) return "Grade successfully deleted";
    else return "Error: Grade was not deleted";
  }

  public static TOGrade getGrade(String level) {
    //Creates TO from string level to be sent to UI
    return new TOGrade(Grade.getWithLevel(level).getLevel());
  }

  // returns all grades
  public static List<TOGrade> getGrades() {
    //Creates list of Grade TO's, iterates through list of all grades, then creates TO of each grade and adds it to the list
    List<TOGrade> grades = new ArrayList<TOGrade>();
    for (Grade i : CoolSuppliesApplication.getCoolSupplies().getGrades()) grades.add(getGrade(i.getLevel()));
    return grades;
  }

}